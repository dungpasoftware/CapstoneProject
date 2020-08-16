import React, { useState, useEffect, } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { StyleSheet, Text, View, FlatList, TouchableOpacity, Dimensions, ActivityIndicator } from 'react-native'

import DishReturnComponent from './DishReturnComponent'
import { MAIN_COLOR } from '../../common/color'
import { loadOrderDishReturn } from '../../actions/dishReturn'
import orderApi from '../../api/orderApi'
import { showToast } from '../../common/functionCommon'

export default function ReturnDishScreen({ route, navigation }) {
    const maxWidth = Dimensions.get('window').width
    const { userInfo, orderId } = route.params;
    const { accessToken } = userInfo
    const listDishReturn = useSelector(state => state.dishReturn.listDishReturn)
    const [listReturn, setListReturn] = useState([])
    const [isLoading, setIsLoading] = useState(false)

    const dispatch = useDispatch()

    useEffect(() => {
        async function _dishpatchActionLoadDishReturn() {
            dispatch(loadOrderDishReturn({
                accessToken,
                orderId
            }))
        }
        _dishpatchActionLoadDishReturn()
    }, [])


    useEffect(() => {
        async function _formatDishReturn() {
            let newList = [...listDishReturn]
            newList = newList.map(dish => {
                let quantityReturn = 0
                if (listReturn.length > 0) {
                    let oldDishNeed = listReturn.find(oldDish => oldDish.orderDishId == dish.orderDishId)
                    if (oldDishNeed != undefined) {
                        quantityReturn = oldDishNeed.quantityReturn > dish.quantityOk ? dish.quantityOk : oldDishNeed.quantityReturn
                    }
                }
                return {
                    orderDishId: dish.orderDishId,
                    quantityReturn: quantityReturn,
                    orderId: dish.orderOrderId,
                    modifiedBy: userInfo.staffCode,
                    createdBy: userInfo.staffCode,
                    quantityOk: dish.quantityOk,
                    dish: dish.dish,
                    orderDishOptions: dish.orderDishOptions
                }
            })
            setListReturn(newList)
        }
        _formatDishReturn()
    }, [listDishReturn])


    function _handleChangeAmount(index, type, valueChange) {
        const oldAmount = listReturn[index].quantityReturn
        let newAmount = oldAmount + valueChange
        if (type == 'sub' && newAmount < 0) {
            return
        }
        if (type == 'add' && newAmount > listReturn[index].quantityOk) {
            return
        }
        let newDishReturn = {
            ...listReturn[index],
            quantityReturn: newAmount
        }
        let newListReturn = [...listReturn]
        newListReturn[index] = newDishReturn
        setListReturn(newListReturn)

    }

    function _handleSubmitReturnDish() {
        setIsLoading(true)
        let isHaveChange = false;
        let listReturnSubmit = listReturn.map((dish) => {
            if (dish.quantityReturn > 0) isHaveChange = true
            return {
                orderDishId: dish.orderDishId,
                quantityReturn: dish.quantityReturn,
                orderId: dish.orderId,
                modifiedBy: dish.modifiedBy,
                createdBy: dish.createdBy,
            }
        })
        if (!isHaveChange) {
            setIsLoading(false)
            setListReturn([])
            showToast("Không có món nào được trả!")
            navigation.goBack()
            return

        }
        orderApi.saveReturnDish(userInfo.accessToken, listReturnSubmit).then((response) => {
            showToast("Trả món thành công")
            setListReturn([])
            setIsLoading(false)
            navigation.goBack()
        }).catch((err) => {
            if (err == 'timeout') {
                showToast("Có lỗi xảy ra!, Trả món thất bại!")
            } else {
                showToast("Có lỗi xảy ra!, Trả món thất bại!")
            }
            setIsLoading(false)
        })
    }

    return (
        <View style={styles.container}>
            {listReturn.length <= 0 ?
                <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
                    <Text style={{ fontSize: 16 }}>{'Không có món nào đủ điều kiện trả'}</Text>
                </View>
                :
                <FlatList
                    style={{ flex: 1 }}
                    data={listReturn}
                    keyExtractor={(item) => item.orderDishId.toString()}
                    renderItem={({ item, index }) => {
                        if (item.quantityOk <= 0) return
                        return (
                            <DishReturnComponent handleChangeAmount={_handleChangeAmount} item={item} index={index} />
                        )
                    }}
                />
            }
            {isLoading ? <ActivityIndicator style={{ height: 45, alignSelf: 'center', marginVertical: 30, }} size="large" color={MAIN_COLOR} /> :
                <TouchableOpacity
                    onPress={_handleSubmitReturnDish}
                    style={{
                        height: 45,
                        width: maxWidth / 2,
                        alignSelf: 'center',
                        backgroundColor: MAIN_COLOR,
                        marginVertical: 30,
                        justifyContent: 'center',
                        borderRadius: 5
                    }}
                >
                    <Text style={{ color: 'white', textAlign: 'center', fontSize: 18, fontWeight: '700' }}>Trả Món</Text>
                </TouchableOpacity>}

        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
    }
})
