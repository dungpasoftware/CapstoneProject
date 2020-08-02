import React from 'react'
import { Text, StyleSheet, View, TouchableOpacity, Image } from 'react-native'
import Feather from 'react-native-vector-icons/Feather';
import IconConstants from './../../common/IconConstants'
import { MAIN_COLOR, COLOR_BUSY, COLOR_READY, COLOR_ORDERED } from '../../common/color';



export default function TableItem({ item, handlePressTable, showTableOption }) {
    if (item.empty === true) {
        return <View style={styles.itemInvisible} />
    }
    function generateIcon(status) {
        switch (status) {
            case "COMPLETED": return IconConstants.Eat
            case "PREPARATION": return IconConstants.Cooking
            case "WAITING_FOR_PAYMENT": return IconConstants.Invoice
            case "JUST_COOKED": return IconConstants.Kitchen
            case "ORDERED": return IconConstants.Ordered
            default: return null
        }
    }
    function generateColor(status) {
        switch (status) {
            case "READY": return COLOR_READY
            case "BUSY": return COLOR_BUSY
            case "ORDERED": return COLOR_ORDERED
            default: return COLOR_ORDERED
        }
    }
    return (
        <View style={[styles.container, { backgroundColor: generateColor(item.statusValue) }]}>
            <View style={styles.header}>
                <Text style={{ flex: 1, marginLeft: 5 }}>{item.staffDto != null ? item.staffDto.staffCode : ""}</Text>
                {!(item.orderDto == null) && <TouchableOpacity
                    onPress={() => showTableOption(item)}
                >
                    <Feather name="chevron-right" size={20} />
                </TouchableOpacity>}
            </View>
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
                            {item.statusValue == 'READY' ?
                                `Số chỗ ngồi từ ${item.minCapacity} đến ${item.maxCapacity}`
                                : item.orderDto != null ? item.orderDto.orderTime : ""}
                        </Text>
                    </View>
                    {item.statusValue !== 'READY' && <View style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
                        {(item.orderDto != null && generateIcon(item.orderDto.orderStatusValue) !== null)
                            && <Image style={styles.icon} source={generateIcon(item.orderDto.orderStatusValue)} />}
                    </View>}

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
