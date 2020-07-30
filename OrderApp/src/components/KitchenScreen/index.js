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
        stompClient.heartbeat.outgoing = 20000;
        stompClient.heartbeat.incoming = 20000;
        stompClient.debug = () => { }
        stompClient.connect(
            {
                token: accessToken
            },
            frame => {
                console.log('socket Chef connected');
                stompClient.subscribe("/topic/chef", ({ body }) => {
                    let listOrders = JSON.parse(body);
                    console.log('socket chef run', listOrders)
                    dispatch(socketLoadAllOrder({ listOrders }))
                });
            },
            error => {
                console.log('Socket chef die')
                console.log(error);
            }
        );

        return () => {
            console.log('Socket chef disconnected')
            stompClient.disconnect();
        }
    }, []);

    const menu = <UserSideMenu openMenu={openMenu} navigation={navigation} />
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
                        labelStyle: { color: 'white', fontSize: 15, fontWeight: '800' },
                        indicatorStyle: { backgroundColor: 'white', height: 5 },
                        style: { backgroundColor: MAIN_COLOR }
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

