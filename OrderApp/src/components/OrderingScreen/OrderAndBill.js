import React from 'react'
import { StyleSheet, View, FlatList } from 'react-native'
import { useSelector, useDispatch } from 'react-redux'

import OrderedItem from './OrderedItem'
import BillOverview from './BillOverView'
import { saveOrder } from '../../actions/dishOrdering'


export default function OrderAndBill({ showToppingBox, accessToken }) {

    const dispatch = useDispatch()
    const rootOrder = useSelector(state => state.dishOrdering.rootOrder)
    const { orderDish, totalAmount, totalItem } = rootOrder

    const handleSaveOrder = () => {
        console.log("data", rootOrder)
        dispatch(saveOrder({ accessToken, rootOrder }))
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
            <BillOverview buttonName="Lưu" totalAmount={totalAmount} totalItem={totalItem} handleSaveOrder={handleSaveOrder} />
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
