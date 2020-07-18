import React from 'react'
import { StyleSheet, Text, View, FlatList, TouchableOpacity } from 'react-native'
import DishReturnComponent from './DishReturnComponent'
import { MAIN_COLOR } from '../../common/color'

export default function ReturnDishScreen() {
    return (
        <View style={styles.container}>
            <FlatList
                style={{ flex: 1 }}
                data={[]}
                keyExtractor={(item, index) => item.id.toString()}
                renderItem={({ item, index }) => {
                    return (
                        <DishReturnComponent item={item} />
                    )
                }}
            />
            <TouchableOpacity
                style={{
                    height: 45,
                    width: 130,
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
