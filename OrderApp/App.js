/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
} from 'react-native';
import Icon from 'react-native-vector-icons/Feather'
import Login from './src/components/Login'
import ListTableScreen from './src/components/ListTableScreen';

Icon.loadFont();



const App: () => React$Node = () => {
  return (
    <ListTableScreen />
  );
};

const styles = StyleSheet.create({

});

export default App;
