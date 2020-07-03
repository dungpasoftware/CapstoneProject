import React, { useState, useEffect, useRef } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { StyleSheet, View, FlatList, TouchableOpacity } from 'react-native'
import SideMenu from 'react-native-side-menu-updated'
import Feather from 'react-native-vector-icons/Feather';

import TableItem from './TableItem'
import FloorItem from './FloorItem'
import UserSideMenu from '../UserSideMenu'
import listTableRequest from '../../api/listTableRequest';
import { loadTable } from './../../actions/listTable'
import TableOption from './TableOption';



export default function ListTableScreen({ route, navigation }) {
    // sau lần chạy đầu tiên, thì sẽ gửi 1 request đọc list location, sau đó sẽ sử dụng location đầu tiên để đọc list table
    // thì sẽ set lai state 2 lần, có nên bỏ lần set state location và sử dụng lần set state table để load lại trang 1 thể
    const dispatch = useDispatch()
    const { accessToken } = route.params;
    const [listLocation, setListLocation] = useState([])
    const [locationTableId, setLocationTableId] = useState(1)

    const listTable = useSelector(state => state.listTable.listTable)


    useEffect(() => {
        async function _retrieveTableData() {
            await dispatch(loadTable({ locationTableId, accessToken }))
        };
        _retrieveTableData()
    }, [locationTableId])

    useEffect(() => {
        async function _retrieveData() {
            const { listLocationAPI } = await listTableRequest.listAllLocation(accessToken)
            await setListLocation(listLocationAPI)
            await setLocationTableId(listLocationAPI[0].locationTableId)
        };
        _retrieveData()
    }, [])


    const menu = <UserSideMenu navigation={navigation} />
    const [open, setOpen] = useState(false)
    function openMenu() {
        let isOpen = open;
        setOpen(!isOpen)
    }

    React.useLayoutEffect(() => {
        navigation.setOptions({
            headerLeft: null,
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


    const tableOptionRef = useRef(null)
    function showTableOption() {
        tableOptionRef.current.showTableOptionBox();
    }


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
                        data={listLocation}
                        keyExtractor={(item, index) => item.locationTableId.toString()}
                        renderItem={({ item, index }) => {
                            return (
                                <FloorItem item={item} index={index} locationTableId={locationTableId} handleLoadTable={setLocationTableId} />
                            )
                        }}
                    />
                </View>
                <View style={styles.line_view}></View>
                <View style={{ flex: 10, marginRight: 8 }}>
                    <FlatList
                        data={listTable}
                        keyExtractor={(item, index) => item.tableId.toString()}
                        numColumns={2}
                        renderItem={({ item, index }) => {
                            return (
                                <TableItem item={item} index={index} navigation={navigation} showTableOption={showTableOption} />
                            )
                        }}
                    />
                </View>
                <TableOption ref={tableOptionRef} />
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
        borderRightWidth: 1,
        borderRightColor: 'rgba(0,0,0,0.6)'
    },
})
