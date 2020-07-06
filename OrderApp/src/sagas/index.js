import { fork, all, takeLatest } from 'redux-saga/effects';
import loginSaga from './loginSaga';
import listTableSaga from './listTableSaga'
import listDishSaga from './listDishSaga'
import { HANDLE_LOGIN, LOAD_TABLE, LOAD_DISH } from '../common/actionType';

const sagas = function* () {
    yield all([takeLatest(HANDLE_LOGIN, loginSaga), takeLatest(LOAD_TABLE, listTableSaga), takeLatest(LOAD_DISH, listDishSaga)]);
};
export default sagas;