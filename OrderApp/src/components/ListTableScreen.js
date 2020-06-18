import React, { Component } from 'react'
import { Text, StyleSheet, View, FlatList, TouchableHighlight, SafeAreaView } from 'react-native'
import dataTable from './dataTable'
import TableItem from './TableItem'

class FloorItem extends Component {
    render() {
        return (
            <View style={styles.table_item_container}>
                <TouchableHighlight>
                    <Text>{this.props.item.name}</Text>
                </TouchableHighlight>
            </View>
        )
    }
}
const formatData = (dataTable, numColumns) => {
    const numberOfFullRows = Math.floor(dataTable.length / numColumns);
    
    let numberOfElementsLastRow = dataTable.length - (numberOfFullRows * numColumns)
    while(numberOfElementsLastRow !== numColumns && numberOfElementsLastRow !== 0){
        dataTable.push({key: `black-${numberOfElementsLastRow}`, empty: true})
        numberOfElementsLastRow = numberOfElementsLastRow + 1
    }

    return dataTable
}
export default class ListTableScreen extends Component {

    render() {
        return (
            <SafeAreaView style={styles.container}>
                <View style={styles.container}>
                    <View style={{ flex: 1 }}>
                        <FlatList
                            data={dataTable}
                            horizontal={true}
                            renderItem={({ item, index }) => {
                                return (
                                    <FloorItem item={item} index={index} />
                                )
                            }}
                        />
                    </View>
                    <View style={styles.line_view}></View>
                    <View style={{ flex: 10, marginVertical: 10 }}>
                        <FlatList
                            data={formatData(dataTable, 2)}
                            numColumns={2}
                            renderItem={({ item, index }) => {
                                return (
                                    <TableItem item={item} index={index} />
                                )
                            }}
                        />
                    </View>
                </View>

            </SafeAreaView>
        )
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column'
    },
    table_item_container: {
        flex: 1,
        height: 50,
        width: 100,
        backgroundColor: 'red',
        marginRight: 20,
        borderWidth: 2,
        borderColor: 'yellow',
        borderRadius: 10,
    },
    line_view: {
        borderWidth: 2,
    },


})
