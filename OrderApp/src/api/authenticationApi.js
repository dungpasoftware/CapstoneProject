import axiosClient from './axiosClient'

const authenticationApi = {
    login: (userData) => {
        const url = `/login?phone=${userData.phone}&password=${userData.password}`
        return axiosClient.post(url)
    },

    checkToken: (token) => {
        const url = `/preLogin?token=${token}`
        return axiosClient.post(url)
    }
}

export default authenticationApi