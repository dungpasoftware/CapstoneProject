import axiosClient from './axiosClient'

const chefApi = {

    loadAllOrder: (accessToken) => {
        const url = `/order/chef`
        return axiosClient.get(url, {
            headers: {
                token: accessToken
            }
        })
    },
    //! handle order
    preparationOrder: (accessToken, data) => {
        const url = `/order/chef-preparation`
        return axiosClient.put(url,
            {
                ...data
            },
            {
                headers: {
                    token: accessToken
                }
            })
    },
    completedOrder: (accessToken, data) => {
        const url = `/order/chef-completed`
        return axiosClient.put(url,
            {
                ...data
            },
            {
                headers: {
                    token: accessToken
                }
            })
    },
    //! handle dish
    preparationDish: (accessToken, data) => {
        const url = `/order-dish/chef-preparation`
        return axiosClient.put(url,
            {
                ...data
            },
            {
                headers: {
                    token: accessToken
                }
            })
    },
    completedDish: (accessToken, data) => {
        const url = `/order-dish/chef-completed`
        return axiosClient.put(url,
            {
                ...data
            },
            {
                headers: {
                    token: accessToken
                }
            })
    },
}

export default chefApi