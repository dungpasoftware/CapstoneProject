import React from 'react'
import { StyleSheet, Text, View, Image, TouchableOpacity } from 'react-native'
import Feather from 'react-native-vector-icons/Feather';

export default function DishItem({ item }) {
    return (
        <View style={styles.container}>
            <TouchableOpacity style={styles.touchable}>
                <Image style={{ height: 50, width: 50 }} source={require('./../../assets/dish.png')} />
                <View style={styles.infoDish}>
                    <Text numberOfLines={1} style={{ fontSize: 17, fontWeight: '600', marginBottom: 10 }}>{item.name}</Text>
                    <Text style={{ color: 'red' }}>{`${item.price} đồng`}</Text>
                </View>
                <TouchableOpacity>
                    <Feather name="info" size={45} color="#24C3A3" />
                </TouchableOpacity>
            </TouchableOpacity>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        borderBottomColor: 'gray',
        borderBottomWidth: 0.5,
        height: 70,

    },
    touchable: {
        flex: 1,
        flexDirection: "row",
        alignItems: 'center',
        marginHorizontal: 8
    },
    infoDish: {
        flex: 1,
        flexDirection: 'column',
        marginHorizontal: 8
    }
})
