import React from 'react'
import { Text, StyleSheet, View, FlatList, TouchableOpacity, SafeAreaView } from 'react-native'
import dataTable from './dataTable'
import dataTableDetail from './dataTableDetail'
import TableItem from './TableItem'

function FloorItem({ item }) {

    return (
        <View style={styles.table_item_container}>
            <TouchableOpacity style={styles.table_item_container}>
                <Text style={{ color: '#24C3A3', fontSize: 18 }}>{item.name}</Text>
            </TouchableOpacity>
        </View>
    )

}
const formatData = (dataTableDetail, numColumns) => {
    const numberOfFullRows = Math.floor(dataTableDetail.length / numColumns);

    let numberOfElementsLastRow = dataTableDetail.length - (numberOfFullRows * numColumns)
    while (numberOfElementsLastRow !== numColumns && numberOfElementsLastRow !== 0) {
        dataTableDetail.push({ key: `black-${numberOfElementsLastRow}`, empty: true })
        numberOfElementsLastRow = numberOfElementsLastRow + 1
    }

    return dataTableDetail
}
export default function ListTableScreen({ navigation }) {


    return (
        <SafeAreaView style={styles.container}>
            <View style={styles.container}>
                <View style={{ flex: 3 }}>
                    <FlatList
                        data={dataTable}
                        renderItem={({ item, index }) => {
                            return (
                                <FloorItem item={item} index={index} />
                            )
                        }}
                    />
                </View>
                <View style={styles.line_view}></View>
                <View style={{ flex: 10, marginRight: 8 }}>
                    <FlatList
                        data={formatData(dataTableDetail, 2)}
                        numColumns={2}
                        renderItem={({ item, index }) => {
                            return (
                                <TableItem item={item} index={index} navigation={navigation} />
                            )
                        }}
                    />
                </View>
            </View>

        </SafeAreaView>
    )

}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'row'
    },
    table_item_container: {
        flex: 1,
        height: 60,
        width: 100,
        borderBottomWidth: 1,
        borderBottomColor: 'gray',
        justifyContent: "center",
        alignItems: "center"
    },
    line_view: {
        borderWidth: 1,
    },


})
