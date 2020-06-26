/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import 'react-native-gesture-handler';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

import Icon from 'react-native-vector-icons/Feather'
import Login from './src/components/Login'
import ListTableScreen from './src/components/ListTableScreen';
Icon.loadFont();

const Stack = createStackNavigator();

const App: () => React$Node = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Login"
          component={Login}
          option={{ title: 'Login' }} />
        <Stack.Screen name="ListTable"
          component={ListTableScreen}
          option={{ title: 'List Table' }} />
      </Stack.Navigator>
    </NavigationContainer>
  );
};



export default App;
