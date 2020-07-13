
import { combineReducers } from 'redux'
import dishOrderingReducer from './dishOrdering'
import dishOrderedReducer from './dishOrdered'
import loginReducer from './loginReducer';
import listTableReducer from './listTable'
import listDishReducer from './listDish'

const rootReducer = combineReducers({
    dishOrdering: dishOrderingReducer,
    loginReducer: loginReducer,
    listTable: listTableReducer,
    listDish: listDishReducer,
    dishOrdered: dishOrderedReducer,
});

export default rootReducer
