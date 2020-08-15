import React, { useState, useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { StyleSheet, View, FlatList, ActivityIndicator } from 'react-native'

import TableItem from './TableItem'
import FloorItem from './FloorItem'
import orderApi from '../../api/orderApi'
import { ORDER_SCREEN } from '../../common/screenName'
import { changeTableId } from '../../actions/dishOrdering'
import { MAIN_COLOR } from '../../common/color'
import { showToast } from '../../common/functionCommon'


export default function SwitchTableScreen({ route, navigation }) {

    const { userInfo, rootOrder, status } = route.params;
    const { accessToken } = userInfo
    const [locationTableId, setLocationTableId] = useState(0)
    const [listTableScreen, setListTableScreen] = useState([])
    const [isLoading, setIsLoading] = useState(false)
    const dispatch = useDispatch()

    const listTable = useSelector(state => state.listTable.listTable)
    const listLocation = useSelector(state => state.listTable.listLocation)

    function addAllLocation(listLocation) {
        if (listLocation == null || listLocation.length == 0) return
        let newListLocation = [...listLocation]
        newListLocation.unshift({
            locationTableId: 0,
            locationCode: 'SPECIAL',
            locationName: 'Tất cả',
            statusValue: 'READY'
        })
        return newListLocation
    }

    const handlePressTable = (item) => {
        setIsLoading(true)
        const newData = {
            orderId: rootOrder.orderId,
            tableId: rootOrder.tableId,
            orderTakerStaffId: userInfo.staffId,
            modifiedBy: userInfo.staffCode,
            statusId: rootOrder.statusId
        }
        orderApi.switchTableOrder(accessToken, newData, item.tableId).then(response => {
            dispatch(changeTableId({ tableId: item.tableId }))
            showToast("Thay đổi bàn thành công!")
            setIsLoading(false)
            if (status == -1) {
                navigation.goBack()
            } else {
                navigation.navigate(ORDER_SCREEN,
                    {
                        userInfo,
                        status: status,
                        orderId: rootOrder.orderId,
                        tableName: item.tableName,
                    })
            }

        }).catch(error => {
            setIsLoading(false)
            showToast("Có lỗi xảy ra, Thay đổi bài thất bại!")
        })

    }

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
        async function _loadScreenTable() {
            let newListTable = [...listTable]
            if (locationTableId == 0) {
                newListTable = newListTable.filter(table => {
                    return table.statusId == 4
                })
            } else {
                newListTable = newListTable.filter(table => {
                    return table.locationId == locationTableId && table.statusId == 4
                })
            }
            newListTable = formatData(newListTable, 2)
            setListTableScreen(newListTable)
        }
        _loadScreenTable()

    }, [locationTableId, listTable])


    return (
        <View style={styles.container}>
            {isLoading ? <ActivityIndicator style={{ marginTop: 15, alignSelf: 'center' }} size="large" color={MAIN_COLOR} />
                : <View style={styles.container}>
                    <View>
                        <FlatList
                            data={addAllLocation(listLocation)}
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
                        <FlatList
                            data={listTableScreen}
                            keyExtractor={(item, index) => item.tableId.toString()}
                            numColumns={2}
                            renderItem={({ item, index }) => {
                                return (
                                    <TableItem item={item} index={index} handlePressTable={handlePressTable} />
                                )
                            }}
                        />

                    </View>
                </View>
            }
        </View>
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
