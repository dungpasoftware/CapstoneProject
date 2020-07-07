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
            console.log(response.data)
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
const orderRequest = { createNewOrder, saveOrder }
export default orderRequest