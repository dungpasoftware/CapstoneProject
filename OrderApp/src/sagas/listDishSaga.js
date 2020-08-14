import { call, put } from 'redux-saga/effects';

import { loadDishSuccess, loadDishFailure } from '../actions/listDish';
import dishApi from '../api/dishApi';




function* postLoadDish(accessToken) {
    try {
        let listDish = yield call(dishApi.listDishByCategory, accessToken, 0);
        let listCategory = yield call(dishApi.listAllCategory, accessToken);
        let newListCategory = [...listCategory]
        newListCategory.unshift({
            categoryId: 0,
            categoryName: 'Tất cả',
        })
        yield put(loadDishSuccess({
            listDish, listCategory: newListCategory
        }));
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loadDishFailure({ err }));
    }
}

export default function* listDishSaga(action) {
    yield call(postLoadDish, action.payload.accessToken);
}