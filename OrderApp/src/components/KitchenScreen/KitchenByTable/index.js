import React from 'react'
import { StyleSheet, View, SectionList, ActivityIndicator, Alert } from 'react-native'
import { useSelector } from 'react-redux'
import { createSelector } from 'reselect'

import TableFatherComponent from './TableFatherComponent'
import DishChildComponent from './DishChildComponent'
import chefApi from '../../../api/chefApi'
import { MAIN_COLOR } from '../../../common/color'

export default function KitchenByTable({ route }) {
    const { userInfo } = route.params
    const { accessToken } = userInfo
    //! format data for listOrders by table
    const listOrdersSelector = state => state.chef.listOrders
    const listOrdersByTableSelector = createSelector(
        listOrdersSelector,
        (listOrders) => listOrders.map((order) => {
            return {
                orderId: order.orderId,
                tableId: order.tableId,
                tableName: order.tableName,
                statusId: order.statusId,
                statusValue: order.statusValue,
                totalQuantity: order.totalQuantity,
                timeOrder: order.timeOrder,
                comment: order.comment,
                data: order.orderDish.filter(dish => {
                    return dish.statusId != 20
                })
            }
        })
    )
    const listOrders = useSelector(listOrdersByTableSelector)
    const isLoading = useSelector(state => state.chef.isLoading)


    // ! functions for chef
    function _handleChangeStatusOrder(orderId, tableName, quantity, statusId) {
        let title = statusId == 19 ? "Đang Thực Hiện" : "Đã Hoàn Thành"
        let message = `Chuyển ${quantity} món của bàn ${tableName} sang trạng thái ${title} ?`
        Alert.alert(
            'Cảnh báo !',
            message,
            [
                {
                    text: 'Không',
                    style: 'cancel'
                },
                { text: 'Tôi chắc chắn', onPress: () => console.log('abc') }
            ],
            { cancelable: false }
        );
    }
    function preparationAOrder(orderId) {
        let newData = {
            orderId,
            chefStaffId: userInfo.staffId
        }
        chefApi.preparationOrder(accessToken, newData)
            .then(response => {
                console.log('Chuyển trạng thái thành công', response)
            })
            .catch((err) => {
                console.log('Chuyển trạng thái thất bại', err)
            })
    }
    function completedAOrder(orderId) {
        let newData = {
            orderId,
            chefStaffId: userInfo.staffId
        }
        chefApi.completedOrder(accessToken, newData)
            .then(response => {
                console.log('Chuyển trạng thái thành công', response)
            })
            .catch((err) => {
                console.log('Chuyển trạng thái thất bại', err)
            })
    }
    function preparationADish(orderDishId) {
        let newData = {
            orderDishId
            // chefStaffId: userInfo.staffId
        }
        chefApi.preparationDish(accessToken, newData)
            .then(response => {
                console.log('Chuyển trạng thái thành công', response)
            })
            .catch((err) => {
                console.log('Chuyển trạng thái thất bại', err)
            })
    }
    function completedADish(orderDishId) {
        let newData = {
            orderDishId,
            chefStaffId: userInfo.staffId
        }
        chefApi.completedDish(accessToken, newData)
            .then(response => {
                console.log('Chuyển trạng thái thành công', response)
            })
            .catch((err) => {
                console.log('Chuyển trạng thái thất bại', err)
            })
    }


    return (
        <View style={styles.container}>
            {isLoading ? <ActivityIndicator style={{ alignSelf: 'center', flex: 1 }} size="large" color={MAIN_COLOR} />
                : <SectionList
                    sections={listOrders}
                    renderSectionHeader={({ section }) => <TableFatherComponent
                        section={section}
                        _handleChangeStatusOrder={_handleChangeStatusOrder}
                    />}
                    renderItem={({ item }) => <DishChildComponent
                        item={item}
                        preparationADish={preparationADish}
                        completedADish={completedADish}
                    />}
                    keyExtractor={(item) => item.orderDishId.toString()}
                    renderSectionFooter={() => <View style={{ height: 10, flex: 1 }}></View>}
                />
            }
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
