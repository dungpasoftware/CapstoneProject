import React, { forwardRef, useRef, useImperativeHandle, useState } from 'react'
import { View, StyleSheet, Text, Dimensions, Platform, TouchableOpacity, TextInput } from 'react-native'
import Feather from 'react-native-vector-icons/Feather';


import Modal from 'react-native-modalbox'


var screen = Dimensions.get('window')


function CancelDishModal({ submitCancelDish }, ref) {

    const [comment, setComment] = useState('')
    const [cancelQuantity, setCancelQuantity] = useState(1)
    const [cancelInfo, setCancelInfo] = useState({})
    const [messageError, setMessageError] = useState('')




    const cancelDishModalRef = useRef(null);
    useImperativeHandle(ref, () => ({
        showCancelDishModalBox: (item) => {

            setCancelInfo({
                statusStatusId: item.statusStatusId,
                orderDishId: item.orderDishId,
                orderOrderId: item.orderOrderId,
                dishName: item.dish.dishName,
                quantity: item.quantity,
                quantityOk: item.quantityOk
            })
            setMessageError("")
            setComment("")
            setCancelQuantity(1)
            cancelDishModalRef.current.open();
        }
    }));


    const _handleSubmitCancelDish = () => {

        if (comment.trim() == "") {
            setMessageError("Vui lòng nhập lí do hủy.")
            return
        }
        let dataCancel = {
            statusStatusId: cancelInfo.statusStatusId,
            orderDishId: cancelInfo.orderDishId,
            orderOrderId: cancelInfo.orderOrderId,
            quantityCancel: cancelQuantity,
            commentCancel: comment,
        }
        submitCancelDish(dataCancel)
        setMessageError("")
        setComment('')
        cancelDishModalRef.current.close();

    }

    function _handleChangeCancelQuantity(value) {
        if (value == -1 && cancelQuantity <= 1) return
        if (value == 1 && cancelQuantity >= cancelInfo.quantityOk) return

        setCancelQuantity(value + cancelQuantity)
    }




    return (
        <Modal
            ref={cancelDishModalRef}
            style={{
                borderRadius: 15,
                shadowRadius: 10,
                width: screen.width - 50,
                height: 350,
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
                <Text style={{ textAlign: 'center', marginVertical: 5, fontSize: 14, fontWeight: '500' }}>Số lượng hủy:</Text>
                <View
                    style={{
                        flexDirection: "row",
                        marginHorizontal: 8,
                        alignItems: 'center',
                        justifyContent: 'center',

                    }}>
                    <TouchableOpacity
                        onPress={() => _handleChangeCancelQuantity(-1)}
                    >
                        <Feather name="minus-circle" color='red' size={40} />
                    </TouchableOpacity>
                    <Text style={{ textAlign: "center", marginHorizontal: 8, fontSize: 20, fontWeight: '500' }}>{cancelQuantity}</Text>
                    <TouchableOpacity
                        onPress={() => _handleChangeCancelQuantity(1)}
                    >
                        <Feather name="plus-circle" color='green' size={40} />
                    </TouchableOpacity>
                </View>

                <View style={{ flex: 3, flexDirection: 'column', marginHorizontal: 20, }}>
                    <Text style={{ marginVertical: 5, fontSize: 15, fontWeight: '500' }}>Lý do hủy:</Text>
                    <View style={{

                        flexDirection: 'row',
                        borderBottomColor: 'gray',
                        borderBottomWidth: 1,
                        alignItems: 'center',
                        marginTop: 5

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
                            placeholder="Nhập lí do hủy món"
                            value={comment}
                            multiline={true}
                            numberOfLines={3}
                            maxLength={200}
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
                        marginHorizontal: 10
                    }}>


                    <TouchableOpacity
                        onPress={() => {
                            setMessageError("")
                            setComment('')
                            cancelDishModalRef.current.close()
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
                        onPress={_handleSubmitCancelDish}
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

export default CancelDishModal = forwardRef(CancelDishModal);
