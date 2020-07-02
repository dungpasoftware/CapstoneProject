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
                listLocationAPI: response.data,
            };
        })
        .catch(err => {
            console.log(err);
        });
}

function listTableByLocation(accessToken, location) {
    instance.defaults.headers['token'] = accessToken;
    return instance.get(`/table/by-location/${location}`)
        .then(response => {
            return {
                listTableAPI: response.data,
            };
        })
        .catch(err => {
            console.log(err);
        });
}

const listTableRequest = { listAllLocation, listTableByLocation }
export default listTableRequest