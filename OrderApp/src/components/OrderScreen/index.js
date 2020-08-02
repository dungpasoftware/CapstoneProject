import React, { useRef, useEffect, useLayoutEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { StyleSheet, TouchableOpacity, View, } from 'react-native'
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import Feather from 'react-native-vector-icons/Feather'

import { loadDishOrdered, loadDishOrderedSuccess } from '../../actions/dishOrdered'
import OrderingScreen from '../OrderingScreen';
import OrderedScreen from '../OrderedScreen';
import OptionOrder from './OptionOrder';
import { MAIN_COLOR } from '../../common/color';
import { ORDERING_SCREEN, ORDERED_SCREEN, RETURN_DISH_SCREEN, SWITCH_TABLE_SCREEN } from '../../common/screenName';


import { YellowBox } from 'react-native';
import TableOrderComment from '../ListTableScreen/TableOrderComment';
import CancelTableModal from '../ListTableScreen/CancelTableModal';

// socket
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import { changeTotalAPOrdering } from '../../actions/dishOrdering'
import { ROOT_API_CONNECTION } from '../../common/apiConnection'

YellowBox.ignoreWarnings([
    'Non-serializable values were found in the navigation state',
]);



const Tab = createMaterialTopTabNavigator();

export default function OrderScreen({ route, navigation }) {
    const dispatch = useDispatch()
    // const dispatch = useDispatch()
    const { userInfo, status, orderId, tableName } = route.params;
    const { accessToken } = userInfo
    const rootOrder = useSelector(state => state.dishOrdered.rootOrder)

    var isShow = false;

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





    const tableOrderCommentRef = useRef(null);
    const _handleShowOptionOrderBox = () => {
        isShow ? optionOrderRef.current.closeOptionOrderBox() : optionOrderRef.current.showOptionOrderBox()
        isShow = !isShow
    }

    const optionOrderRef = useRef(null);
    function showTableOrderCommentBox(itemSelected) {
        tableOrderCommentRef.current.showTableOrderCommentBox(itemSelected);
    }

    const cancelTableOrderRef = useRef(null)
    function showCancelTableModal(item) {
        cancelTableOrderRef.current.showCancelTableModal(item);
    }

    React.useLayoutEffect(() => {
        navigation.setOptions({
            title: tableName,
            headerRight: rootOrder.statusId == 14 ? null : () => (
                <TouchableOpacity style={{ marginRight: 10 }} onPress={_handleShowOptionOrderBox}>
                    <Feather name="more-horizontal" size={40} color='white' />
                </TouchableOpacity>
            ),
        });
    }, [navigation, rootOrder, tableName]);

    function selectOptionMenu(index) {
        switch (index) {
            case 1: {
                showTableOrderCommentBox(rootOrder)
                break;
            }
            case 2: {
                navigation.navigate(SWITCH_TABLE_SCREEN, { userInfo, rootOrder, status })
                break;
            }
            case 3: {
                navigation.navigate(RETURN_DISH_SCREEN, { userInfo, orderId: rootOrder.orderId })
                break;
            }
            case 4: {
                showCancelTableModal(rootOrder)
                break;
            }

            default:
                console.log(rootOrder)
                break;
        }
    }


    return (
        <View style={{ flex: 1, flexDirection: 'column' }}>
            <Tab.Navigator
                initialRouteName={status == "ORDERED" ? ORDERED_SCREEN : ORDERING_SCREEN}
                swipeEnabled={false}
                tabBarOptions={{
                    activeTintColor: '#24C3A3',
                    inactiveTintColor: 'rgb(175,180,183)',
                    labelStyle: { fontSize: 16, fontWeight: '700' },
                    indicatorStyle: { backgroundColor: MAIN_COLOR },

                }}

            >
                <Tab.Screen
                    name={ORDERING_SCREEN}
                    options={{
                        title: 'Đang Order'
                    }}
                    component={OrderingScreen}
                    initialParams={{ userInfo }}
                    listeners={{
                        tabPress: e => {
                            if (rootOrder.statusId == 14) {
                                e.preventDefault();
                            }
                        },
                    }}
                />
                <Tab.Screen
                    name={ORDERED_SCREEN}
                    options={{ title: 'Đã Order' }}
                    component={OrderedScreen}
                    initialParams={{ userInfo }}
                />
            </Tab.Navigator>
            <OptionOrder ref={optionOrderRef} selectOptionMenu={selectOptionMenu} statusOrder={rootOrder.statusId} />
            <TableOrderComment accessToken={accessToken} ref={tableOrderCommentRef} />
            <CancelTableModal userInfo={userInfo} ref={cancelTableOrderRef} navigation={navigation} />
        </View>

    )
}

