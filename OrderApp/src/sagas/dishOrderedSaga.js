import { call, put } from 'redux-saga/effects';

import { loadDishOrderedSuccess, loadDishOrderedFailure } from '../actions/dishOrdered';
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


export default function* dishOrderedSaga(action) {
    yield call(postLoadDishOrdered, action.payload.accessToken, action.payload.orderId);
}

