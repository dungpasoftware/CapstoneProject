import { call, put } from 'redux-saga/effects';

import { createOrderFailure, loadOrderInfomation, saveOrderSuccess, saveOrderFailure } from '../actions/dishOrdering';
import { loadDishOrderedSuccess } from '../actions/dishOrdered';
import orderApi from '../api/orderApi';




function* postLoadOrderInfo(userInfo, tableId) {
    try {
        let response = yield call(orderApi.createNewOrder, userInfo, tableId);
        yield put(loadOrderInfomation(response));
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(createOrderFailure(err));
    }
}

function* postSaveOrder(accessToken, rootOrder) {
    try {
        let response = yield call(orderApi.saveOrder, accessToken, rootOrder);
        yield put(saveOrderSuccess(response));
        yield put(loadDishOrderedSuccess(response));
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(saveOrderFailure(err));
    }
}

export function* loadOrderInfoSaga(action) {
    yield call(postLoadOrderInfo, action.payload.userInfo, action.payload.tableId);
}


export function* saveOrderSaga(action) {
    yield call(postSaveOrder, action.payload.accessToken, action.payload.rootOrder);
}