import React, { useRef, useState } from 'react'
import { StyleSheet, View, FlatList, ActivityIndicator, Alert } from 'react-native'
import { useSelector } from 'react-redux'


import Ordered2Item from './Ordered2Item'
import BillOverview from '../OrderingScreen/BillOverView'
import OptionDishOrdered from './OptionDishOrdered'
import ChangeAmountAndPrice from './ChangeAmountAndPrice'
import ChangeTopping from './ChangeTopping'
import orderApi from '../../api/orderApi'
import CancelDishModal from './CancelDishModal'
import { MAIN_COLOR } from '../../common/color';




export default function OrderedScreen({ route }) {
    const { userInfo } = route.params
    const { accessToken } = userInfo
    const { rootOrder, isLoading } = useSelector(state => state.dishOrdered)
    const [paymentLoading, setPaymentLoading] = useState(false)



    const optionDishRef = useRef(null);
    const changeAPRef = useRef(null);
    function showOptionDish(item) {
        optionDishRef.current.showOptionDishBox(item);
    }


    const changeToppingRef = useRef(null)
    function showChangeTopping(item) {
        changeToppingRef.current.showChangeTopping(item);
    }


    const cancelDishModalRef = useRef(null)
    function showCancelDishModal(item) {
        cancelDishModalRef.current.showCancelDishModalBox(item);
    }


    function showOptionDetail(option, itemSelected) {
        switch (option) {
            case 1: {
                changeAPRef.current.showChangeAPRefBox(itemSelected);
                break;
            }
            case 2: {
                showChangeTopping(itemSelected)
                break
            }
            case 3: {
                showCancelDishModal(itemSelected)
                break
            }
            default: console.log(itemSelected)
                break;
        }

    }

    function submitCancelDish(dishInfo) {
        let dataForCancel = {
            ...dishInfo,
            modifiedBy: userInfo.staffCode
        }
        orderApi.cancelDishOrder(accessToken, dataForCancel)
    }

    function _handleSubmitPayment() {
        setPaymentLoading(true)
        orderApi.waitingForPayment(accessToken, { orderId: rootOrder.orderId }).then(response => {
            setPaymentLoading(false)
            if (response.data == undefined) {
                Alert.alert(
                    'Thông báo!',
                    response,
                    [
                        {
                            text: 'Tôi hiểu',
                            style: 'cancel'
                        }
                    ],
                    { cancelable: false }
                );
            }
            console.log("Báo thanh toán thành công", response)
        }).catch(err => {
            setPaymentLoading(false)
            Alert.alert(
                'Lỗi!',
                err,
                [
                    {
                        text: 'Thoát',
                        style: 'cancel'
                    }
                ],
                { cancelable: false }
            );
        })
    }

    return (
        <View style={styles.container} pointerEvents={rootOrder.statusId == 15 ? 'none' : 'auto'}>
            {isLoading ? <ActivityIndicator style={{ flex: 9, alignSelf: 'center' }} size="large" color={MAIN_COLOR} /> :
                <View style={{ flex: 9 }} pointerEvents={rootOrder.statusId == 14 ? 'none' : 'auto'}>
                    <FlatList
                        data={rootOrder.orderDish}
                        keyExtractor={(item) => item.orderDishId.toString()}
                        renderItem={({ item }) => {
                            if (item.quantityOk <= 0 && item.statusStatusId != 22) return;
                            return (
                                <Ordered2Item item={item} showOptionDish={showOptionDish} />
                            )
                        }}
                    />
                </View>}
            <BillOverview
                buttonName={rootOrder.statusId == 14 ? "Hủy Thanh Toán" : "Thanh toán"}
                totalAmount={rootOrder.totalAmount}
                totalItem={rootOrder.totalItem}
                isLoading={isLoading || paymentLoading}
                handle={_handleSubmitPayment}
            />
            <OptionDishOrdered ref={optionDishRef} handleMenu={showOptionDetail} />
            <ChangeAmountAndPrice ref={changeAPRef} userInfo={userInfo} />
            <ChangeTopping ref={changeToppingRef} accessToken={accessToken} />
            <CancelDishModal ref={cancelDishModalRef} submitCancelDish={submitCancelDish} />
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        backgroundColor: 'white'

    }
})
