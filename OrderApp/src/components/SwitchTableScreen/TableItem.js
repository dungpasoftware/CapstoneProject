import React from 'react'
import { Text, StyleSheet, View, TouchableOpacity } from 'react-native'
import { MAIN_COLOR, COLOR_READY } from '../../common/color';



export default function TableItem({ item, handlePressTable }) {
    if (item.empty === true) {
        return <View style={styles.itemInvisible} />
    }



    return (
        <View style={[styles.container, { backgroundColor: COLOR_READY }]}>
            <View style={styles.body}>
                <TouchableOpacity style={styles.body}
                    onPress={() => handlePressTable(item)}
                >
                    <View style={styles.tableInformation}>
                        <Text
                            numberOfLines={1}
                            style={{ fontSize: 22, marginTop: 8 }}>
                            {item.tableName}
                        </Text>
                        <Text style={{ fontSize: 13 }}>
                            {`Số chỗ ngồi từ ${item.minCapacity} đến ${item.maxCapacity}`}
                        </Text>
                    </View>
                </TouchableOpacity>
            </View>
        </View>
    )

}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        height: 80,
        overflow: 'hidden',
        flexDirection: 'column',
        borderWidth: 2,
        borderRadius: 10,
        borderColor: 'rgba(0,0,0,0.3)',
        marginLeft: 8,
        marginVertical: 10,
    },
    header: {
        flex: 2,
        flexDirection: 'row',
        backgroundColor: MAIN_COLOR
    },

    itemInvisible: {
        flex: 1,
        height: 80,
        marginRight: 30,
        marginVertical: 10,
        backgroundColor: 'transparent',

    },
    body: {
        flex: 6,
        flexDirection: 'row',
    },
    tableInformation: {
        flex: 2,
        flexDirection: "column",
        justifyContent: 'space-between',
        paddingLeft: 5
    },
    icon: {
        width: 40,
        height: 40
    }
})
