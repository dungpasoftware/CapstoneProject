import React, { useRef, useEffect } from 'react'
import { StyleSheet, Text, View, FlatList } from 'react-native'
import { useSelector, useDispatch } from 'react-redux'


import Ordered2Item from './Ordered2Item'
import BillOverview from '../OrderingScreen/BillOverView'
import OptionDishOrdered from './OptionDishOrdered'
import ChangeAmountAndPrice from './ChangeAmountAndPrice'
import { loadDishOrdered } from '../../actions/dishOrdered'
import orderRequest from '../../api/orderRequest'

export default function OrderedScreen({ route }) {
    const dispatch = useDispatch()
    const { accessToken } = route.params
    const orderId = useSelector(state => state.dishOrdering.rootOrder.orderId)
    const rootOrdered = useSelector(state => state.dishOrdered.rootOrder)

    useEffect(() => {
        async function handleLoadDishOrdered() {
            await dispatch(loadDishOrdered({ accessToken, orderId }))
        };
        handleLoadDishOrdered()
    }, [orderId])
    const optionDishRef = useRef(null);
    const changeAPRef = useRef(null);
    function showOptionDish(item) {
        optionDishRef.current.showOptionDishBox(item);
    }
    function showOptionDetail(option, itemSelected) {
        changeAPRef.current.showChangeAPRefBox(itemSelected);
    }

    function saveDataChangeAP(newDataChange) {
        orderRequest.changeAPByOrderDishId(accessToken, newDataChange)
            .then(response => console.log("Thay đổi thành công"))
            .catch(err => console.log("Thay đổi thất bại"))
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
            <OptionDishOrdered ref={optionDishRef} handleMenu={showOptionDetail} />
            <ChangeAmountAndPrice ref={changeAPRef} saveDataChangeAP={saveDataChangeAP} />
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