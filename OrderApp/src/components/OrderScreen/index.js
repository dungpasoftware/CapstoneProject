import React, { useRef } from 'react'
import { StyleSheet, TouchableOpacity, View } from 'react-native'
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import Feather from 'react-native-vector-icons/Feather'
import OrderingScreen from '../OrderingScreen';
import OrderedScreen from '../OrderedScreen';
import OptionOrder from './OptionOrder';
import { MAIN_COLOR } from '../../common/color';

const Tab = createMaterialTopTabNavigator();

export default function OrderScreen({ route, navigation }) {
    const { accessToken, status } = route.params;

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
                initialRouteName={status == "ORDERED" ? "Đã Order" : "Đang Order"}
                swipeEnabled={false}
                tabBarOptions={{
                    activeTintColor: '#24C3A3',
                    inactiveTintColor: 'rgb(175,180,183)',
                    labelStyle: { fontSize: 16, fontWeight: '700' },
                    indicatorStyle: { backgroundColor: MAIN_COLOR }
                }}
            >
                <Tab.Screen
                    name="Đang Order"
                    component={OrderingScreen}
                    initialParams={{ accessToken: accessToken }}
                />
                <Tab.Screen
                    name="Đã Order"
                    component={OrderedScreen}
                    initialParams={{ accessToken: accessToken }}
                />
            </Tab.Navigator>
            <OptionOrder ref={optionOrderRef} navigation={navigation} />
        </View>

    )
}

const styles = StyleSheet.create({})
