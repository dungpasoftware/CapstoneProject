
import { combineReducers } from 'redux'
import dishOrderingReducer from './dishOrdering'
import loginReducer from './loginReducer';
import listTableReducer from './listTable'
import listDishReducer from './listDish'

const rootReducer = combineReducers({
    dishOrdering: dishOrderingReducer,
    loginReducer: loginReducer,
    listTable: listTableReducer,
    listDish: listDishReducer
});

export default rootReducer
