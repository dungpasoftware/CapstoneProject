import React from 'react'
import { Text, StyleSheet, View, TouchableOpacity, Image } from 'react-native'
import Feather from 'react-native-vector-icons/Feather';
import IconConstants from './../../common/IconConstants'
import { MAIN_COLOR, COLOR_BUSY, COLOR_READY, COLOR_ORDERED } from '../../common/color';
import { ORDER_SCREEN } from '../../common/screenName';


export default function TableItem({ item, navigation, showTableOption }) {
    if (item.empty === true) {
        return <View style={styles.itemInvisible} />
    }
    function generateIcon(status) {
        switch (status) {
            case "eat": return IconConstants.Eat
            case "cooking": return IconConstants.Cooking
            case "invoice": return IconConstants.Invoice
            case "kitchen": return IconConstants.Kitchen
            default: return ''
        }
    }
    function generateColor(status) {
        switch (status) {
            case "READY": return COLOR_READY
            case "BUSY": return COLOR_BUSY
            case "ORDERED": return COLOR_ORDERED
            default: return COLOR_READY
        }
    }

    return (
        <View style={[styles.container, { backgroundColor: generateColor(item.status.statusValue) }]}>
            <View style={styles.header}>
                <Text style={{ flex: 1, marginLeft: 5 }}>Dung</Text>
                <TouchableOpacity
                    onPress={() => showTableOption()}
                >
                    <Feather name="chevron-right" size={20} />
                </TouchableOpacity>
            </View>
            <View style={styles.body}>
                <TouchableOpacity style={styles.body}
                    onPress={() => navigation.navigate(ORDER_SCREEN)}
                >
                    <View style={styles.tableInformation}>
                        <Text style={{ fontSize: 22, marginTop: 8 }}>{item.tableName}</Text>
                        <Text>{item.orderDto.timeOrder}</Text>
                    </View>
                    <View style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
                        <Image style={styles.icon} source={generateIcon('kitchen')} />
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
