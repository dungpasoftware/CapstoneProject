import React, { useEffect, useState } from 'react'
import { StyleSheet, View, FlatList, Alert, Text } from 'react-native'
import { useSelector, useDispatch } from 'react-redux'

import OrderedItem from './OrderedItem'
import BillOverview from './BillOverView'
import { saveOrder } from '../../actions/dishOrdering'
import { ORDERED_SCREEN } from '../../common/screenName'
import { showToast } from '../../common/functionCommon'


export default function OrderAndBill({ showToppingBox, userInfo, navigation }) {

    const dispatch = useDispatch()
    const { accessToken } = userInfo
    const rootOrder = useSelector(state => state.dishOrdering.rootOrder)
    const saveOrderIsLoading = useSelector(state => state.dishOrdering.saveOrderIsLoading)
    const error = useSelector(state => state.dishOrdering.error)
    const message = useSelector(state => state.dishOrdering.message)
    const { orderDish, totalAmount, totalItem } = rootOrder
    const [toSaving, setToSaving] = useState(false)

    const handleSaveOrder = () => {
        let newRootOrder = { ...rootOrder }
        if (newRootOrder.orderDish.length == 0) {
            Alert.alert(
                "Lỗi!",
                "Danh sách món ăn được chọn trống.",
                [
                    { text: "Tôi hiểu" }
                ],
                { cancelable: false }
            );
            return
        }
        newRootOrder.orderDish = newRootOrder.orderDish.map(orderDishItem => {
            return {
                ...orderDishItem,
                createBy: userInfo.staffCode,
                orderDishOptions: orderDishItem.orderDishOptions.filter(option => option.quantity > 0)
            }
        })
        dispatch(saveOrder({ accessToken, rootOrder: newRootOrder }))
        setToSaving(true)

    }
    useEffect(() => {
        if (toSaving == true && saveOrderIsLoading == false) {
            if (message != null) {
                let title = 'Tối đa từng món có thể làm:'
                setToSaving(false)
                Alert.alert(
                    title,
                    message,
                    [
                        { text: "Tôi hiểu" }
                    ],
                    { cancelable: false }
                );
            } else {
                if (error == null) {
                    setToSaving(false)
                    navigation.navigate(ORDERED_SCREEN)
                } else {
                    setToSaving(false)
                    if (error == "timeout") {
                        showToast("Lỗi mạng! Lưu order thất bại!")
                    } else {
                        showToast("Có lỗi gì đó xảy ra!")
                    }
                }

            }

        }
    }, [saveOrderIsLoading])

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

            <BillOverview isLoading={saveOrderIsLoading} buttonName="Lưu" totalAmount={totalAmount} totalItem={totalItem} handle={handleSaveOrder} />
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 5,
        flexDirection: 'column',
    },
    orderedContainer: {
        flex: 7,
    }
})
