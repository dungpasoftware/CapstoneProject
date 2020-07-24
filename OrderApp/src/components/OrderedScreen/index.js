import React, { useRef, useEffect } from 'react'
import { StyleSheet, View, FlatList, ActivityIndicator } from 'react-native'
import { useSelector, useDispatch } from 'react-redux'

import { loadDishOrdered, loadDishOrderedSuccess } from '../../actions/dishOrdered'
import Ordered2Item from './Ordered2Item'
import BillOverview from '../OrderingScreen/BillOverView'
import OptionDishOrdered from './OptionDishOrdered'
import ChangeAmountAndPrice from './ChangeAmountAndPrice'
import ChangeTopping from './ChangeTopping'
import orderApi from '../../api/orderApi'
import CancelDishModal from './CancelDishModal'
import { MAIN_COLOR } from '../../common/color';


// socket
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import { changeTotalAPOrdering } from '../../actions/dishOrdering'
import { ROOT_API_CONNECTION } from '../../common/apiConnection'


export default function OrderedScreen({ route }) {
    const dispatch = useDispatch()
    const { userInfo, orderId, loadDataToRootOrder } = route.params
    const { accessToken } = userInfo
    const { rootOrder, isLoading } = useSelector(state => state.dishOrdered)
    useEffect(() => {
        let socket = new SockJS(`${ROOT_API_CONNECTION}/rms-websocket`);
        let stompClient = Stomp.over(socket);
        stompClient.debug = () => { }
        stompClient.connect(
            {
                token: accessToken
            },
            frame => {
                console.log('socket Ordered connected');
                stompClient.subscribe(`/topic/orderdetail/${orderId}`, ({ body }) => {
                    let orderData = JSON.parse(body);
                    console.log('Soket hoạt động', orderData)
                    dispatch(loadDishOrderedSuccess(orderData))
                    dispatch(changeTotalAPOrdering({
                        totalAmount: orderData.totalAmount,
                        totalItem: orderData.totalItem
                    }))
                });
            },
            error => {

                console.log(error);
            }
        );
        return () => {
            console.log('socket Ordered disconnected')
            stompClient.disconnect();
        }
    }, []);



    useEffect(() => {
        dispatch(loadDishOrdered({ accessToken, orderId }))
    }, [])


    useEffect(() => {
        loadDataToRootOrder(rootOrder)
    }, [rootOrder])


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

    function saveDataChangeAP(newDataChange) {
        let dataForChange = {
            ...newDataChange,
            createBy: userInfo.staffCode,
            modifiedBy: userInfo.staffCode
        }
        orderApi.changeAPByOrderDishId(accessToken, dataForChange)
            .then(response => console.log("Thay đổi thành công"))
            .catch(err => console.log("Thay đổi thất bại", err))
    }



    return (
        <View style={styles.container}>
            {isLoading ? <ActivityIndicator style={{ flex: 9, marginTop: 15, alignSelf: 'center' }} size="large" color={MAIN_COLOR} /> :
                <View style={{ flex: 9 }}>
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
            <BillOverview buttonName="Thanh toán" totalAmount={rootOrder.totalAmount} totalItem={rootOrder.totalItem} />
            <OptionDishOrdered ref={optionDishRef} handleMenu={showOptionDetail} />
            <ChangeAmountAndPrice ref={changeAPRef} saveDataChangeAP={saveDataChangeAP} />
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
