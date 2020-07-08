import React from 'react'
import { StyleSheet, View, FlatList } from 'react-native'
import { useSelector, useDispatch } from 'react-redux'

import OrderedItem from './OrderedItem'
import BillOverview from './BillOverView'
import { saveOrder } from '../../actions/dishOrdering'
import { ORDERED_SCREEN } from '../../common/screenName'


export default function OrderAndBill({ showToppingBox, accessToken, navigation }) {

    const dispatch = useDispatch()
    const rootOrder = useSelector(state => state.dishOrdering.rootOrder)
    const { orderDish, totalAmount, totalItem, orderId } = rootOrder

    const handleSaveOrder = () => {
        console.log("data", rootOrder)

        dispatch(saveOrder({ accessToken, rootOrder }))
        navigation.navigate(ORDERED_SCREEN)
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
