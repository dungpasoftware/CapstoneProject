import React from 'react'
import { StyleSheet, Text, View, Image, TouchableOpacity } from 'react-native'

export default function DishChildComponent({ item }) {
    return (
        <TouchableOpacity style={styles.container}>
            <View style={styles.container}>
                <Text
                    style={{ marginHorizontal: 8, fontSize: 20, fontWeight: '600' }}
                >
                    {item.amount}
                </Text>
                <Text
                    style={{ flex: 1, fontSize: 18, paddingHorizontal: 10 }}
                >
                    {item.name}
                </Text>
                <TouchableOpacity>
                    <Image style={{ width: 35, height: 35, marginHorizontal: 8 }} source={require('./../../../assets/pan.png')} />
                </TouchableOpacity>

            </View>
        </TouchableOpacity>

    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        height: 45,
        flexDirection: 'row',
        borderColor: 'rgba(0,0,0,0.5)',
        borderWidth: 0.5,
        backgroundColor: 'white',
        alignItems: "center"
    }
})
