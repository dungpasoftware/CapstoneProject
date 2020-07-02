
import { combineReducers } from 'redux'
import dishOrderingReducer from './dishOrdering'
import loginReducer from './loginReducer';

const rootReducer = combineReducers({
    dishOrdering: dishOrderingReducer,
    loginReducer: loginReducer
});

export default rootReducer
