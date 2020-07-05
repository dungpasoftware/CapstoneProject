import React from 'react'
import { View, SafeAreaView } from 'react-native'
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';

import { MAIN_COLOR } from '../../common/color';
import KitchenByDish from './KitchenByDish';
import KitchenByTable from './KitchenByTable';

const Tab = createMaterialTopTabNavigator();

export default function OrderScreen() {

    return (
        <SafeAreaView style={{ flex: 1, flexDirection: 'column' }}>
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
        </SafeAreaView>
    )
}

