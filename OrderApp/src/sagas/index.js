import { fork, all, takeLatest } from 'redux-saga/effects';
import loginSaga from './loginSaga';
import listTableSaga from './listTableSaga'
import { HANDLE_LOGIN, LOAD_TABLE } from '../common/actionType';

const sagas = function* () {
    yield all([takeLatest('HANDLE_LOGIN', loginSaga), takeLatest(LOAD_TABLE, listTableSaga)]);
};
export default sagas;