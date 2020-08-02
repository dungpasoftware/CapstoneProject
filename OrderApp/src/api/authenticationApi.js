import axiosClient from './axiosClient'

const authenticationApi = {
    login: (userData) => {
        const url = `/login`
        return axiosClient.post(url, userData)
    },

    checkToken: (accessToken) => {
        const url = `/pre-login`
        return axiosClient.post(url, null, {
            headers: {
                token: accessToken
            },
            timeout: 3000,
            timeoutErrorMessage: 'timeout',
        })
    }
}

export default authenticationApi