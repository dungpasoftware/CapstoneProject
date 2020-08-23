import { call, put } from 'redux-saga/effects';


import { loadTableSuccess, loadTableFailure } from '../actions/listTable';
import tableApi from './../api/tableApi';


// function addOpenLocation(listLocation) {
//     let newListLocation = [...listLocation]
//     newListLocation.unshift({
//         locationTableId: 0,
//         locationCode: 'SPECIAL',
//         locationName: 'Đang mở',
//         statusValue: 'READY'
//     })
//     return newListLocation
// }


function* postLoadTable(accessToken) {
    try {
        // let accessToken = yield call(getAccessToken)

        let listLocation = yield call(tableApi.listAllLocation, accessToken);
        // listLocation = yield call(addOpenLocation, listLocation)
        let listTable = yield call(tableApi.listAllTable, accessToken);
        yield put(loadTableSuccess({
            listLocation, listTable
        }));
    } catch (err) {
        console.log('err  ------------->', err);
        yield put(loadTableFailure(err));
    }
}

export default function* listTableSaga(action) {
    yield call(postLoadTable, action.payload.accessToken);
}