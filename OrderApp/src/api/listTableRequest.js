import axios from 'axios';

const url = 'http://192.168.1.29:8080'

const instance = axios.create({
    baseURL: url,
});

function listAllLocation(accessToken) {
    instance.defaults.headers['token'] = accessToken;
    return instance.get(`/location-table/all`)
        .then(response => {
            return {
                listLocation: response.data,
            };
        })
        .catch(err => {
            console.log(err);
        });
}

export default listAllLocation