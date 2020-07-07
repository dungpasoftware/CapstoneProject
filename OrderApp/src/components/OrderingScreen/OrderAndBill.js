import React from 'react'
import { StyleSheet, View, FlatList } from 'react-native'
import { useSelector } from 'react-redux'

import OrderedItem from './OrderedItem'
import BillOverview from './BillOverView'


export default function OrderAndBill({ showToppingBox }) {

    const listDish = useSelector(state => state.dishOrdering.orderDish)

    function caculatateOrder(listDish) {
        if (typeof listDish !== 'undefined' && listDish.length > 0) {
            var orderResult = listDish.reduce(function (accumulator, currentValue) {
                accumulator.totalAmount += currentValue.amount
                accumulator.totalPrice += currentValue.price * currentValue.amount
                return accumulator
            }, { totalPrice: 0, totalAmount: 0 })
            return orderResult
        }
        return { totalPrice: 0, totalAmount: 0 }
    }

    return (
        <View style={styles.container}>
            <View style={styles.orderedContainer}>
                <FlatList
                    data={listDish}
                    keyExtractor={(item, index) => item.orderDishId}
                    renderItem={({ item, index }) => {
                        return (
                            <OrderedItem item={item} index={index} showToppingBox={showToppingBox} />
                        )
                    }}
                />
            </View>
            <BillOverview buttonName="Lưu" orderResult={caculatateOrder(listDish)} />
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
