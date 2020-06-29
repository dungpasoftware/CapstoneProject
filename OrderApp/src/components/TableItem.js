import React from 'react'
import { Text, StyleSheet, View, TouchableOpacity, Image } from 'react-native'
import Feather from 'react-native-vector-icons/Feather';
import IconConstants from './../common/IconConstants'


export default function TableItem({ item, navigation }) {
    if (item.empty === true) {
        return <View style={styles.itemInvisible} />
    }
    function generateIcon(status) {
        switch (status) {
            case "eat": return IconConstants.Eat
            case "cooking": return IconConstants.Cooking
            case "invoice": return IconConstants.Invoice
            default: return IconConstants.Cooking
        }
    }
    return (
        <View style={styles.container}>
            <View style={styles.header}>
                <Text style={{ flex: 1, marginLeft: 5 }}>{item.orderName}</Text>
                <TouchableOpacity >
                    <Feather name="chevron-right" size={20} />
                </TouchableOpacity>
            </View>
            <View style={styles.body}>
                <TouchableOpacity style={styles.body}
                    onPress={() => navigation.navigate('OrderScreen')}
                >
                    <View style={styles.tableInformation}>
                        <Text style={{ fontSize: 22, marginTop: 8 }}>{item.name}</Text>
                        <Text>{item.time}</Text>
                    </View>
                    <View style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
                        <Image style={styles.icon} source={generateIcon(item.icon)} />
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
        flexDirection: 'column',
        borderWidth: 1,
        borderRadius: 10,
        marginLeft: 8,
        marginVertical: 10
    },
    header: {
        flex: 2,
        flexDirection: 'row',
        borderTopStartRadius: 10,
        borderTopEndRadius: 10,
        backgroundColor: '#24C3A3'
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
