import React from 'react'
import { StyleSheet, View, SectionList, ActivityIndicator, Alert } from 'react-native'
import { useSelector } from 'react-redux'
import { createSelector } from 'reselect'

import TableChildComponent from './TableChildComponent'
import DishFatherComponent from './DishFatherComponent'
import chefApi from '../../../api/chefApi'
import { MAIN_COLOR } from '../../../common/color'

export default function KitchenByDish({ route }) {
    const { userInfo } = route.params
    const { accessToken } = userInfo

    //! format data
    const listOrdersSelector = state => state.chef.listOrders
    const listOrdersByDishSelector = createSelector(
        listOrdersSelector,
        (listOrders) => {
            let listDishOrders = []
            listOrders.forEach(order => {
                order.orderDish.forEach(dish => {
                    if (listDishOrders.length == 0) {
                        if (dish.statusId != 20) {
                            listDishOrders.push({
                                dishId: dish.dishId,
                                dishName: dish.dishName,
                                statusId: dish.statusId,
                                totalQuantity: dish.quantityOk,
                                data: [
                                    {
                                        orderDishId: dish.orderDishId,
                                        orderDishOptions: dish.orderDishOptions,
                                        statusId: dish.statusId,
                                        statusValue: dish.statusValue,
                                        quantityOk: dish.quantityOk,
                                        tableId: order.tableId,
                                        tableName: order.tableName,
                                        timeOrder: order.timeOrder,
                                        comment: dish.comment
                                    }
                                ]
                            })
                        }
                    } else {
                        let isFounded = false
                        listDishOrders.forEach((dishOrder) => {
                            if (dishOrder.dishId == dish.dishId && dish.statusId != 20) {
                                if (dish.statusId == 18) {
                                    dishOrder.statusId = 18
                                }
                                dishOrder.totalQuantity += dish.quantityOk
                                dishOrder.data.push({
                                    orderDishId: dish.orderDishId,
                                    orderDishOptions: dish.orderDishOptions,
                                    statusId: dish.statusId,
                                    statusValue: dish.statusValue,
                                    quantityOk: dish.quantityOk,
                                    tableId: order.tableId,
                                    tableName: order.tableName,
                                    timeOrder: order.timeOrder,
                                    comment: dish.comment
                                })
                                isFounded = true
                            }
                        })
                        if (!isFounded && dish.statusId != 20) {
                            listDishOrders.push({
                                dishId: dish.dishId,
                                dishName: dish.dishName,
                                statusId: dish.statusId,
                                totalQuantity: dish.quantityOk,
                                data: [
                                    {
                                        orderDishId: dish.orderDishId,
                                        orderDishOptions: dish.orderDishOptions,
                                        statusId: dish.statusId,
                                        statusValue: dish.statusValue,
                                        quantityOk: dish.quantityOk,
                                        tableId: order.tableId,
                                        tableName: order.tableName,
                                        timeOrder: order.timeOrder,
                                        comment: dish.comment
                                    }
                                ]
                            })
                        }
                    }
                })
            });
            return listDishOrders
        }
    )
    const listOrders = useSelector(listOrdersByDishSelector)
    const isLoading = useSelector(state => state.chef.isLoading)

    //! user handle
    function _handleChangeStatusTable(orderDishId, statusId) {
        let newData = {
            orderDishId,
            statusId,
            chefStaffId: userInfo.staffId
        }
        chefApi.changeStatusTableByDish(accessToken, newData)
            .then(response => {
                console.log('Chuyển trạng thái thành công', response)
            })
            .catch((err) => {
                console.log('Chuyển trạng thái thất bại', err)
            })
    }
    function changeStatusTable(dishId, statusId) {
        let newData = {
            dishId,
            statusId,
            chefStaffId: userInfo.staffId
        }
        chefApi.changeStatusDishByDish(accessToken, newData)
            .then(response => {
                console.log('Chuyển trạng thái thành công', response)
            })
            .catch((err) => {
                console.log('Chuyển trạng thái thất bại', err)
            })
    }
    function _handleChangeStatusDish(dishId, dishName, quantity, statusId) {
        let title = statusId == 19 ? "Đang Thực Hiện" : "Đã Hoàn Thành"
        let message = `Chuyển ${quantity} món ${dishName} sang trạng thái ${title} ?`
        Alert.alert(
            'Cảnh báo !',
            message,
            [
                {
                    text: 'Không',
                    style: 'cancel'
                },
                { text: 'Tôi chắc chắn', onPress: () => changeStatusTable(dishId, statusId) }
            ],
            { cancelable: false }
        );
    }

    return (
        <View style={styles.container}>
            {isLoading ? <ActivityIndicator style={{ flex: 1, alignSelf: 'center' }} size="large" color={MAIN_COLOR} />
                : <SectionList
                    sections={listOrders}
                    renderSectionHeader={({ section }) => <DishFatherComponent section={section} _handleChangeStatusDish={_handleChangeStatusDish} />}
                    renderItem={({ item, index }) => <TableChildComponent item={item} index={index} _handleChangeStatusTable={_handleChangeStatusTable} />}
                    renderSectionFooter={() => <View style={{ height: 10, flex: 1 }}></View>}
                    keyExtractor={(item, index) => item.orderDishId.toString()}
                />}
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        paddingHorizontal: 8,
        backgroundColor: '#D9D9D9',
        paddingVertical: 10

    }
})
