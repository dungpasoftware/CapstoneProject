import axiosClient from './axiosClient'

const orderApi = {
    createNewOrder: (userInfo, tableId) => {
        const url = `/order/create-order`
        return axiosClient.post(url,
            {
                tableId: tableId,
                orderTakerStaffId: userInfo.staffId
            },
            {
                headers: {
                    token: userInfo.accessToken
                }
            })
    },
    saveOrder: (accessToken, rootOrder) => {
        const url = `/order/save-order`
        return axiosClient.put(url,
            {
                ...rootOrder
            },
            {
                headers: {
                    token: accessToken
                }
            })
    },
    loadDishOrderdByOrderId: (accessToken, orderId) => {
        const url = `/order/${orderId}`
        return axiosClient.get(url, {
            headers: {
                token: accessToken
            }
        })
    },

    changeAPByOrderDishId: (accessToken, dataChange) => {
        const url = `/order-dish/update-quantity`
        return axiosClient.put(url,
            {
                ...dataChange
            },
            {
                headers: {
                    token: accessToken
                }
            })
    },
    changeCommentByOrderId: (accessToken, dataChange) => {
        const url = `/order/comment`
        return axiosClient.put(url,
            {
                ...dataChange
            },
            {
                headers: {
                    token: accessToken
                }
            })
    },
    changeToppingInOrdered: (accessToken, dataChange) => {
        const url = `/order-dish/update-topping`
        return axiosClient.put(url,
            {
                ...dataChange
            },
            {
                headers: {
                    token: accessToken
                }
            })
    },
    cancelDishOrder: (accessToken, dataChange) => {
        const url = `order-dish/cancel`
        return axiosClient.put(url,
            {
                ...dataChange
            },
            {
                headers: {
                    token: accessToken
                }
            })
    },
    cancelTableOrder: (accessToken, dataChange) => {
        const url = `/order/cancel`
        return axiosClient.put(url,
            {
                ...dataChange
            },
            {
                headers: {
                    token: accessToken
                }
            })
    },
    listDishReturnByOrderId: (accessToken, orderId) => {
        const url = `/order-dish/return/${orderId}`
        return axiosClient.get(url, {
            headers: {
                token: accessToken
            }
        })
    },
    saveReturnDish: (accessToken, dishReturn) => {
        const url = `/order-dish/return`
        return axiosClient.put(url, dishReturn,
            {
                headers: {
                    token: accessToken
                }
            })
    },
    switchTableOrder: (accessToken, dataOrder, newTableId) => {
        console.log('newTableId', dataOrder)
        console.log('newTableId', newTableId)
        const url = `/order/change-table`
        return axiosClient.put(url, dataOrder,
            {
                headers: {
                    token: accessToken,
                },
                params: {
                    tableId: newTableId
                }
            })
    },


}

export default orderApi