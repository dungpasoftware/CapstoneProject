import { call, put } from 'redux-saga/effects';

import { createOrderFailure, loadOrderInfomation } from '../actions/dishOrdering';
import orderRequest from '../api/orderRequest';




function* postLoadDish(userInfo, tableId) {
    try {
        let response = yield call(orderRequest.createNewOrder, userInfo, tableId);
        yield put(loadOrderInfomation(response.orderInfomationAPI));
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(createOrderFailure(err));
    }
}

export default function* listDishSaga(action) {
    console.log('ListDish - Action', action);
    yield call(postLoadDish, action.payload.userInfo, action.payload.tableId);
}