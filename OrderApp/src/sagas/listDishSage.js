import { call, put } from 'redux-saga/effects';
import AsyncStorage from '@react-native-community/async-storage';

import { loadDishSuccess, loadDishFailure } from '../actions/listDish';
import dishRequest from './../api/dishRequest';

async function getAccessToken() {
    try {
        const value = await AsyncStorage.getItem('AccessToken');
        if (value !== null) {
            return value;
        }
    } catch (error) {
        // Error retrieving data
    }
};



function* postLoadDish(categoryId) {
    try {
        // let accessToken = yield call(getAccessToken)
        let accessToken = yield getAccessToken()
        let response = yield call(dishRequest.listDishByCategory, accessToken, categoryId);
        yield put(loadDishSuccess(response.listDishAPI));
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loadDishFailure(err));
    }
}

export default function* listTableSaga(action) {
    console.log('ListDish - Action', action);
    yield call(postLoadDish, action.payload.category);
}