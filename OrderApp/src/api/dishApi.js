import axiosClient from './axiosClient'

const dishApi = {
    listAllCategory: (accessToken) => {
        const url = `/categories`
        return axiosClient.get(url, {
            headers: {
                token: accessToken
            }
        })
    },
    listDishByCategory: (accessToken, categoryId) => {
        const url = `/categories/${categoryId}/dishes`
        return axiosClient.get(url, {
            headers: {
                token: accessToken
            }
        })
    },
    listOptionsByDishId: (accessToken, dishId) => {
        const url = `/dishes/${dishId}/options`
        return axiosClient.get(url, {
            headers: {
                token: accessToken
            }
        })
    },



}

export default dishApi