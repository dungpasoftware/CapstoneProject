import React, { useRef } from 'react'
import { StyleSheet, View, } from 'react-native'

import ToppingBox from '../OrderScreen/ToppingBox'
import OrderAndBill from './OrderAndBill'
import CategoryAndDish from './CategoryAndDish'



export default function OrderingScreen({ route, navigation }) {

    const { userInfo } = route.params
    const { accessToken } = userInfo
    const toppingBoxRef = useRef(null)
    function showToppingBox(item) {
        toppingBoxRef.current.showToppingBox(item);
    }

    return (
        <View style={styles.container}>
            <OrderAndBill showToppingBox={showToppingBox} userInfo={userInfo} navigation={navigation} />
            <CategoryAndDish showToppingBox={showToppingBox} accessToken={accessToken} />
            <ToppingBox ref={toppingBoxRef} />
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        backgroundColor: 'white'
    },

})