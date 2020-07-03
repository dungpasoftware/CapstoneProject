import { call, put } from 'redux-saga/effects';
import AsyncStorage from '@react-native-community/async-storage';

import { loadTableSuccess, loadTableFailure } from '../actions/listTable';
import listTableRequest from './../api/listTableRequest';

// function* getAccessToken() {
//     try {
//         yield AsyncStorage.getItem('AccessToken');
//     }
//     catch (error) {
//         console.log('ERROR saveTokenToStore: ', err);
//     }
// }
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
        console.log("acessToken", accessToken)
        let response = yield call(listTableRequest.listTableByLocation, accessToken, locationTableId);
        console.log(response)
        yield put(loadTableSuccess(response));
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loadTableFailure(err));
    }
}

export default function* listTableSaga(action) {
    console.log('ListTable - Action', action);
    yield call(postLoadTable, action.payload.locationTableId);
}