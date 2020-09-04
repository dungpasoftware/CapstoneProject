import { fork, all, takeLatest } from 'redux-saga/effects';
import { loginSaga, checkTokenSaga } from './loginSaga';
import listTableSaga from './listTableSaga'
import listDishSaga from './listDishSaga'
import { loadOrderInfoSaga, saveOrderSaga } from './dishOrderingSaga'
import { HANDLE_LOGIN, LOAD_TABLE, LOAD_DISH, CREATE_NEW_ORDER, SAVE_ORDER, LOAD_ORDERED, CHECK_TOKEN, LOAD_ORDER_DISH_RETURN, LOAD_ALL_ORDER } from '../common/actionType';
import { dishOrderedSaga, dishReturnSaga } from './dishOrderedSaga';
import chefSaga from './chefSaga';

const sagas = function* () {
    yield all([takeLatest(CHECK_TOKEN, checkTokenSaga), takeLatest(HANDLE_LOGIN, loginSaga), takeLatest(LOAD_TABLE, listTableSaga),
    takeLatest(LOAD_DISH, listDishSaga), takeLatest(CREATE_NEW_ORDER, loadOrderInfoSaga), takeLatest(SAVE_ORDER, saveOrderSaga)
        , takeLatest(LOAD_ORDERED, dishOrderedSaga), takeLatest(LOAD_ORDER_DISH_RETURN, dishReturnSaga), takeLatest(LOAD_ALL_ORDER, chefSaga)]);
};
export default sagas;