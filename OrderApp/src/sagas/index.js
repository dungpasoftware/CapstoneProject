import { fork, all, takeLatest } from 'redux-saga/effects';
import loginSaga from './loginSaga';
import { HANDLE_LOGIN } from '../common/actionType';

const sagas = function* () {
    yield all([takeLatest(HANDLE_LOGIN, loginSaga)]);
};
export default sagas;