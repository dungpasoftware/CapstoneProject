import React from 'react'
import { StyleSheet, Text, View, Image, TouchableOpacity } from 'react-native'

export default function BillOverview({ buttonName, totalAmount, totalItem, handleSaveOrder }) {
    return (
        <View style={styles.container}>
            <View style={{ flex: 1, flexDirection: "row", alignItems: "center" }}>
                <Image style={{ height: 30, width: 30, marginHorizontal: 8 }} source={require('./../../assets/contract.png')} />
                <Text style={{ fontWeight: '600', fontSize: 16 }}>{totalItem}</Text>
            </View>
            <View style={{ flex: 4, flexDirection: "row", alignItems: "center", marginLeft: 20 }} >
                <Image style={{ height: 30, width: 30, marginHorizontal: 8 }} source={require('./../../assets/dollar.png')} />
                <Text style={{ color: 'red', fontWeight: '600', fontSize: 16 }}>{`${new Intl.NumberFormat().format(totalAmount)} đ`}</Text>
            </View>
            <TouchableOpacity
                onPress={handleSaveOrder}
                style={styles.touchInfo}>
                <Text style={{ fontWeight: 'bold', fontSize: 18 }}>{buttonName}</Text>
            </TouchableOpacity>
        </View>

    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'row',
        borderBottomColor: 'gray',
        borderBottomWidth: 0.5,
        borderTopColor: 'gray',
        borderTopWidth: 0.5,
    },
    touchInfo: {
        flex: 2,
        borderWidth: 0.5,
        width: 80,
        justifyContent: "center",
        alignItems: 'center',
        borderColor: '#24C3A3',
        backgroundColor: '#D3FFF6'
    },
})
