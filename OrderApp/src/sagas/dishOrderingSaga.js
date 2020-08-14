import { call, put } from 'redux-saga/effects';

import { createOrderFailure, loadOrderInfomation, saveOrderSuccess, saveOrderFailure, saveOrderNotEnough } from '../actions/dishOrdering';
import { loadDishOrderedSuccess } from '../actions/dishOrdered';
import orderApi from '../api/orderApi';


function* postLoadOrderInfo(userInfo, tableId) {
    try {
        let response = yield call(orderApi.createNewOrder, userInfo, tableId);
        yield put(loadOrderInfomation(response));
    } catch (err) {
        console.log('create order err  ---->', err);
        yield put(createOrderFailure(err));
    }
}

function* postSaveOrder(accessToken, rootOrder) {
    try {

        let response = yield call(orderApi.saveOrder, accessToken, rootOrder);
        console.log(response)
        if (response.message == null) {
            yield put(saveOrderSuccess(response));
            yield put(loadDishOrderedSuccess(response));
        } else {
            yield put(saveOrderNotEnough({ response }));
        }


    } catch (err) {
        console.log('save order err----->', err);
        yield put(saveOrderFailure({ err }));
    }
}

export function* loadOrderInfoSaga(action) {
    yield call(postLoadOrderInfo, action.payload.userInfo, action.payload.tableId);
}


export function* saveOrderSaga(action) {
    yield call(postSaveOrder, action.payload.accessToken, action.payload.rootOrder);
}