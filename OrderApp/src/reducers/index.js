
import { combineReducers } from 'redux'
import dishOrderingReducer from './dishOrdering'
import loginReducer from './loginReducer';
import listTable from './listTable'

const rootReducer = combineReducers({
    dishOrdering: dishOrderingReducer,
    loginReducer: loginReducer,
    listTable: listTable,
});

export default rootReducer
