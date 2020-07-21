import axiosClient from './axiosClient'

const authenticationApi = {
    login: (userData) => {
        const url = `/login`
        return axiosClient.post(url, userData)
    },

    checkToken: (token) => {
        const url = `/preLogin?token=${token}`
        return axiosClient.post(url)
    }
}

export default authenticationApi