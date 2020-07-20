import axios from 'axios';
import { ROOT_API_CONNECTION } from '../common/apiConnection';


const instance = axios.create({
    baseURL: ROOT_API_CONNECTION,
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

function listAllTable(accessToken) {
    instance.defaults.headers['token'] = accessToken;
    return instance.get(`/table/all`)
        .then(response => {
            return {
                listTableAPI: response.data,
            };
        })
        .catch(err => {
            console.log(err);
        });
}

const listTableRequest = { listAllLocation, listTableByLocation, listAllTable }
export default listTableRequest