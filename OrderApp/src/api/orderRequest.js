import axios from 'axios';

const url = 'http://192.168.1.29:8080'

const instance = axios.create({
    baseURL: url,
});

function createNewOrder(accessToken) {
    instance.defaults.headers['token'] = accessToken;
    return instance.post(`/order/create-order`, {
        tableId: 5,
        orderTakerStaffId: 2
    })
        .then(response => {
            console.log(response.data)
            return {
                response
            };
        })
        .catch(err => {
            console.log(err);
        });
}
const orderRequest = { createNewOrder }
export default orderRequest