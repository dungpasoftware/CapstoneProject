import React from 'react'
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native'

export default function CategoryItem({ item }) {
    return (
        <View style={styles.container}>
            <TouchableOpacity style={styles.touchable}>
                <Text style={styles.text}>{item.name}</Text>
            </TouchableOpacity>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        borderBottomColor: 'gray',
        borderBottomWidth: 0.5,
        height: 50,
    },
    touchable: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center'
    },
    text: {
        fontSize: 16,
        textAlign: 'center'
    }
})
