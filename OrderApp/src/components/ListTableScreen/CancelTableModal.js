import React, { forwardRef, useRef, useImperativeHandle, useState } from 'react'
import { View, StyleSheet, Text, Dimensions, Platform, TouchableOpacity, TextInput } from 'react-native'

import orderApi from '../../api/orderApi'
import Modal from 'react-native-modalbox'
import { LIST_TABLE_SCREEN } from '../../common/screenName'
import { showToast } from '../../common/functionCommon'


var screen = Dimensions.get('window')


function CancelTableModal({ userInfo, navigation }, ref) {
    const { accessToken, staffId, staffCode } = userInfo
    const [comment, setComment] = useState('')
    const [tableOrder, setTableOrder] = useState({})
    const [messageError, setMessageError] = useState('')




    const cancelTableOrderRef = useRef(null);
    useImperativeHandle(ref, () => ({
        showCancelTableModal: (item) => {
            let isTable = item.orderDto == null ? isTable = false : isTable = true;
            setTableOrder({
                isTable: isTable,
                orderId: isTable ? item.orderDto.orderId : item.orderId,
                statusId: isTable ? item.orderDto.statusId : item.statusId,
                tableId: item.tableId,
                tableName: item.tableName
            })
            setComment('')
            setMessageError("")
            cancelTableOrderRef.current.open();
        }
    }));


    const _handleAcceptCancalTable = () => {
        if (comment.trim() == "") {
            setMessageError("Vui lòng nhập lí do hủy.")
            return
        }
        let dataCancel = {
            orderId: tableOrder.orderId,
            statusId: tableOrder.statusId,
            tableId: tableOrder.tableId,
            staffId: staffId,
            modifiedBy: staffCode,
            comment: comment.trim()
        }
        orderApi.cancelTableOrder(accessToken, dataCancel).then((res) => {
            setMessageError("")
            showToast('Hủy bàn thành công!')
            !dataCancel.isTable && navigation.navigate(LIST_TABLE_SCREEN, { userInfo })
        }).catch((err) => {
            if (err == "timeout") {
                showToast("Có gì đó xảy ra, hủy bàn thất bại.")
            } else {
                showToast("Có gì đó xảy ra, hủy bàn thất bại.")
            }
            setMessageError("")
        })
        cancelTableOrderRef.current.close();

    }




    return (
        <Modal
            ref={cancelTableOrderRef}
            style={{
                borderRadius: 15,
                shadowRadius: 10,
                width: screen.width - 50,
                height: 250,
                justifyContent: 'center',
                overflow: 'hidden'
            }}
            position='center'
            backdrop={true}
        >
            <View style={styles.container}>
                <View style={{ flex: 1, backgroundColor: '#24C3A3', justifyContent: "center", alignItems: "center" }}>
                    <Text style={{ textAlign: "center", color: 'white', fontSize: 20, fontWeight: 'bold', textAlign: "center" }}>
                        {`Huỷ bàn ${tableOrder.tableName}`}
                    </Text>
                </View>
                <Text style={{ fontSize: 16, fontWeight: '600', marginTop: 10, marginLeft: 10, marginBottom: 5 }}>Lí do:</Text>
                <View style={{ flex: 3, flexDirection: 'column', marginHorizontal: 20, }}>
                    <View style={{

                        flexDirection: 'row',
                        borderBottomColor: 'gray',
                        borderBottomWidth: 1,
                        alignItems: 'center',

                    }}>
                        {/* <Feather name='phone' size={26} color='#24C3A3' /> */}

                        <TextInput style={{
                            flex: 1,
                            color: 'black',
                            fontSize: 16,
                            marginLeft: 10,
                            textAlignVertical: 'bottom'
                        }}
                            onChangeText={text => setComment(text)}
                            placeholder="Nhập lí do"
                            value={comment}
                            multiline={true}
                            maxLength={100}
                            autoCorrect={false}
                        />

                    </View>
                    {(messageError != "" && comment.trim() == "") &&
                        <Text style={{ color: 'red', fontSize: 16, marginLeft: 8, marginTop: 5 }}>{messageError}</Text>}
                </View>
                <View
                    style={{
                        flexDirection: 'row',
                        flex: 1,
                        justifyContent: 'space-evenly',
                        alignItems: 'flex-end',
                        marginBottom: 10,
                        marginHorizontal: 10,
                    }}>

                    <TouchableOpacity
                        onPress={() => {
                            setMessageError("")
                            cancelTableOrderRef.current.close()
                        }}
                        style={{
                            backgroundColor: 'red', flex: 1, height: 40, alignItems: "center",
                            justifyContent: 'center', marginHorizontal: 5, borderRadius: 5
                        }}>
                        <Text
                            style={{ textAlign: 'center', color: 'white', fontSize: 18, fontWeight: 'bold' }}
                        >Hủy
                        </Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        onPress={_handleAcceptCancalTable}
                        style={{
                            backgroundColor: 'green', flex: 1, height: 40, alignItems: "center",
                            justifyContent: 'center', marginHorizontal: 5, borderRadius: 5
                        }}>
                        <Text
                            style={{
                                textAlign: 'center', color: 'white',
                                fontSize: 18, fontWeight: 'bold'
                            }}
                        >
                            Đồng ý
                            </Text>
                    </TouchableOpacity>
                </View>
            </View>
        </Modal >
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
    }
})

export default CancelTableModal = forwardRef(CancelTableModal);
