import { call, put } from 'redux-saga/effects';
import { loginSuccess, loginFailure } from '../actions/loginAction';
import loginRequest from './../api/loginRequest';
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

function* postLoginAction(phone, password) {
    try {

        let response = yield call(loginRequest.login, phone, password);
        //Nếu API gọi thành công. Chúng ta save access_token và Store
        // _storeData(response)
        yield call(saveTokenToStore, response.userInfo);
        yield put(loginSuccess(response.userInfo)); // Gọi action LOGIN_SUCCESS
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loginFailure(err));// Nếu lỗi gọi action LOGIN_FAILURE
    }
}
function* postCheckToken(token) {
    try {

        let response = yield call(loginRequest.checkToken, token);
        yield put(loginSuccess(response.userInfo)); // Gọi action LOGIN_SUCCESS
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loginFailure(err));// Nếu lỗi gọi action LOGIN_FAILURE
    }
}

export function* loginSaga(action) {
    yield call(postLoginAction, action.payload.phone, action.payload.password);
}

export function* checkTokenSaga(action) {
    yield call(postCheckToken, action.payload.token);
}