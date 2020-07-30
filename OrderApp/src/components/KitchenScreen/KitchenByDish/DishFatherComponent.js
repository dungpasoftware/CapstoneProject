import React from 'react'
import { StyleSheet, Text, View, Image, TouchableOpacity } from 'react-native'

export default function DishFatherComponent({ section, _handleChangeStatusDish }) {
    return (
        <TouchableOpacity
            onPress={() => _handleChangeStatusDish(section.dishId, section.dishName, section.totalQuantity, 20)}
            style={styles.container}>
            <View style={styles.container}>
                <Text
                    style={{ marginHorizontal: 8, fontSize: 18, fontWeight: '600', color: '#900C3F' }}
                >
                    {section.totalQuantity}
                </Text>
                <Text
                    style={{ flex: 1, fontSize: 22, fontWeight: '600', paddingHorizontal: 10, color: '#900C3F' }}
                >
                    {section.dishName}
                </Text>
                {section.statusId == 18 &&
                    <TouchableOpacity
                        onPress={() => _handleChangeStatusDish(section.dishId, section.dishName, section.totalQuantity, 19)}
                    >
                        <Image style={{ width: 35, height: 35, marginHorizontal: 8 }} source={require('./../../../assets/pan.png')} />
                    </TouchableOpacity>
                }

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
