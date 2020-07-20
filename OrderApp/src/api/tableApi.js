import axiosClient from './axiosClient'

const tableApi = {
    listAllLocation: (accessToken) => {
        const url = `/location-table/all`
        return axiosClient.get(url, {
            headers: {
                token: accessToken
            }
        })
    },
    listAllTable: (accessToken) => {
        const url = `/table/all`
        return axiosClient.get(url, {
            headers: {
                token: accessToken
            }
        })
    },

}

export default tableApi
