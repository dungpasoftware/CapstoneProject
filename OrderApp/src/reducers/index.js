
import { combineReducers } from 'redux'
import dishOrderingReducer from './dishOrdering'
import dishOrderedReducer from './dishOrdered'
import loginReducer from './loginReducer';
import listTableReducer from './listTable'
import listDishReducer from './listDish'
import dishReturnReducer from './dishReturnReducer'
import chefReducer from './chefReducer';

const rootReducer = combineReducers({
    dishOrdering: dishOrderingReducer,
    loginReducer: loginReducer,
    listTable: listTableReducer,
    listDish: listDishReducer,
    dishOrdered: dishOrderedReducer,
    dishReturn: dishReturnReducer,
    chef: chefReducer,
});

export default rootReducer
