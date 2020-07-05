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
import KitchenScreen from './src/components/KitchenScreen';


import { NavigationContainer } from '@react-navigation/native';

const sagaMiddleware = createSagaMiddleware();
const store = createStore(rootReducer, applyMiddleware(sagaMiddleware));
sagaMiddleware.run(rootSaga);

const AppRedux = () => {
    return (
        <NavigationContainer>
            <KitchenScreen />
        </NavigationContainer>

        // <Provider store={store}>
        //     <App />
        // </Provider>
    )
}

AppRegistry.registerComponent(appName, () => AppRedux);
