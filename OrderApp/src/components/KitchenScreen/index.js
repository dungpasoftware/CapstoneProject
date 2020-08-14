import React, { useState, useEffect, useLayoutEffect } from 'react'
import { useDispatch } from 'react-redux'
import { View, TouchableOpacity } from 'react-native'
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import SideMenu from 'react-native-side-menu-updated'
import Feather from 'react-native-vector-icons/Feather';

import { MAIN_COLOR } from '../../common/color';
import KitchenByDish from './KitchenByDish';
import KitchenByTable from './KitchenByTable';
import UserSideMenu from '../UserSideMenu'
import { loadAllOrder, socketLoadAllOrder } from '../../actions/chefAction';

// socket
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import { ROOT_API_CONNECTION } from '../../common/apiConnection';

const Tab = createMaterialTopTabNavigator();

export default function OrderScreen({ route, navigation }) {
    const { userInfo } = route.params;
    const { accessToken } = userInfo
    const dispatch = useDispatch()

    useLayoutEffect(() => {
        dispatch(loadAllOrder({ accessToken }))
    }, [])

    useEffect(() => {
        let socket = new SockJS(`${ROOT_API_CONNECTION}/rms-websocket`);
        let stompClient = Stomp.over(socket);
        //!function support
        function successCallback(stompClient) {
            console.log('socket Chef connected');
            stompClient.subscribe("/topic/chef", ({ body }) => {
                let listOrders = JSON.parse(body);
                console.log('socket kitchen run', listOrders)
                dispatch(socketLoadAllOrder({ listOrders }))
            });
        }
        //!main function
        function connectAndReconnect(successCallback) {
            socket = new SockJS(`${ROOT_API_CONNECTION}/rms-websocket`);
            stompClient = Stomp.over(socket);
            stompClient.debug = () => { }
            stompClient.connect(
                {
                    token: accessToken
                },
                frame => {
                    successCallback(stompClient)
                },
                error => {
                    console.log('socket kitchen die')
                    setTimeout(() => {
                        connectAndReconnect(successCallback);
                    }, 5000);
                }
            );
        }
        //! call function
        connectAndReconnect(successCallback)
        //! when unmount
        return () => {
            console.log('Socket kitchen disconnected')
            stompClient.disconnect();
        }
    }, []);

    const menu = <UserSideMenu openMenu={openMenu} userInfo={userInfo} navigation={navigation} />
    const [open, setOpen] = useState(false)
    function openMenu() {
        let isOpen = open;
        setOpen(!isOpen)
    }

    useLayoutEffect(() => {
        navigation.setOptions({
            headerLeft: null,
            headerRight: () => (
                <TouchableOpacity
                    style={{ marginRight: 10 }}
                    onPress={() => openMenu()}
                >
                    <Feather name="menu" size={40} color='white' />
                </TouchableOpacity>
            ),
        });
    });

    return (
        <SideMenu
            menu={menu}
            isOpen={open}
            menuPosition='right'
            disableGestures={true}
            onChange={() => setOpen(!open)}
        >
            <View style={{ flex: 1, flexDirection: 'column' }}>
                <Tab.Navigator
                    tabBarOptions={{
                        activeTintColor: '#24C3A3',
                        inactiveTintColor: 'rgb(175,180,183)',
                        labelStyle: { fontSize: 16, fontWeight: '700' },
                        indicatorStyle: { backgroundColor: MAIN_COLOR },
                    }}
                >
                    <Tab.Screen
                        name="Theo bàn"
                        component={KitchenByTable}
                        initialParams={{ userInfo }}
                    />
                    <Tab.Screen
                        name="Theo món"
                        component={KitchenByDish}
                        initialParams={{ userInfo }}
                    />

                </Tab.Navigator>
            </View>
        </SideMenu>
    )
}

