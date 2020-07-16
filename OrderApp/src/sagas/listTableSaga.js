import { call, put } from 'redux-saga/effects';


import { loadTableSuccess, loadTableFailure } from '../actions/listTable';
import listTableRequest from './../api/listTableRequest';





function* postLoadTable(accessToken) {
    try {
        // let accessToken = yield call(getAccessToken)
        let response = yield call(listTableRequest.listAllTable, accessToken);
        yield put(loadTableSuccess(response.listTableAPI));
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loadTableFailure(err));
    }
}

export default function* listTableSaga(action) {
    yield call(postLoadTable, action.payload.accessToken);
}