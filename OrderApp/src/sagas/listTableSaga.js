import { call, put } from 'redux-saga/effects';
import AsyncStorage from '@react-native-community/async-storage';

import { loadTableSuccess, loadTableFailure } from '../actions/listTable';
import listTableRequest from './../api/listTableRequest';

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


function* postLoadTable(locationTableId) {
    try {
        // let accessToken = yield call(getAccessToken)
        let accessToken = yield getAccessToken()
        let response = yield call(listTableRequest.listTableByLocation, accessToken, locationTableId);
        yield put(loadTableSuccess(response.listTableAPI));
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loadTableFailure(err));
    }
}

export default function* listTableSaga(action) {
    console.log('ListTable - Action', action);
    yield call(postLoadTable, action.payload.locationTableId);
}