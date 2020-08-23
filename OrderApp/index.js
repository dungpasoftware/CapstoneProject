/**
 * @format
 */

import { AppRegistry } from 'react-native';
import { Provider } from 'react-redux'
import { createStore, applyMiddleware } from 'redux'
import createSagaMiddleware from 'redux-saga';
import React from 'react'

import rootReducer from './src/reducers'
import rootSaga from './src/sagas';

import App from './App';
import { name as appName } from './app.json';

const sagaMiddleware = createSagaMiddleware();
const store = createStore(rootReducer, applyMiddleware(sagaMiddleware));
sagaMiddleware.run(rootSaga);

const AppRedux = () => {
    return (

        <Provider store={store}>
            <App />
        </Provider>
    )
}

AppRegistry.registerComponent(appName, () => AppRedux);

