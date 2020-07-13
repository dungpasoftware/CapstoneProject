import { fork, all, takeLatest } from 'redux-saga/effects';
import loginSaga from './loginSaga';
import listTableSaga from './listTableSaga'
import listDishSaga from './listDishSaga'
import { loadOrderInfoSaga, saveOrderSaga } from './dishOrderingSaga'
import { HANDLE_LOGIN, LOAD_TABLE, LOAD_DISH, CREATE_NEW_ORDER, SAVE_ORDER, LOAD_ORDERED } from '../common/actionType';
import dishOrderedSaga from './dishOrderedSaga';

const sagas = function* () {
    yield all([takeLatest(HANDLE_LOGIN, loginSaga), takeLatest(LOAD_TABLE, listTableSaga),
    takeLatest(LOAD_DISH, listDishSaga), takeLatest(CREATE_NEW_ORDER, loadOrderInfoSaga), takeLatest(SAVE_ORDER, saveOrderSaga)
        , takeLatest(LOAD_ORDERED, dishOrderedSaga)]);
};
export default sagas;