import React, { useRef, useState, useEffect } from 'react'
import { StyleSheet, View, FlatList, ActivityIndicator, Alert, Text } from 'react-native'
import { useSelector, useDispatch } from 'react-redux'


import Ordered2Item from './Ordered2Item'
import BillOverview from '../OrderingScreen/BillOverView'
import OptionDishOrdered from './OptionDishOrdered'
import ChangeAP from './ChangeAP'
import ChangeTopping from './ChangeTopping'
import orderApi from '../../api/orderApi'
import CancelDishModal from './CancelDishModal'
import { MAIN_COLOR } from '../../common/color';
import { loadDishOrdered } from '../../actions/dishOrdered'
import { showToast } from '../../common/functionCommon'




export default function OrderedScreen({ route, navigation }) {
    const { userInfo, orderId } = route.params
    const { accessToken } = userInfo
    const { rootOrder, isLoading, error } = useSelector(state => state.dishOrdered)
    const [paymentLoading, setPaymentLoading] = useState(false)

    const dispatch = useDispatch()

    const optionDishRef = useRef(null);
    function showOptionDish(item) {
        optionDishRef.current.showOptionDishBox(item);
    }


    const changeAPRef = useRef(null);
    function showChangeAP(item) {
        changeAPRef.current.showChangeAPRefBox(item);
    }


    const changeToppingRef = useRef(null)
    function showChangeTopping(item) {
        changeToppingRef.current.showChangeTopping(item);
    }


    const cancelDishModalRef = useRef(null)
    function showCancelDishModal(item) {
        cancelDishModalRef.current.showCancelDishModalBox(item);
    }

    useEffect(() => {
        const unsubscribe = navigation.addListener('blur', () => {
            // The screen is focused
            // Call any action
            optionDishRef.current.closeOptionDishBox()
            changeAPRef.current.closeChangeAPRefBox()
            changeToppingRef.current.closeChangeTopping()
            cancelDishModalRef.current.closeCancelDishModalBox()


        });

        // Return the function to unsubscribe from the event so it gets removed on unmount
        return unsubscribe;
    }, [navigation]);



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
                showChangeAP(itemSelected)
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
                showToast("Có gì đó xảy ra, hủy món thất bại.")
            } else {
                showToast("Có gì đó xảy ra, hủy món thất bại.")
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
        }).catch(err => {
            setPaymentLoading(false)
            showToast(`Có gì đó xảy ra, ${rootOrder.statusId == 14 ? "thanh toán" : "hủy thanh toán"} thất bại.`)
        })
    }
    const isAcceptPayment = rootOrder.statusId == 15
    const isRequestPayment = rootOrder.statusId == 14
    function checkDishInOrder() {
        if (rootOrder.orderDish == undefined && rootOrder.orderDish == null) return true
        if (rootOrder.orderDish.length <= 0) return true
        let check = true
        rootOrder.orderDish.forEach(item => {
            if (item.quantityOk > 0 || item.quantityCancel > 0) {
                check = false
            }
        });
        return check
    }
    return (
        <View style={styles.container}>
            {isLoading ? <ActivityIndicator style={{ flex: 9, alignSelf: 'center' }} size="large" color={MAIN_COLOR} /> :
                checkDishInOrder() ?
                    <View style={{ flex: 9, justifyContent: 'center', alignItems: 'center' }}>
                        <Text style={{ fontSize: 16 }}>{'Chưa có món nào được đặt'}</Text>
                    </View> :
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
            <ChangeAP ref={changeAPRef} userInfo={userInfo} />
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
