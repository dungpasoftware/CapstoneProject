import axios from 'axios';

const url = 'http://192.168.1.29:8080'
axios.defaults.baseURL = url;

function login(phone = '0337384888', password = 'sa123456') {
    return axios.get(`/login?phone=${phone}&password=${password}`)
        .then(response => {
            console.log(respone)
            return {
                access_token: response.headers.token,
            };
        })
        .catch(err => {
            console.log(err);
        });
}

export default login