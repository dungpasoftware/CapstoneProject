import React from 'react'
import { StyleSheet, View, SectionList } from 'react-native'
import { useSelector } from 'react-redux'
import { createSelector } from 'reselect'

import TableChildComponent from './TableChildComponent'
import DishFatherComponent from './DishFatherComponent'

export default function KitchenByDish() {
    const listOrdersSelector = state => state.chef.listOrders
    const listOrdersByDishSelector = createSelector(
        listOrdersSelector,
        (listOrders) => {
            let listDishOrders = []
            listOrders.forEach(order => {
                order.orderDish.forEach(dish => {
                    if (listDishOrders.length == 0) {
                        listDishOrders.push({
                            dishId: dish.dishId,
                            dishName: dish.dishName,
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
                                    timeOrder: order.timeOrder
                                }
                            ]
                        })
                    } else {
                        let isFounded = false
                        listDishOrders.forEach((dishOrder) => {
                            if (dishOrder.dishId == dish.dishId) {
                                dishOrder.totalQuantity += dish.quantityOk
                                dishOrder.data.push({
                                    orderDishId: dish.orderDishId,
                                    orderDishOptions: dish.orderDishOptions,
                                    statusId: dish.statusId,
                                    statusValue: dish.statusValue,
                                    quantityOk: dish.quantityOk,
                                    tableId: order.tableId,
                                    tableName: order.tableName,
                                    timeOrder: order.timeOrder
                                })
                                isFounded = true
                            }
                        })
                        if (!isFounded) {
                            listDishOrders.push({
                                dishId: dish.dishId,
                                dishName: dish.dishName,
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
                                        timeOrder: order.timeOrder
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


    return (
        <View style={styles.container}>
            <SectionList
                sections={listOrders}
                renderSectionHeader={({ section }) => <DishFatherComponent section={section} />}
                renderItem={({ item, index }) => <TableChildComponent item={item} index={index} />}
                renderSectionFooter={() => <View style={{ height: 10, flex: 1 }}></View>}
                keyExtractor={(item, index) => item.orderDishId.toString()}
            />
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
