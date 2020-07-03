import axios from 'axios';

const url = 'http://192.168.1.29:8080'
axios.defaults.baseURL = url;

function login(phone = '0337384888', password = 'sa123456') {
    return axios.get(`/login?phone=${phone}&password=${password}`)
        .then(response => {
            return {
                access_token: response.data,
            };
        })
        .catch(err => {
            console.log(err);
        });
}

export default login