import { call, put } from 'redux-saga/effects';


import { loadTableSuccess, loadTableFailure } from '../actions/listTable';
import tableApi from './../api/tableApi';





function* postLoadTable(accessToken) {
    try {
        // let accessToken = yield call(getAccessToken)
        let response = yield call(tableApi.listAllTable, accessToken);
        yield put(loadTableSuccess(response));
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loadTableFailure(err));
    }
}

export default function* listTableSaga(action) {
    yield call(postLoadTable, action.payload.accessToken);
}