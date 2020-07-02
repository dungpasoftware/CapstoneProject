import { call, put } from 'redux-saga/effects';
import { loadTableSuccess, loadTableFailure } from '../actions/listTable';
import listTableByLocation from './../api/listTableRequest';


function* postLoadTable(accessToken, location) {
    try {
        let response = yield call(listTableByLocation, accessToken, location);
        //Nếu API gọi thành công. Chúng ta save access_token và Store
        yield put(loadTableSuccess(response)); // Gọi action LOGIN_SUCCESS
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loadTableFailure(err));// Nếu lỗi gọi action LOGIN_FAILURE
    }
}

export default function* listTableSaga(action) {
    console.log('ListTable - Action', action);
    yield call(postLoadTable, action.accessToken., action.payload.location);
}