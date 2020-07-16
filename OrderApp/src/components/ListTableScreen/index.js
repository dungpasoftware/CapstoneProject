import React, { useState, useEffect, useRef } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { StyleSheet, View, FlatList, TouchableOpacity, ActivityIndicator } from 'react-native'
import SideMenu from 'react-native-side-menu-updated'
import Feather from 'react-native-vector-icons/Feather';

import TableItem from './TableItem'
import FloorItem from './FloorItem'
import UserSideMenu from '../UserSideMenu'
import listTableRequest from '../../api/listTableRequest';
import { loadTable } from './../../actions/listTable'
import { createNewOrder, loadOrderInfomation } from './../../actions/dishOrdering'
import TableOption from './TableOption';
import TableOrderComment from './TableOrderComment';
import { ORDER_SCREEN } from '../../common/screenName';
import { MAIN_COLOR } from '../../common/color';




export default function ListTableScreen({ route, navigation }) {
    // sau lần chạy đầu tiên, thì sẽ gửi 1 request đọc list location, sau đó sẽ sử dụng location đầu tiên để đọc list table
    // thì sẽ set lai state 2 lần, có nên bỏ lần set state location và sử dụng lần set state table để load lại trang 1 thể
    const dispatch = useDispatch()
    const { userInfo } = route.params;
    const { accessToken } = userInfo
    const [listLocation, setListLocation] = useState([])
    const [locationTableId, setLocationTableId] = useState(1)

    const { listTable, isLoading } = useSelector(state => state.listTable)


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

    const handlePressTable = (item) => {
        if (item.statusValue == "READY") {
            dispatch(createNewOrder({ userInfo, tableId: item.tableId }))
        } else {
            dispatch(loadOrderInfomation({
                orderId: item.orderDto.orderId,
                orderCode: item.orderDto.orderCode,
                orderStatusId: item.orderDto.orderStatusId,
                tableId: item.tableId,
                totalAmount: item.orderDto.totalAmount,
                totalItem: item.orderDto.totalItem
            }))
        }
        navigation.navigate(ORDER_SCREEN, { accessToken, status: item.statusValue, tableName: item.tableName })
    }

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
    function showOptionDetail(option, itemSelected) {
        switch (option) {
            case 1: {
                console.log(itemSelected)
                break;
            }
            case 2: {
                tableOrderCommentRef.current.showTableOrderCommentBox(itemSelected)
                break
            }
            default: console.log(itemSelected)
                break;
        }

    }

    const tableOptionRef = useRef(null)
    function showTableOption(item) {
        tableOptionRef.current.showTableOptionBox(item);
    }
    const tableOrderCommentRef = useRef(null)
    function showTableOrderCommentBox() {
        tableOrderCommentRef.current.showTableOrderCommentBox();
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
                    {isLoading ? <ActivityIndicator style={{ marginTop: 15, alignSelf: 'center' }} size="large" color={MAIN_COLOR} />
                        : <FlatList
                            data={listTable}
                            keyExtractor={(item, index) => item.tableId.toString()}
                            numColumns={2}
                            renderItem={({ item, index }) => {
                                return (
                                    <TableItem item={item} index={index} handlePressTable={handlePressTable} showTableOption={showTableOption} />
                                )
                            }}
                        />
                    }
                </View>
                <TableOption ref={tableOptionRef} handleMenu={showOptionDetail} />
                <TableOrderComment ref={tableOrderCommentRef} />
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
