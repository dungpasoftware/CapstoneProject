import { call, put } from 'redux-saga/effects';

import { loadAllOrderFailure, loadAllOrderSuccess } from '../actions/chefAction';
import chefApi from '../api/chefApi';




function* postLoadOrder(accessToken) {
    try {
        let response = yield call(chefApi.loadAllOrder, accessToken);
        yield put(loadAllOrderSuccess(response));
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loadAllOrderFailure({ err }));
    }
}

export default function* chefSaga(action) {
    yield call(postLoadOrder, action.payload.accessToken);
}