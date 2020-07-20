import axios from 'axios';

const url = 'http://192.168.1.29:8080'
axios.defaults.baseURL = url;

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