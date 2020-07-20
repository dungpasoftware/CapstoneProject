import axios from 'axios';
import { ROOT_API_CONNECTION } from '../common/apiConnection';

axios.defaults.baseURL = ROOT_API_CONNECTION;

function login(phone, password) {
    return axios.post(`/login?phone=${phone}&password=${password}`)
        .then(response => {
            return {
                userInfo: response.data,
            };
        })
        .catch(err => {
            console.log("Lỗi đăng nhập ở API", err);
        });
}

function checkToken(token) {
    return axios.post(`/preLogin?token=${token}`)
        .then(response => {
            return {
                userInfo: response.data,
            };
        })
        .catch(err => {
            console.log("Lỗi check token ở API", err);
        });
}

const loginRequest = { login, checkToken }
export default loginRequest