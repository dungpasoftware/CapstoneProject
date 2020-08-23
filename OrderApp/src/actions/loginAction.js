import { HANDLE_LOGIN, LOGIN_SUCCESS, LOGIN_FAILURE, HANDLE_LOGOUT, CHECK_TOKEN, CHECK_TOKEN_FAILURE } from "../common/actionType";

export const actionLogin = payload => ({
    type: HANDLE_LOGIN,
    payload,
});
export const actionLogout = payload => ({
    type: HANDLE_LOGOUT,
    payload,
});

export const loginSuccess = payload => ({
    type: LOGIN_SUCCESS,
    payload,
});

export const loginFailure = payload => ({
    type: LOGIN_FAILURE,
    payload,
});

export const checkToken = payload => ({
    type: CHECK_TOKEN,
    payload,
});
export const checkTokenFailure = payload => ({
    type: CHECK_TOKEN_FAILURE,
    payload,
});

export default {
    actionLogin,
    loginSuccess,
    loginFailure,
    checkToken
};