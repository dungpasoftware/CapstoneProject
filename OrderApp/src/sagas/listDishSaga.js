import { call, put } from 'redux-saga/effects';

import { loadDishSuccess, loadDishFailure } from '../actions/listDish';
import dishApi from '../api/dishApi';




function* postLoadDish(categoryId, accessToken) {
    try {
        let response = yield call(dishApi.listDishByCategory, accessToken, categoryId);
        yield put(loadDishSuccess(response));
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loadDishFailure(err));
    }
}

export default function* listDishSaga(action) {
    yield call(postLoadDish, action.payload.categoryId, action.payload.accessToken);
}