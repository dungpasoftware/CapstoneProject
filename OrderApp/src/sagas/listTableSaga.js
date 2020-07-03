import { call, put } from 'redux-saga/effects';
import AsyncStorage from '@react-native-community/async-storage';

import { loadTableSuccess, loadTableFailure } from '../actions/listTable';
import listTableRequest from './../api/listTableRequest';

// async function getAccessToken() {
//     try {
//         const value = await AsyncStorage.getItem('AccessToken');
//         if (value !== null) {
//             return value;
//         }
//     } catch (error) {
//         // Error retrieving data
//     }
// };
function formatData(dataTableDetail, numColumns) {
    const numberOfFullRows = Math.floor(dataTableDetail.length / numColumns);

    let numberOfElementsLastRow = dataTableDetail.length - (numberOfFullRows * numColumns)
    while (numberOfElementsLastRow !== numColumns && numberOfElementsLastRow !== 0) {
        dataTableDetail.push({ tableId: `black-${numberOfElementsLastRow}`, empty: true })
        numberOfElementsLastRow = numberOfElementsLastRow + 1
    }

    return dataTableDetail
}


function* postLoadTable(locationTableId, accessToken) {
    try {
        // let accessToken = yield call(getAccessToken)
        let response = yield call(listTableRequest.listTableByLocation, accessToken, locationTableId);
        yield put(loadTableSuccess(formatData(response.listTableAPI, 2)));
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loadTableFailure(err));
    }
}

export default function* listTableSaga(action) {
    console.log('ListTable - Action', action);
    yield call(postLoadTable, action.payload.locationTableId, action.payload.accessToken);
}