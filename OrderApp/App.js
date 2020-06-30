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

import Feather from 'react-native-vector-icons/Feather'
import Login from './src/components/Login'
import ListTableScreen from './src/components/ListTableScreen';
import { TouchableOpacity } from 'react-native-gesture-handler';
import OrderScreen from './src/components/OrderScreen';

Feather.loadFont();

const Stack = createStackNavigator();

const App: () => React$Node = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Login"
          component={Login}
          options={{
            title: 'Login',
            headerStyle: {
              backgroundColor: '#24C3A3',
            },
            headerTintColor: '#fff',
            headerTitleStyle: {
              fontWeight: 'bold',
            },
          }}
        />
        <Stack.Screen name="ListTable"
          component={ListTableScreen}
          options={{
            title: 'List Table',
            headerTintColor: 'white',
            headerStyle: {
              backgroundColor: '#24C3A3',
            },
            headerRight: () => (
              <TouchableOpacity style={{ marginRight: 10 }}>
                <Feather name="menu" size={40} color='white' />
              </TouchableOpacity>
            ),
          }}
        />
        <Stack.Screen name="OrderScreen"
          component={OrderScreen}
          options={{
            title: 'Ban 1-1',
            headerStyle: {
              backgroundColor: '#24C3A3',
            },
            headerTintColor: '#fff',
            headerTitleStyle: {
              fontWeight: 'bold',
            },
            headerRight: () => (
              <TouchableOpacity style={{ marginRight: 10 }}>
                <Feather name="more-horizontal" size={40} color='white' />
              </TouchableOpacity>
            ),
          }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
};



export default App;
