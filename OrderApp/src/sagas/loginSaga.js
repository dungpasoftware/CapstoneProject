import { call, put } from 'redux-saga/effects';
import { loginSuccess, loginFailure, checkTokenFailure } from '../actions/loginAction';
import authenticationApi from './../api/authenticationApi';
import AsyncStorage from '@react-native-community/async-storage';


function* saveTokenToStore(data) {
    try {
        yield AsyncStorage.setItem(
            'AccessToken',
            data.token
        );
    } catch (error) {
        console.log("Lưu token thất bại", error)
    }

}

function* postLoginAction(userData) {
    try {

        let response = yield call(authenticationApi.login, userData);
        //Nếu API gọi thành công. Chúng ta save access_token và Store
        // _storeData(response)
        yield call(saveTokenToStore, response);
        yield put(loginSuccess(response)); // Gọi action LOGIN_SUCCESS
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loginFailure(err));// Nếu lỗi gọi action LOGIN_FAILURE
    }
}
function* postCheckToken(token) {
    try {
        let response = yield call(authenticationApi.checkToken, token);
        yield put(loginSuccess(response)); // Gọi action LOGIN_SUCCESS
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(checkTokenFailure(err));// Nếu lỗi gọi action LOGIN_FAILURE
    }
}

export function* loginSaga(action) {
    yield call(postLoginAction, action.payload);
}

export function* checkTokenSaga(action) {
    yield call(postCheckToken, action.payload.token);
}