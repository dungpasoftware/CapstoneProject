import { call, put } from 'redux-saga/effects';
import { loginSuccess, loginFailure } from '../actions/loginAction';
import login from './../api/loginRequest';
import AsyncStorage from '@react-native-community/async-storage';


function* saveTokenToStore(data) {
    yield AsyncStorage.multiSet(
        [['AccessToken', data.token], ['role', data.roleName]],
        err => {
            console.log('ERROR saveTokenToStore: ', err);
        },
    );
}

function* postLoginAction(phone, password) {
    try {
        console.log(
            `Login Saga - postLoginArction: username: ${phone} - password: ${password}`,
        );
        let response = yield call(login, phone, password);
        //Nếu API gọi thành công. Chúng ta save access_token và Store
        // _storeData(response)
        console.log(response)
        yield call(saveTokenToStore, response.access_token);
        yield put(loginSuccess(response.access_token)); // Gọi action LOGIN_SUCCESS
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loginFailure(err));// Nếu lỗi gọi action LOGIN_FAILURE
    }
}

export default function* loginSaga(action) {
    yield call(postLoginAction, action.payload.phone, action.payload.password);
}