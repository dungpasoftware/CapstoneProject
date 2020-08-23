import React from 'react'
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native'
import { MAIN_COLOR } from '../../common/color'

export default function FloorItem({ item, locationTableId, handleLoadTable }) {
    return (
        <View style={[styles.table_item_container, { backgroundColor: locationTableId == item.locationTableId ? '#BEFFF2' : 'white' }]}>
            <TouchableOpacity
                style={styles.table_item_container}
                onPress={() => handleLoadTable(item.locationTableId)}
            >
                <Text style={{ color: MAIN_COLOR, fontSize: 18, textAlign: 'center' }}>{item.locationName}</Text>
            </TouchableOpacity>
        </View>
    )
}

const styles = StyleSheet.create({
    table_item_container: {
        flex: 1,
        height: 60,
        width: 110,
        paddingHorizontal: 10,
        borderRightWidth: 1,
        borderRightColor: 'rgba(0,0,0,0.1)',
        justifyContent: "center",
        alignItems: "center"
    },
})
