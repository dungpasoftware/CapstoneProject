import { call, put } from 'redux-saga/effects';

import { createOrderFailure, loadOrderInfomation, saveOrderSuccess, saveOrderFailure } from '../actions/dishOrdering';
import { loadDishOrderedSuccess } from '../actions/dishOrdered';
import orderRequest from '../api/orderRequest';




function* postLoadOrderInfo(userInfo, tableId) {
    try {
        let response = yield call(orderRequest.createNewOrder, userInfo, tableId);
        yield put(loadOrderInfomation(response.orderInfomationAPI));
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(createOrderFailure(err));
    }
}

function* postSaveOrder(accessToken, rootOrder) {
    try {
        let response = yield call(orderRequest.saveOrder, accessToken, rootOrder);
        yield put(saveOrderSuccess(response));
        yield put(loadDishOrderedSuccess(response.orderedAPI));
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