import axios from 'axios';

const url = 'http://192.168.1.29:8080'

const instance = axios.create({
    baseURL: url,
});

function listAllCategory(accessToken) {
    instance.defaults.headers['token'] = accessToken;
    return instance.get(`/location-table/all`)
        .then(response => {
            return {
                listCategoryAPI: response.data,
            };
        })
        .catch(err => {
            console.log(err);
        });
}

function listDishByCategory(accessToken, category) {
    instance.defaults.headers['token'] = accessToken;
    return instance.get(`/table/by-location/${category}`)
        .then(response => {
            return {
                listDishAPI: response.data,
            };
        })
        .catch(err => {
            console.log(err);
        });
}

const dishRequest = { listAllCategory, listDishByCategory }
export default dishRequest