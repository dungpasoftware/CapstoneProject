import axios from 'axios'
import queryString from 'query-string'
import { ROOT_API_CONNECTION } from '../common/apiConnection'
import AsyncStorage from '@react-native-community/async-storage';

// Set up default config for http requests here
// Please have a look at here `https://github.com/axios/axios#request- config` for the full list of configs
const axiosClient = axios.create({
    baseURL: ROOT_API_CONNECTION,
    headers: {
        'Content-Type': 'application/json',
    },
    paramsSerializer: params => queryString.stringify(params),
    timeout: 10000,
    timeoutErrorMessage: 'timeout',
});



axiosClient.interceptors.request.use(async (config) => {
    // Handle token here ...
    return config;
});

axiosClient.interceptors.response.use((response) => {
    if (response && response.data) {
        return response.data;
    }
    return response;
}, (error) => {
    if (error.code === 'ECONNABORTED')
        throw 'timeout';
    else
        throw error.response;
});

export default axiosClient;