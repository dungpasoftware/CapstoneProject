/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, { useEffect } from 'react';
import 'react-native-gesture-handler';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { YellowBox } from 'react-native';

import Feather from 'react-native-vector-icons/Feather'
import Login from './src/components/Login'
import ListTableScreen from './src/components/ListTableScreen';
import OrderScreen from './src/components/OrderScreen';

Feather.loadFont();

const Stack = createStackNavigator();

const App: () => React$Node = () => {
  useEffect(() => {
    YellowBox.ignoreWarnings(['Animated: `useNativeDriver`']);
    YellowBox.ignoreWarnings(['Animated.event']);
  }, [])

  return (
    <NavigationContainer>
      <Stack.Navigator screenOptions={{ gestureEnabled: false }} >
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

          }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
};



export default App;
