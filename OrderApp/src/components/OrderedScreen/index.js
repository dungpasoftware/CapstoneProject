import React, { useRef, useEffect } from 'react'
import { StyleSheet, Text, View, FlatList } from 'react-native'
import { useSelector, useDispatch } from 'react-redux'


import Ordered2Item from './Ordered2Item'
import BillOverview from '../OrderingScreen/BillOverView'
import OptionDishOrdered from './OptionDishOrdered'
import { loadDishOrdered } from '../../actions/dishOrdered'

export default function OrderedScreen({ route }) {
    const dispatch = useDispatch()
    const { accessToken } = route.params
    const orderId = useSelector(state => state.dishOrdering.rootOrder.orderId)
    const rootOrdered = useSelector(state => state.dishOrdering.rootOrder)

    useEffect(() => {
        async function handleLoadDishOrdered() {
            dispatch(loadDishOrdered(accessToken, orderId))
        };
        handleLoadDishOrdered()
    }, [orderId])

    const optionDishRef = useRef(null);
    function showOptionDish() {
        optionDishRef.current.showOptionDishBox();
    }
    return (
        <View style={styles.container}>
            <View style={{ flex: 9 }}>
                <FlatList
                    data={rootOrdered.orderDish}
                    keyExtractor={(item) => item.orderDishId.toString()}
                    renderItem={({ item }) => {
                        return (
                            <Ordered2Item item={item} showOptionDish={showOptionDish} />
                        )
                    }}
                />
            </View>
            <BillOverview buttonName="Thanh toán" totalAmount={rootOrdered.totalAmount} totalItem={rootOrdered.totalItem} />
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
