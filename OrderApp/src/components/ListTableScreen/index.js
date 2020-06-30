import React, { useState } from 'react'
import { Text, StyleSheet, View, FlatList, TouchableOpacity } from 'react-native'
import SideMenu from 'react-native-side-menu-updated'
import Feather from 'react-native-vector-icons/Feather';

import dataTable from './../dataTable'
import dataTableDetail from './../dataTableDetail'
import TableItem from './TableItem'
import FloorItem from './FloorItem'
import SideMenuContain from './../../navigators/SideMenuContain'


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
    const menu = <SideMenuContain />
    const [open, setOpen] = useState(false)
    function openMenu() {
        let isOpen = open;
        setOpen(!isOpen)
    }

    React.useLayoutEffect(() => {
        navigation.setOptions({
            headerRight: () => (
                <TouchableOpacity
                    style={{ marginRight: 10 }}
                    onPress={() => openMenu()}
                >
                    <Feather name="menu" size={40} color='white' />
                </TouchableOpacity>
            ),
        });
    });
    return (
        <SideMenu
            menu={menu}
            isOpen={open}
            menuPosition='right'
            onChange={() => setOpen(!open)}
        >
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
        </SideMenu>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'row',
        backgroundColor: 'white'
    },
    line_view: {
        borderWidth: 1,
    },
})
