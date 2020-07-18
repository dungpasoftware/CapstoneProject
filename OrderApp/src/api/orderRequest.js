import axios from 'axios';

const url = 'http://192.168.1.29:8080'

const instance = axios.create({
    baseURL: url,
});

function createNewOrder(userInfo, tableId) {
    instance.defaults.headers['token'] = userInfo.accessToken;
    return instance.post(`/order/create-order`, {
        tableId: tableId,
        orderTakerStaffId: userInfo.staffId
    })
        .then(response => {
            return {
                orderInfomationAPI: response.data
            };
        })
        .catch(err => {
            console.log(err);
        });
}
function saveOrder(accessToken, rootOrder) {
    instance.defaults.headers['token'] = accessToken;
    return instance.put(`/order/save-order`, {
        ...rootOrder
    })
        .then(response => {
            console.log(response)
            return {
                abc: response
            };
        })
        .catch(err => {
            console.log(err);
        });
}

function loadDishOrderdByOrderId(accessToken, orderId) {
    instance.defaults.headers['token'] = accessToken;
    return instance.get(`/order/${orderId}`)
        .then(response => {
            return {
                dishOrderedAPI: response.data
            };
        })
        .catch(err => {
            console.log(err);
        });
}


function changeAPByOrderDishId(accessToken, dataChange) {
    instance.defaults.headers['token'] = accessToken;
    return instance.put(`/order-dish/update-quantity`, {
        ...dataChange
    })
        .then(response => {
            return {
                responseAPI: response

            };
        })
        .catch(err => {
            console.log(err);
        });
}
function changeCommentByOrderId(accessToken, dataChange) {
    instance.defaults.headers['token'] = accessToken;
    return instance.put(`/order/comment`, {
        ...dataChange
    })
        .then(response => {
            console.log("Thay đổi comment thành công", response)
            return {
                responseAPI: response

            };
        })
        .catch(err => {
            console.log(err);
        });
}


function changeToppingInOrdered(accessToken, dataChange) {
    instance.defaults.headers['token'] = accessToken;
    return instance.put(`/order-dish/update-topping`, {
        ...dataChange
    })
        .then(response => {
            return {
                responseAPI: response

            };
        })
        .catch(err => {
            console.log(err);
        });
}
const orderRequest = { createNewOrder, saveOrder, loadDishOrderdByOrderId, changeAPByOrderDishId, changeToppingInOrdered, changeCommentByOrderId }
export default orderRequest