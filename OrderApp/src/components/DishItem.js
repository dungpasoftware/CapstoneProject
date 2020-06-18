import React, { Component } from 'react'
import { Text, StyleSheet, View } from 'react-native'

export default class DishItem extends Component {
    render() {
        return (
            <View style={styles.container}>
                <Text>abcd</Text>
            </View>
        )
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'row'
    },
    infoDish: {
        flex: 1,
        flexDirection: 'column'
    }
})
