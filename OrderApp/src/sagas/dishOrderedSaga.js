import { call, put } from 'redux-saga/effects';

import { loadDishOrderedSuccess, loadDishOrderedFailure } from '../actions/dishOrdered';
import { loadOrderDishReturnSuccess, loadOrderDishReturnFailure } from '../actions/dishReturn';
import orderApi from '../api/orderApi';




function* postLoadDishOrdered(accessToken, orderId) {
    try {
        let response = yield call(orderApi.loadDishOrderdByOrderId, accessToken, orderId);
        yield put(loadDishOrderedSuccess(response));
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loadDishOrderedFailure(err));
    }
}

function* postLoadDishReturn(accessToken, orderId) {
    try {
        let response = yield call(orderApi.listDishReturnByOrderId, accessToken, orderId);
        yield put(loadOrderDishReturnSuccess(response));
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loadOrderDishReturnFailure(err));
    }
}



export function* dishOrderedSaga(action) {
    yield call(postLoadDishOrdered, action.payload.accessToken, action.payload.orderId);
}
export function* dishReturnSaga(action) {
    yield call(postLoadDishReturn, action.payload.accessToken, action.payload.orderId);
}

