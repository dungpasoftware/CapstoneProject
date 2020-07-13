import { call, put } from 'redux-saga/effects';

import { loadDishOrderedSuccess, loadDishOrderedFailure } from '../actions/dishOrdered';
import orderRequest from '../api/orderRequest';




function* postLoadDishOrdered(accessToken, orderId) {
    try {
        let response = yield call(orderRequest.loadDishOrderdByOrderId, accessToken, orderId);
        yield put(loadDishOrderedSuccess(response.dishOrderedAPI));
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loadDishOrderedFailure(err));
    }
}


export default function* dishOrderedSaga(action) {
    console.log('LoadOrderInfo - Action', action);
    yield call(postLoadDishOrdered, action.payload.accessToken, action.payload.orderId);
}

