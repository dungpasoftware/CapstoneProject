import React, { forwardRef, useRef, useImperativeHandle, useState } from 'react'
import { View, StyleSheet, Text, Dimensions, Platform, TouchableOpacity, TextInput } from 'react-native'


import Modal from 'react-native-modalbox'


var screen = Dimensions.get('window')


function CancelDishModal({ submitCancelDish }, ref) {

    const [comment, setComment] = useState('')
    const [cancelInfo, setCancelInfo] = useState({})




    const cancelDishModalRef = useRef(null);
    useImperativeHandle(ref, () => ({
        showCancelDishModalBox: (item) => {

            setCancelInfo({
                statusStatusId: item.statusStatusId,
                orderDishId: item.orderDishId,
                orderOrderId: item.orderOrderId,
                dishName: item.dish.dishName
            })
            cancelDishModalRef.current.open();
        }
    }));


    const _handleSubmitCancelDish = () => {
        submitCancelDish({
            statusStatusId: cancelInfo.statusStatusId,
            orderDishId: cancelInfo.orderDishId,
            orderOrderId: cancelInfo.orderOrderId,
            comment: comment
        })
        setComment('')
        cancelDishModalRef.current.close();

    }




    return (
        <Modal
            ref={cancelDishModalRef}
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
                        {`Hủy món ${cancelInfo.dishName}`}
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
                            placeholder="Nhập lí do hủy món"
                            value={comment}
                            multiline={true}
                            numberOfLines={3}
                            maxLength={80}
                            autoCorrect={false}
                        />

                    </View>
                </View>
                <View style={{ flexDirection: 'row', flex: 1, justifyContent: 'space-evenly', alignItems: 'flex-end', marginBottom: 10 }}>
                    <TouchableOpacity
                        onPress={() => {
                            setComment('')
                            cancelDishModalRef.current.close()
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
                        onPress={_handleSubmitCancelDish}
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

export default CancelDishModal = forwardRef(CancelDishModal);
