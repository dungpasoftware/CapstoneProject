import React, { useRef, useEffect, useLayoutEffect } from 'react'
import { StyleSheet, TouchableOpacity, View, ActivityIndicator } from 'react-native'
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import Feather from 'react-native-vector-icons/Feather'
import { useSelector, useDispatch, shallowEqual } from 'react-redux'

import OrderingScreen from '../OrderingScreen';
import { loadDishOrdered } from '../../actions/dishOrdered'
import OrderedScreen from '../OrderedScreen';
import OptionOrder from './OptionOrder';
import { MAIN_COLOR } from '../../common/color';
import { ORDERING_SCREEN, ORDERED_SCREEN } from '../../common/screenName';



const Tab = createMaterialTopTabNavigator();

export default function OrderScreen({ route, navigation }) {
    const dispatch = useDispatch()
    const { accessToken, status, orderId } = route.params;
    var isShow = false;
    // const orderId = useSelector(state => state.dishOrdering.rootOrder.orderId)
    const rootOrder = useSelector(state => state.dishOrdered.rootOrder)

    useLayoutEffect(() => {
        async function handleLoadDishOrdered() {
            await dispatch(loadDishOrdered({ accessToken, orderId }))
        };
        handleLoadDishOrdered()
    }, [orderId])

    const _handleShowOptionOrderBox = () => {
        isShow ? optionOrderRef.current.closeOptionOrderBox() : optionOrderRef.current.showOptionOrderBox()
        isShow = !isShow
    }

    const optionOrderRef = useRef(null);
    React.useLayoutEffect(() => {
        navigation.setOptions({
            title: rootOrder.tableName,
            headerRight: () => (
                <TouchableOpacity style={{ marginRight: 10 }} onPress={_handleShowOptionOrderBox}>
                    <Feather name="more-horizontal" size={40} color='white' />
                </TouchableOpacity>
            ),
        });
    }, [navigation, rootOrder]);



    return (
        <View style={{ flex: 1, flexDirection: 'column' }}>
            {/* {isLoading ? <ActivityIndicator style={{ marginTop: 15, alignSelf: 'center' }} size="large" color={MAIN_COLOR} /> : */}
            <Tab.Navigator
                initialRouteName={status == "ORDERED" ? ORDERED_SCREEN : ORDERING_SCREEN}
                swipeEnabled={false}
                tabBarOptions={{

                    activeTintColor: '#24C3A3',
                    inactiveTintColor: 'rgb(175,180,183)',
                    labelStyle: { fontSize: 16, fontWeight: '700' },
                    indicatorStyle: { backgroundColor: MAIN_COLOR }
                }}
            >
                <Tab.Screen
                    name={ORDERING_SCREEN}
                    options={{ title: 'Đang Order' }}
                    component={OrderingScreen}
                    initialParams={{ accessToken: accessToken }}
                />
                <Tab.Screen
                    name={ORDERED_SCREEN}
                    options={{ title: 'Đã Order' }}
                    component={OrderedScreen}
                    initialParams={{ accessToken: accessToken, rootOrdered: rootOrder }}
                />
            </Tab.Navigator>
            <OptionOrder ref={optionOrderRef} navigation={navigation} />
        </View>

    )
}

const styles = StyleSheet.create({})
