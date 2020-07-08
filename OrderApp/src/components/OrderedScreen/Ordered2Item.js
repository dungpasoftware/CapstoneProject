import React from 'react'
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native'

export default function Ordered2Item({ item, showOptionDish }) {
    return (
        <View style={styles.container}>
            <TouchableOpacity style={styles.container} onPress={() => showOptionDish()}>
                <View
                    style={{
                        flex: 1,
                        justifyContent: 'center',
                        borderRightColor: 'gray',
                        borderRightWidth: 0.5,
                        alignItems: "center"
                    }}>
                    <Text style={styles.text}>{item.quantity}</Text>
                </View>
                <View
                    style={{
                        flex: 5,
                        justifyContent: 'center',
                        marginHorizontal: 8
                    }}>
                    <Text numberOfLines={1} style={styles.text}>{item.dish.dishName}</Text>
                </View>
                <View style={{ flex: 3, justifyContent: 'center' }}>
                    <Text style={{ color: 'red', textAlign: 'center' }} numberOfLines={1}>{`${new Intl.NumberFormat().format(item.dish.defaultPrice)} đồng`}</Text>
                </View>
            </TouchableOpacity>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'row',
        height: 50,
        backgroundColor: '#f2f2f2',
        borderColor: 'gray',
        borderWidth: 0.5,
        marginBottom: 5
    },
    text: {
        fontWeight: '600',
    }

})
