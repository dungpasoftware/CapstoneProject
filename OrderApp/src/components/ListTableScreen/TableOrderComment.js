import React, { forwardRef, useRef, useImperativeHandle, useState } from 'react'
import { View, StyleSheet, Text, Dimensions, Platform, TouchableOpacity, TextInput } from 'react-native'
import Modal from 'react-native-modalbox'


var screen = Dimensions.get('window')


function TableOrderComment(props, ref) {

    const [comment, setComment] = useState('')
    const [tableOrder, setTableOrder] = useState({})




    const tableOrderCommentRef = useRef(null);
    useImperativeHandle(ref, () => ({
        showTableOrderCommentBox: (item) => {
            console.log(item)
            setTableOrder(item)
            tableOrderCommentRef.current.open();
        }
    }));






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
                            maxLength={50}
                            autoCorrect={false}
                        />

                    </View>
                </View>
                <View style={{ flexDirection: 'row', flex: 1, justifyContent: 'space-evenly', alignItems: 'flex-end', marginBottom: 10 }}>
                    <TouchableOpacity
                        onPress={() => {
                            setComment('')
                            changeAPRef.current.close()
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
