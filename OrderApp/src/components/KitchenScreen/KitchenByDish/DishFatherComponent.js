import React from 'react'
import { StyleSheet, Text, View, Image, TouchableOpacity } from 'react-native'

export default function DishFatherComponent({ section }) {
    return (
        <TouchableOpacity style={styles.container}>
            <View style={styles.container}>
                <Text
                    style={{ marginHorizontal: 8, fontSize: 18, fontWeight: '600' }}
                >
                    2
            </Text>
                <Text
                    style={{ flex: 1, fontSize: 22, fontWeight: '600', paddingHorizontal: 10 }}
                >
                    Thit hun khoi
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
