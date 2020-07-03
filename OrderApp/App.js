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
import { LOGIN_SCREEN, LIST_TABLE_SCREEN, ORDER_SCREEN } from './src/common/screenName';

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
        <Stack.Screen name={LOGIN_SCREEN}
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
        <Stack.Screen name={LIST_TABLE_SCREEN}
          component={ListTableScreen}
          options={{
            title: 'List Table',
            headerTintColor: 'white',
            headerStyle: {
              backgroundColor: '#24C3A3',
            },

          }}
        />
        <Stack.Screen name={ORDER_SCREEN}
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
