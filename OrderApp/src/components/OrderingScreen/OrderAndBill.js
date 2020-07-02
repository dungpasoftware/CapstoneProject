import React from 'react'
import { StyleSheet, Text, View, FlatList } from 'react-native'
import { useSelector } from 'react-redux'

import OrderedItem from './OrderedItem'
import BillOverview from './BillOverView'


export default function OrderAndBill({ showToppingBox }) {

    const listDish = useSelector(state => state.dishOrdering.listDish)

    return (
        <View style={styles.container}>
            <View style={styles.orderedContainer}>
                <FlatList
                    data={listDish}
                    keyExtractor={(item, index) => item.id}
                    renderItem={({ item, index }) => {
                        return (
                            <OrderedItem item={item} index={index} showToppingBox={showToppingBox} />
                        )
                    }}
                />
            </View>
            <BillOverview buttonName="Lưu" />
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
