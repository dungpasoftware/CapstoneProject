import React, { useRef, useState } from 'react'
import { StyleSheet, View, FlatList, ActivityIndicator, Alert, Text } from 'react-native'
import { useSelector, useDispatch } from 'react-redux'


import Ordered2Item from './Ordered2Item'
import BillOverview from '../OrderingScreen/BillOverView'
import OptionDishOrdered from './OptionDishOrdered'
import ChangeAmountAndPrice from './ChangeAmountAndPrice'
import ChangeTopping from './ChangeTopping'
import orderApi from '../../api/orderApi'
import CancelDishModal from './CancelDishModal'
import { MAIN_COLOR } from '../../common/color';
import { loadDishOrdered } from '../../actions/dishOrdered'
import { showToast } from '../../common/functionCommon'




export default function OrderedScreen({ route }) {
    const { userInfo, orderId } = route.params
    const { accessToken } = userInfo
    const { rootOrder, isLoading, error } = useSelector(state => state.dishOrdered)
    const [paymentLoading, setPaymentLoading] = useState(false)

    const dispatch = useDispatch()

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


    if (error != null) {
        Alert.alert(
            "Lỗi",
            "Hệ thống không phản hồi.",
            [
                {
                    text: "Thử lại",
                    onPress: () => {
                        dispatch(loadDishOrdered({ accessToken, orderId }))
                    }
                }
            ],
            { cancelable: false }
        );
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
            modifiedBy: userInfo.staffCode,
            createdBy: userInfo.staffCode,
        }
        orderApi.cancelDishOrder(accessToken, dataForCancel).then((res) => {
            showToast('Hủy món thành công!')
        }).catch(err => {
            if (err == "timeout") {
                showToast("Lỗi mạng! Hủy món thất bại!")
            } else {
                showToast("Hủy món thất bại!")
            }
        })
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
            showToast(`Có lỗi xảy ra! ${rootOrder.statusId == 14 ? "Thanh toán" : "Hủy thanh toán"} thất bại!`)
        })
    }
    const isAcceptPayment = rootOrder.statusId == 15
    const isRequestPayment = rootOrder.statusId == 14
    return (
        <View style={styles.container}>
            {isLoading ? <ActivityIndicator style={{ flex: 9, alignSelf: 'center' }} size="large" color={MAIN_COLOR} /> :
                (rootOrder.orderDish != undefined && rootOrder.orderDish.length <= 0) ? <View style={{ flex: 9, justifyContent: 'center', alignItems: 'center' }}>
                    <Text style={{ fontSize: 16 }}>{'Chưa có món nào được đặt'}</Text></View> :
                    <View style={{ flex: 9 }}>
                        <FlatList
                            data={(rootOrder.orderDish != undefined && rootOrder.orderDish != null) ? rootOrder.orderDish : []}
                            keyExtractor={(item) => item.orderDishId.toString()}
                            renderItem={({ item }) => {
                                if (item.quantityOk <= 0 && item.statusStatusId != 22) return;
                                return (
                                    <Ordered2Item isDisable={isAcceptPayment || isRequestPayment} item={item} showOptionDish={showOptionDish} />
                                )
                            }}
                        />
                    </View>}
            <BillOverview
                buttonName={isRequestPayment ? "Hủy Thanh Toán" : "Thanh toán"}
                isDisable={isAcceptPayment}
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
