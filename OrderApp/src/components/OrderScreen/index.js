import React, { useRef } from 'react'
import { StyleSheet, TouchableOpacity, View } from 'react-native'
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import Feather from 'react-native-vector-icons/Feather'
import OrderingScreen from '../OrderingScreen';
import OrderedScreen from '../OrderedScreen';
import OptionOrder from './OptionOrder';

const Tab = createMaterialTopTabNavigator();

export default function OrderScreen({ navigation }) {
    const optionOrderRef = useRef(null);
    React.useLayoutEffect(() => {
        navigation.setOptions({
            headerRight: () => (
                <TouchableOpacity style={{ marginRight: 10 }} onPress={() => optionOrderRef.current.showOptionOrderBox()}>
                    <Feather name="more-horizontal" size={40} color='white' />
                </TouchableOpacity>
            ),
        });
    }, [navigation]);
    return (
        <View style={{ flex: 1, flexDirection: 'column' }}>
            <Tab.Navigator
                swipeEnabled={false}
                tabBarOptions={{
                    activeTintColor: '#24C3A3'
                }}
            >
                <Tab.Screen name="Đang Order" component={OrderingScreen} />
                <Tab.Screen name="Đã Order" component={OrderedScreen} />
            </Tab.Navigator>
            <OptionOrder ref={optionOrderRef} />
        </View>

    )
}

const styles = StyleSheet.create({})
