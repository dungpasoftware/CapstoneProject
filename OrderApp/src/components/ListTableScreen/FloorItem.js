import React from 'react'
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native'
import { MAIN_COLOR } from '../../common/color'

export default function FloorItem({ item, }) {
    return (
        <View style={[styles.table_item_container, { backgroundColor: locationTableId == item.locationTableId ? '#BEFFF2' : 'white' }]}>
            <TouchableOpacity
                style={styles.table_item_container}
                onPress={() => setLocationTableId(item.locationTableId)}
            >
                <Text style={{ color: MAIN_COLOR, fontSize: 18 }}>{item.locationName}</Text>
            </TouchableOpacity>
        </View>
    )
}

const styles = StyleSheet.create({
    table_item_container: {
        flex: 1,
        height: 60,
        width: 100,
        borderBottomWidth: 1,
        borderBottomColor: 'rgba(0,0,0,0.1)',
        justifyContent: "center",
        alignItems: "center"
    },
})
