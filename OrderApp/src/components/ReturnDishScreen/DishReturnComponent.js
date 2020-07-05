import React from 'react'
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native'
import Feather from 'react-native-vector-icons/Feather';

export default function DishReturnComponent() {
    return (
        <View style={styles.container}>
            <TouchableOpacity style={styles.container}>
                <Text style={{ marginHorizontal: 8, fontSize: 20, fontWeight: '500' }}>2</Text>
                <Text style={{ flex: 1, fontSize: 18, marginLeft: 10 }}>Ca phe da</Text>
                <View style={{ flexDirection: "row", marginHorizontal: 8, alignItems: 'center' }}>
                    <TouchableOpacity>
                        <Feather name="minus-circle" color='red' size={40} />
                    </TouchableOpacity>
                    <Text style={{ textAlign: "center", marginHorizontal: 8, fontSize: 20, fontWeight: '500' }}>2</Text>
                    <TouchableOpacity>
                        <Feather name="plus-circle" color='green' size={40} />
                    </TouchableOpacity>
                </View>
            </TouchableOpacity >
        </View >
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'row',
        alignItems: 'center',
        height: 45,
        borderBottomColor: 'gray',
        borderBottomWidth: 1

    }
})
