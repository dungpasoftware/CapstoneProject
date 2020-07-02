import { fork, all, takeLatest } from 'redux-saga/effects';
import loginSaga from './loginSaga';
import listTableSaga from './listTableSaga'
import { HANDLE_LOGIN, LOAD_TABLE } from '../common/actionType';

const sagas = function* () {
    yield fork(takeLatest(HANDLE_LOGIN, loginSaga));
    yield fork(takeLatest(LOAD_TABLE, listTableSaga));
};
export default sagas;