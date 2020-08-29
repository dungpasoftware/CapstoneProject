import React from 'react'
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native'

export default function CategoryItem({ item, categoryId, setCategoryId }) {
    return (
        <View style={[styles.container, { backgroundColor: categoryId == item.categoryId ? '#BEFFF2' : 'white' }]}>
            <TouchableOpacity
                onPress={() => setCategoryId(item.categoryId)}
                style={styles.touchable}>
                <Text style={styles.text}>{item.categoryName}</Text>
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
