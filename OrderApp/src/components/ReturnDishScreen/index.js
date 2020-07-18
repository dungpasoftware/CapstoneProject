import React, { useState, useEffect } from 'react'
import { StyleSheet, Text, View, FlatList, TouchableOpacity, Dimensions } from 'react-native'
import DishReturnComponent from './DishReturnComponent'
import { MAIN_COLOR } from '../../common/color'

export default function ReturnDishScreen({ route, navigation }) {
    const maxWidth = Dimensions.get('window').width
    const { userInfo, rootOrder } = route.params;
    const { accessToken } = userInfo


    useEffect(() => {


    }, [rootOrder])

    function _handleChangeAmount(valueChange) {

    }
    console.log('im here', rootOrder)
    return (
        <View style={styles.container}>
            <FlatList
                style={{ flex: 1 }}
                data={rootOrder.orderDish}
                keyExtractor={(item) => item.orderDishId.toString()}
                renderItem={({ item, index }) => {
                    return (
                        <DishReturnComponent item={item} />
                    )
                }}
            />
            <TouchableOpacity
                style={{
                    height: 45,
                    width: maxWidth / 2,
                    alignSelf: 'center',
                    backgroundColor: MAIN_COLOR,
                    marginVertical: 30,
                    justifyContent: 'center'
                }}
            >
                <Text style={{ color: 'white', textAlign: 'center', fontSize: 18, fontWeight: '700' }}>Trả Món</Text>
            </TouchableOpacity>

        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
    }
})
