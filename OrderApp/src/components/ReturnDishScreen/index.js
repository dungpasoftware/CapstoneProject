import React, { useState, useEffect, } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { StyleSheet, Text, View, FlatList, TouchableOpacity, Dimensions, ActivityIndicator } from 'react-native'

import DishReturnComponent from './DishReturnComponent'
import { MAIN_COLOR } from '../../common/color'
import { loadOrderDishReturn } from '../../actions/dishReturn'
import orderApi from '../../api/orderApi'

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
                    quantityOk: dish.quantityOk,
                    dish: dish.dish,
                    orderDishOptions: dish.orderDishOptions
                }
            })
            setListReturn(newList)
        }
        _formatDishReturn()
    }, [listDishReturn])


    function _handleChangeAmount(index, orderDishId, type, valueChange) {
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
        let listReturnSubmit = listReturn.map((dish) => {
            return {
                orderDishId: dish.orderDishId,
                quantityReturn: dish.quantityReturn,
                orderId: dish.orderId,
                modifiedBy: dish.modifiedBy
            }
        })
        // console.log(listReturnSubmit)
        orderApi.saveReturnDish(userInfo.accessToken, listReturnSubmit).then((response) => {
            console.log("Save list return dish thành công")
            setListReturn([])
            setIsLoading(false)
            navigation.goBack()
        }).catch((err) => {
            console.log('lỗi save list return dish', err)
            setIsLoading(false)
        })
    }

    return (
        <View style={styles.container}>
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
            {isLoading ? <ActivityIndicator style={{ marginTop: 15, alignSelf: 'center' }} size="large" color={MAIN_COLOR} /> :
                <TouchableOpacity
                    onPress={_handleSubmitReturnDish}
                    style={{
                        height: 45,
                        width: maxWidth / 2,
                        alignSelf: 'center',
                        backgroundColor: MAIN_COLOR,
                        marginVertical: 30,
                        justifyContent: 'center'
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
