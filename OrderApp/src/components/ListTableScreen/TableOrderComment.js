import React, { forwardRef, useRef, useImperativeHandle, useState } from 'react'
import { View, StyleSheet, Text, Dimensions, Platform, TouchableOpacity, TextInput } from 'react-native'

import orderRequest from '../../api/orderRequest'
import Modal from 'react-native-modalbox'


var screen = Dimensions.get('window')


function TableOrderComment({ accessToken }, ref) {

    const [comment, setComment] = useState('')
    const [tableOrder, setTableOrder] = useState({})




    const tableOrderCommentRef = useRef(null);
    useImperativeHandle(ref, () => ({
        showTableOrderCommentBox: (item) => {
            let isTable = item.orderDto == null ? isTable = false : isTable = true;
            const newComment = isTable ? (item.orderDto.comment != null ? item.orderDto.comment : '')
                : (item.comment != null ? item.comment : '')
            setTableOrder({
                orderId: isTable ? item.orderDto.orderId : item.orderId,
                comment: newComment,
                tableName: item.tableName
            })
            setComment(newComment)
            tableOrderCommentRef.current.open();
        }
    }));


    const _handleSubmitComment = () => {
        tableOrder.comment !== comment.trim() &&
            orderRequest.changeCommentByOrderId(accessToken, { orderId: tableOrder.orderId, comment: comment.trim() })
        setComment('')
        tableOrderCommentRef.current.close();

    }




    return (
        <Modal
            ref={tableOrderCommentRef}
            style={{
                borderRadius: Platform.OS == 'ios' ? 15 : 0,
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
                        {tableOrder.tableName}
                    </Text>
                </View>

                <View style={{ flex: 3, flexDirection: 'column', marginHorizontal: 20, marginTop: 15, }}>
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
                            marginLeft: 10
                        }}
                            onChangeText={text => setComment(text)}
                            placeholder="Nhập ghi chú"
                            value={comment}
                            multiline={true}
                            numberOfLines={3}
                            maxLength={80}
                            autoCorrect={false}
                        />

                    </View>
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
                            setComment('')
                            tableOrderCommentRef.current.close()
                        }}
                        style={{
                            backgroundColor: 'red', flex: 1, height: 40, alignItems: "center",
                            justifyContent: 'center', marginHorizontal: 5
                        }}>
                        <Text
                            style={{ textAlign: 'center', color: 'white', fontSize: 18, fontWeight: 'bold' }}
                        >Hủy
                        </Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        onPress={_handleSubmitComment}
                        style={{
                            backgroundColor: 'green', flex: 1, height: 40, alignItems: "center",
                            justifyContent: 'center', marginHorizontal: 5
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

export default TableOrderComment = forwardRef(TableOrderComment);
