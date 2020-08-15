import React from 'react'
import { StyleSheet, View, SectionList, ActivityIndicator, Alert } from 'react-native'
import { useSelector, useDispatch } from 'react-redux'
import { createSelector } from 'reselect'

import TableFatherComponent from './TableFatherComponent'
import DishChildComponent from './DishChildComponent'
import chefApi from '../../../api/chefApi'
import { MAIN_COLOR } from '../../../common/color'
import { loadAllOrder } from '../../../actions/chefAction'
import { showToast } from '../../../common/functionCommon'

export default function KitchenByTable({ route }) {
    const { userInfo } = route.params
    const { accessToken } = userInfo
    const dispatch = useDispatch()
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
    const { isLoading, error } = useSelector(state => state.chef)

    if (error != null) {
        Alert.alert(
            "Lỗi",
            "Hệ thống không phản hồi.",
            [
                {
                    text: "Thử lại",
                    onPress: () => {
                        dispatch(loadAllOrder({ accessToken }))
                    }
                }
            ],
            { cancelable: false }
        );
    }
    // ! functions for chef
    function changeStatusOrder(orderId, statusId) {
        let newData = {
            orderId,
            statusId,
            chefStaffId: userInfo.staffId
        }
        chefApi.changeStatusOrderByTable(accessToken, newData)
            .then(response => {
                showToast("Chuyển trạng thái thành công")
            })
            .catch((err) => {
                showToast("Có lỗi xảy ra! Chuyển trạng thái thất bại")
            })
    }
    function _handleChangeStatusOrder(orderId, tableName, quantity, statusId) {
        let title = statusId == 12 ? "Đang Thực Hiện" : "Đã Hoàn Thành"
        let message = `Chuyển ${quantity} món của bàn ${tableName} sang trạng thái ${title} ?`
        Alert.alert(
            'Cảnh báo !',
            message,
            [
                {
                    text: 'Không',
                    style: 'cancel'
                },
                { text: 'Tôi chắc chắn', onPress: () => changeStatusOrder(orderId, statusId) }
            ],
            { cancelable: false }
        );
    }
    //! child
    function _handleChangeStatusDish(orderDishId, statusId) {
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
                        _handleChangeStatusDish={_handleChangeStatusDish}
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
