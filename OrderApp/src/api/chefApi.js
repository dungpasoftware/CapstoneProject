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

    changeStatusOrderByTable: (accessToken, data) => {
        const url = `/order/chef-order`
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
    //!handle item follow dish
    changeStatusTableByDish: (accessToken, data) => {
        const url = `/order-dish/chef-by-order`
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
    changeStatusDishByDish: (accessToken, data) => {
        const url = `/order-dish/chef-by-dish`
        return axiosClient.put(url,
            {
                ...data
            },
            {
                headers: {
                    token: accessToken
                }
            })
    }
}

export default chefApi