import React, { useRef } from 'react'
import { StyleSheet, View, } from 'react-native'

import ToppingBox from '../OrderScreen/ToppingBox'
import OrderAndBill from './OrderAndBill'
import CategoryAndDish from './CategoryAndDish'



export default function OrderingScreen({ route }) {

    const { accessToken } = route.params
    const toppingBoxRef = useRef(null)
    function showToppingBox() {
        toppingBoxRef.current.showToppingBox();
    }

    return (
        <View style={styles.container}>
            <OrderAndBill showToppingBox={showToppingBox} />
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
