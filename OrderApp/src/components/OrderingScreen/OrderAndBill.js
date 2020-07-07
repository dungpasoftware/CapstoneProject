import React from 'react'
import { StyleSheet, View, FlatList } from 'react-native'
import { useSelector } from 'react-redux'

import OrderedItem from './OrderedItem'
import BillOverview from './BillOverView'
import orderRequest from '../../api/orderRequest'


export default function OrderAndBill({ showToppingBox, accessToken }) {

    const rootOrder = useSelector(state => state.dishOrdering.rootOrder)
    const { orderDish, totalAmount, totalItem } = rootOrder

    const saveOrder = () => {
        orderRequest.saveOrder(accessToken, rootOrder)
    }

    return (
        <View style={styles.container}>
            <View style={styles.orderedContainer}>
                <FlatList
                    data={orderDish}
                    keyExtractor={(item, index) => item.orderDishId.toString()}
                    renderItem={({ item, index }) => {
                        return (
                            <OrderedItem item={item} index={index} showToppingBox={showToppingBox} />
                        )
                    }}
                />
            </View>
            <BillOverview buttonName="Lưu" totalAmount={totalAmount} totalItem={totalItem} saveOrder={saveOrder} />
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 5,
        flexDirection: 'column'
    },
    orderedContainer: {
        flex: 7
    }
})
