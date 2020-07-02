
import { combineReducers } from 'redux'
import dishOrderingReducer from './dishOrdering'
import loginReducer from './loginReducer';
import listTableReducer from './listTable'

const rootReducer = combineReducers({
    dishOrdering: dishOrderingReducer,
    loginReducer: loginReducer,
    listTable: listTableReducer,
});

export default rootReducer
