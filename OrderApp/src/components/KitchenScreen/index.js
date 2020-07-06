import React, { useState, } from 'react'
import { View, TouchableOpacity } from 'react-native'
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import SideMenu from 'react-native-side-menu-updated'
import Feather from 'react-native-vector-icons/Feather';

import { MAIN_COLOR } from '../../common/color';
import KitchenByDish from './KitchenByDish';
import KitchenByTable from './KitchenByTable';
import UserSideMenu from '../UserSideMenu'

const Tab = createMaterialTopTabNavigator();

export default function OrderScreen({ navigation }) {

    const menu = <UserSideMenu navigation={navigation} />
    const [open, setOpen] = useState(false)
    function openMenu() {
        let isOpen = open;
        setOpen(!isOpen)
    }
    React.useLayoutEffect(() => {
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
                    <Tab.Screen name="Theo bàn" component={KitchenByTable} />
                    <Tab.Screen name="Theo món" component={KitchenByDish} />

                </Tab.Navigator>
            </View>
        </SideMenu>
    )
}

