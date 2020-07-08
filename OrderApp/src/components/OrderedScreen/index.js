import React, { useRef, useEffect, useState } from 'react'
import { StyleSheet, Text, View, FlatList } from 'react-native'
import { useSelector } from 'react-redux'


import Ordered2Item from './Ordered2Item'
import BillOverview from '../OrderingScreen/BillOverView'
import OptionDishOrdered from './OptionDishOrdered'
import orderRequest from '../../api/orderRequest'

export default function OrderedScreen({ route }) {
    const { accessToken } = route.params
    const orderId = useSelector(state => state.dishOrdering.rootOrder.orderId)
    const [ordered, setOrdered] = useState({})

    useEffect(() => {
        async function loadDishOrdered() {
            const response = await orderRequest.loadDishOrderdByOrderId(accessToken, orderId)
            await setOrdered(response.dishOrderedAPI)
        };
        loadDishOrdered()
    }, [orderId])

    const optionDishRef = useRef(null);
    function showOptionDish() {
        optionDishRef.current.showOptionDishBox();
    }
    return (
        <View style={styles.container}>
            <View style={{ flex: 9 }}>
                <FlatList
                    data={ordered.orderDish}
                    keyExtractor={(item) => item.orderDishId.toString()}
                    renderItem={({ item }) => {
                        return (
                            <Ordered2Item item={item} showOptionDish={showOptionDish} />
                        )
                    }}
                />
            </View>
            <BillOverview buttonName="Thanh toán" totalAmount={ordered.totalAmount} totalItem={ordered.totalItem} />
            <OptionDishOrdered ref={optionDishRef} />
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        backgroundColor: 'white'

    }
})
