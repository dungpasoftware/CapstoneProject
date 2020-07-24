import React from 'react'
import { StyleSheet, View, FlatList } from 'react-native'
import { useSelector, useDispatch } from 'react-redux'

import OrderedItem from './OrderedItem'
import BillOverview from './BillOverView'
import { saveOrder } from '../../actions/dishOrdering'
import { ORDERED_SCREEN } from '../../common/screenName'


export default function OrderAndBill({ showToppingBox, userInfo, navigation }) {

    const dispatch = useDispatch()
    const { accessToken } = userInfo
    const rootOrder = useSelector(state => state.dishOrdering.rootOrder)
    const { orderDish, totalAmount, totalItem } = rootOrder

    const handleSaveOrder = () => {
        let newRootOrder = { ...rootOrder }
        newRootOrder.orderDish = newRootOrder.orderDish.map(orderDishItem => {
            return {
                ...orderDishItem,
                createBy: userInfo.staffCode,
                orderDishOptions: orderDishItem.orderDishOptions.filter(option => option.quantity > 0)
            }
        })
        // console.log("newOrder", newRootOrder)
        dispatch(saveOrder({ accessToken, rootOrder: newRootOrder }))
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
