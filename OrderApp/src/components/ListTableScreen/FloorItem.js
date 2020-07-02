import React from 'react'
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native'

export default function FloorItem({ item }) {
    return (
        <View style={styles.table_item_container}>
            <TouchableOpacity style={styles.table_item_container}>
                <Text style={{ color: '#24C3A3', fontSize: 18 }}>{item.locationName}</Text>
            </TouchableOpacity>
        </View>
    )
}

const styles = StyleSheet.create({
    table_item_container: {
        flex: 1,
        height: 60,
        width: 100,
        borderBottomWidth: 1,
        borderBottomColor: 'rgba(0,0,0,0.1)',
        justifyContent: "center",
        alignItems: "center"
    },
})
