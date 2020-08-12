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
import LoginScreen from './src/components/LoginScreen'
import ListTableScreen from './src/components/ListTableScreen';
import OrderScreen from './src/components/OrderScreen';
import ReturnDishScreen from './src/components/ReturnDishScreen';
import KitchenScreen from './src/components/KitchenScreen';
import {
  LOGIN_SCREEN, LIST_TABLE_SCREEN, ORDER_SCREEN,
  RETURN_DISH_SCREEN, KITCHEN_SCREEN, SPLASH_SCREEN, SWITCH_TABLE_SCREEN
} from './src/common/screenName';
import SplashScreen from './src/components/SplashScreen';
import SwitchTableScreen from './src/components/SwitchTableScreen';

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
        <Stack.Screen name={SPLASH_SCREEN}
          component={SplashScreen}
          options={{
            headerShown: false,
          }}
        />
        <Stack.Screen name={LOGIN_SCREEN}
          component={LoginScreen}
          options={{
            headerShown: false,
          }}
        />
        <Stack.Screen name={LIST_TABLE_SCREEN}
          component={ListTableScreen}
          options={{
            title: 'Danh sách bàn',
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
        <Stack.Screen name={RETURN_DISH_SCREEN}
          component={ReturnDishScreen}
          options={{
            title: 'Trả món',
            headerStyle: {
              backgroundColor: '#24C3A3',
            },
            headerTintColor: '#fff',
            headerTitleStyle: {
              fontWeight: 'bold',
            },

          }}
        />
        <Stack.Screen name={SWITCH_TABLE_SCREEN}
          component={SwitchTableScreen}
          options={{
            title: 'Chuyển bàn',
            headerStyle: {
              backgroundColor: '#24C3A3',
            },
            headerTintColor: '#fff',
            headerTitleStyle: {
              fontWeight: 'bold',
            },

          }}
        />
        <Stack.Screen name={KITCHEN_SCREEN}
          component={KitchenScreen}
          options={{
            title: 'Nhà bếp',
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
