import React, { useRef, useEffect, useLayoutEffect } from 'react'
import { StyleSheet, View, FlatList } from 'react-native'
import { useSelector, useDispatch } from 'react-redux'

import { loadDishOrdered } from '../../actions/dishOrdered'
import Ordered2Item from './Ordered2Item'
import BillOverview from '../OrderingScreen/BillOverView'
import OptionDishOrdered from './OptionDishOrdered'
import ChangeAmountAndPrice from './ChangeAmountAndPrice'
import ChangeTopping from './ChangeTopping'
import orderRequest from '../../api/orderRequest'


export default function OrderedScreen({ route }) {
    const dispatch = useDispatch()
    const { accessToken, orderId, loadDataToRootOrder } = route.params
    const rootOrdered = useSelector(state => state.dishOrdered.rootOrder)
    useLayoutEffect(() => {
        dispatch(loadDishOrdered({ accessToken, orderId }))
    }, [])

    useEffect(() => {
        loadDataToRootOrder(rootOrdered)

    }, [rootOrdered])

    const optionDishRef = useRef(null);
    const changeAPRef = useRef(null);
    function showOptionDish(item) {
        optionDishRef.current.showOptionDishBox(item);
    }

    const changeToppingRef = useRef(null)
    function showChangeTopping(item) {
        changeToppingRef.current.showChangeTopping(item);
    }


    function showOptionDetail(option, itemSelected) {
        switch (option) {
            case 1: {
                changeAPRef.current.showChangeAPRefBox(itemSelected);
                break;
            }
            case 2: {
                changeToppingRef.current.showChangeTopping(itemSelected)
                break
            }
            default: console.log(itemSelected)
                break;
        }

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
            <ChangeTopping ref={changeToppingRef} accessToken={accessToken} />
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
