import React from 'react'
import { StyleSheet } from 'react-native'
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import OrderingScreen from '../OrderingScreen';
import OrderedScreen from '../OrderedScreen';

const Tab = createMaterialTopTabNavigator();

export default function OrderScreen() {
    return (
        <Tab.Navigator
            swipeEnabled={false}
            tabBarOptions={{
                activeTintColor: '#24C3A3'
            }}
        >
            <Tab.Screen name="Đang Order" component={OrderingScreen} />
            <Tab.Screen name="Đã Order" component={OrderedScreen} />
        </Tab.Navigator>
    )
}

const styles = StyleSheet.create({})
