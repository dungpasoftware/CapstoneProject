import React, { useState, useEffect, useRef } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { StyleSheet, View, FlatList, TouchableOpacity, ActivityIndicator } from 'react-native'
import SideMenu from 'react-native-side-menu-updated'
import Feather from 'react-native-vector-icons/Feather';

import TableItem from './TableItem'
import FloorItem from './FloorItem'
import UserSideMenu from '../UserSideMenu'
import listTableRequest from '../../api/listTableRequest';
import { loadTable, loadTableSuccess } from './../../actions/listTable'
import { createNewOrder, loadOrderInfomation } from './../../actions/dishOrdering'
import TableOption from './TableOption';
import TableOrderComment from './TableOrderComment';
import { ORDER_SCREEN } from '../../common/screenName';
import { MAIN_COLOR } from '../../common/color';

// socket
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
const ENDPOINT = "http://192.168.1.29:8080";


export default function ListTableScreen({ route, navigation }) {

    const dispatch = useDispatch()
    const { userInfo } = route.params;
    const { accessToken } = userInfo
    const [listLocation, setListLocation] = useState([])
    const [locationTableId, setLocationTableId] = useState(1)
    const [listTableScreen, setListTableScreen] = useState([])

    const { listTable, isLoading } = useSelector(state => state.listTable)

    useEffect(() => {
        let socket = new SockJS(`${ENDPOINT}/rms-websocket`);
        let stompClient = Stomp.over(socket);
        stompClient.debug = () => { }
        stompClient.connect(
            {
                token: accessToken
            },
            frame => {
                console.log('connected');
                stompClient.subscribe("/topic/tables", ({ body }) => {
                    let tableData = JSON.parse(body);
                    dispatch(loadTableSuccess(tableData))
                });
            },
            error => {
                console.log(error);
            }
        );

        return () => stompClient.disconnect();
    }, []);
    function formatData(dataTableDetail, numColumns) {
        const numberOfFullRows = Math.floor(dataTableDetail.length / numColumns);

        let numberOfElementsLastRow = dataTableDetail.length - (numberOfFullRows * numColumns)
        while (numberOfElementsLastRow !== numColumns && numberOfElementsLastRow !== 0) {
            dataTableDetail.push({ tableId: `black-${numberOfElementsLastRow}`, empty: true })
            numberOfElementsLastRow = numberOfElementsLastRow + 1
        }

        return dataTableDetail
    }

    useEffect(() => {
        async function _retrieveData() {
            const { listLocationAPI } = await listTableRequest.listAllLocation(accessToken)
            let newListLocation = [...listLocationAPI]
            newListLocation.unshift({
                locationTableId: 0,
                locationCode: 'SPECIAL',
                locationName: 'Đang mở',
                statusValue: 'READY'
            })
            await setListLocation(newListLocation)
            await dispatch(loadTable({ accessToken }))
            await setLocationTableId(newListLocation[0].locationTableId)
        };
        _retrieveData()
    }, [])

    useEffect(() => {
        async function _loadScreenTable() {
            let newListTable = [...listTable]
            if (locationTableId == 0) {
                newListTable = newListTable.filter(table => {
                    return table.statusId == 5 || table.statusId == 6
                })
            } else {
                newListTable = newListTable.filter(table => {
                    return table.locationId == locationTableId
                })
            }
            newListTable = formatData(newListTable, 2)
            setListTableScreen(newListTable)
        }
        _loadScreenTable()

    }, [locationTableId, listTable])



    const handlePressTable = (item) => {
        if (item.statusValue == "READY") {
            dispatch(createNewOrder({ userInfo }))
        } else {
            dispatch(loadOrderInfomation({
                orderId: item.orderDto.orderId,
                orderCode: item.orderDto.orderCode,
                statusId: item.orderDto.statusId,
                tableId: item.tableId,
                totalAmount: item.orderDto.totalAmount,
                totalItem: item.orderDto.totalItem
            }))
            navigation.navigate(ORDER_SCREEN, { accessToken, status: item.statusValue, orderId: item.orderDto.orderId })
        }

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
                showTableOrderCommentBox(itemSelected)
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
    function showTableOrderCommentBox(itemSelected) {
        tableOrderCommentRef.current.showTableOrderCommentBox(itemSelected);
    }


    return (
        <SideMenu
            menu={menu}
            isOpen={open}
            menuPosition='right'
            onChange={() => setOpen(!open)}
        >
            <View style={styles.container}>
                <View style={{}}>
                    <FlatList
                        data={listLocation}
                        horizontal={true}
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
                            data={listTableScreen}
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
                <TableOrderComment ref={tableOrderCommentRef} accessToken={accessToken} />
            </View>
        </SideMenu>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        backgroundColor: 'white'
    },
    line_view: {
        borderBottomWidth: 1,
        borderBottomColor: 'rgba(0,0,0,0.6)'
    },
})
