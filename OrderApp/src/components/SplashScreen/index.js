import React, { useEffect } from 'react'
import { Text, StyleSheet, View } from 'react-native'
import AsyncStorage from '@react-native-community/async-storage';

_retrieveData = async () => {
    try {
        const value = await AsyncStorage.getItem('AccessToken');
        if (value !== null) {
            // We have data!!
            return value;
        }
    } catch (error) {
        // Error retrieving data
    }
};


export default function SplashScreen() {
    useEffect(() => {
        async function _handleStart() {

        }
        _handleStart()
        // return () => {    
        // }
    }, [])
    return (
        <View style={styles.container}>
            <Text style={styles.title}> ASM </Text>
        </View>
    )

}

const styles = StyleSheet.create({
    container: {
        backgroundColor: 'white',
        flex: 1,
        alignItems: 'center',
        justifyContent: "center",
    },
    title: {
        fontWeight: 'bold',
        fontSize: 18
    },
})

