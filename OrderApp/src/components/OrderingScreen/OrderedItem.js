import React from 'react'
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native'


export default function OrderedItem() {
    return (
        <View style={styles.container}>
            <TouchableOpacity style={styles.button}>
                <Text style={styles.textButton}>-</Text>
            </TouchableOpacity>
            <View style={styles.infoDish}>
                <Text style={{ fontSize: 16, fontWeight: '700' }}>Mon A</Text>
                <View style={{ flexDirection: 'row' }}>
                    <Text style={{ fontWeight: '600', fontSize: 16 }}>5</Text>
                    <Text style={{ marginHorizontal: 5 }}>x</Text>
                    <Text style={{ fontSize: 15, color: 'red' }}>99 dong</Text>
                </View>
            </View>
            <TouchableOpacity style={styles.button}>
                <Text style={styles.textButton}>+</Text>
            </TouchableOpacity>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'row',
        height: 50,
        borderColor: 'gray',
        borderWidth: 0.5,
        marginBottom: 2
    },

    button: {
        flex: 1,
        backgroundColor: 'gray',
        justifyContent: "center",
        alignItems: 'center'
    },
    textButton: {
        color: 'white',
        fontSize: 45,
        textAlign: 'center'
    },
    infoDish: {
        flex: 6,
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: "center",
        backgroundColor: '#f2f2f2',
        paddingHorizontal: 8
    }
})
