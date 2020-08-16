import React, { forwardRef, useRef, useImperativeHandle, useState } from 'react'

import {
    View, StyleSheet, Text, Dimensions, Platform, TouchableOpacity,
    TextInput, TouchableWithoutFeedback, Keyboard, FlatList
} from 'react-native'
import Feather from 'react-native-vector-icons/Feather';
import Modal from 'react-native-modalbox'

import dishApi from '../../api/dishApi'
import orderApi from '../../api/orderApi';
import { showToast } from '../../common/functionCommon';


var screen = Dimensions.get('window')

function ToppingHavePriceItem({ item, handleChangeTopping }) {
    return (
        <View style={{
            flex: 1,
            height: 40,
            borderBottomColor: 'black',
            borderBottomWidth: 0.5,
            flexDirection: 'row',
            alignItems: 'flex-end',
        }}>

            <TouchableOpacity
                onPress={() => handleChangeTopping(item.optionId, 1)}
                style={{
                    flex: 1,
                    flexDirection: 'row',
                    justifyContent: 'center'
                }}
            >
                <Text style={{ color: 'black', flex: 1, fontSize: 16 }}>
                    {item.optionType == "MONEY" ?
                        item.optionName + ` (${new Intl.NumberFormat().format(item.optionPrice)} đ)` :
                        item.optionName}
                </Text>
            </TouchableOpacity>
            <Text style={{ fontSize: 22, marginHorizontal: 8 }}>{item.quantity}</Text>
            <TouchableOpacity
                onPress={() => handleChangeTopping(item.optionId, -1)}
            >
                <Feather name="minus" size={30} />
            </TouchableOpacity>


        </View>
    )
}

function ToppingNoPriceItem({ item, handleChangeTopping }) {
    return (
        <View style={{
            flex: 1,
            height: 40,
            borderBottomColor: 'black',
            borderBottomWidth: 0.5,
            flexDirection: 'row',
            alignItems: 'flex-end',
        }}>
            <TouchableOpacity
                onPress={() => handleChangeTopping(item.optionId)}
                style={{
                    flex: 1,
                    flexDirection: 'row',
                    alignItems: 'flex-end',
                }}
            >
                <Text style={{ color: 'black', flex: 1, fontSize: 16 }}>
                    {item.optionName}
                </Text>
                {
                    item.quantity == 1 && <Feather name="check" size={30} />
                }

            </TouchableOpacity>
        </View>
    )
}


// day la functionc chinh ----------------------------------------------
function ChangeTopping({ accessToken }, ref) {
    const [newComment, setNewComment] = useState('')
    const [dishOption, setDishOption] = useState({})
    const [orderDishOption, setOrderDishOption] = useState([])
    // function gennerateKey(number) {
    //     return Math.floor((Math.random() * 1000000000) + 1) + number;
    // }
    function loadAllOption(dishId, listOptionOrdered) {
        let conHangChuanChi = []

        dishApi.listOptionsByDishId(accessToken, dishId).then(
            response => {
                conHangChuanChi = response.map(option => {
                    let newOption
                    listOptionOrdered.forEach(item => {
                        if (item.optionId === option.optionId) {
                            newOption = {
                                orderDishOptionId: item.orderDishOptionId,
                                optionId: option.optionId,
                                optionName: option.optionName,
                                optionType: option.optionType,
                                optionUnit: option.unit,
                                quantity: item.quantity,
                                optionPrice: item.optionPrice,
                                sumPrice: item.sumPrice
                            }
                            return;
                        }
                    })
                    if (newOption != undefined) {
                        return newOption
                    } else {
                        return {
                            orderDishOptionId: 999999999,
                            optionId: option.optionId,
                            optionName: option.optionName,
                            optionType: option.optionType,
                            optionUnit: option.unit,
                            quantity: 0,
                            optionPrice: option.price,
                            sumPrice: 0
                        }
                    }
                })
                setOrderDishOption(conHangChuanChi)
            }
        ).catch(err => console.log(err))
    }

    const changeToppingRef = useRef(null);
    useImperativeHandle(ref, () => ({
        showChangeTopping: (item) => {
            setDishOption(item)
            setNewComment((item.comment == null || item.comment == "") ? "" : item.comment)
            loadAllOption(item.dish.dishId, item.orderDishOptions)
            changeToppingRef.current.open();
        }
    }));


    const caculateSellPrice = (options) => {

        let sellPrice = options.reduce((accumulator, currentValue) => {
            if (currentValue.quantity > 0 && currentValue.optionType == "MONEY") {
                return accumulator + currentValue.sumPrice
            } else {
                return accumulator
            }
        }, 0)
        return sellPrice

    }

    const handleAddDishWithOption = () => {
        const sellPrice = caculateSellPrice(orderDishOption)
        const newDishOrder = {
            orderDishId: dishOption.orderDishId,
            orderOrderId: dishOption.orderOrderId,
            quantity: dishOption.quantity,
            comment: newComment,
            sellPrice: sellPrice + dishOption.dish.defaultPrice,
            sumPrice: (sellPrice + dishOption.dish.defaultPrice) * dishOption.quantityOk,
            orderDishOptions: orderDishOption
        }
        orderApi.changeToppingInOrdered(accessToken, newDishOrder).then(
            response => {
                showToast("Thay đổi topping thành công")
            }
        ).catch(err => {
            if (err == "timeout") {
                showToast("Lỗi mạng! Thay đổi topping thất bại!")
            } else {
                showToast("Thay đổi topping thất bại!")
            }
        })
        changeToppingRef.current.close()
    }




    function handleChangeToppingHaveMoney(optionId, value) {
        let newArrayOption = [...orderDishOption]
        newArrayOption = newArrayOption.map(option => {
            if (option.optionId == optionId) {
                if (option.quantity <= 0 && value == -1) {
                    return option
                } else {
                    return {
                        ...option,
                        quantity: option.quantity + value,
                        sumPrice: option.sumPrice + option.optionPrice * value
                    }
                }
            } else {
                return option
            }
        })
        setOrderDishOption(newArrayOption)
    }
    function handleChangeToppingNoMoney(optionId) {
        let newArrayOption = [...orderDishOption]
        newArrayOption = newArrayOption.map(option => {
            if (option.optionId == optionId) {
                if (option.quantity == 0) {
                    return {
                        ...option,
                        quantity: 1
                    }
                } else {
                    return {
                        ...option,
                        quantity: 0
                    }
                }
            } else {
                return option
            }
        })
        setOrderDishOption(newArrayOption)
    }

    return (
        <Modal
            ref={changeToppingRef}
            style={{
                borderRadius: 15,
                shadowRadius: 10,
                width: screen.width - 60,
                height: 400,
                justifyContent: 'center',
                overflow: 'hidden'
            }}
            position='center'
            backdrop={true}
        >
            <TouchableWithoutFeedback style={styles.container} onPress={Keyboard.dismiss}>
                <View style={styles.container}>
                    <View style={styles.titleBar}>
                        <Text style={{ color: 'white', fontWeight: '700', fontSize: 18 }}>
                            {dishOption.dish != undefined ? dishOption.dish.dishName : ''}
                        </Text>
                        <TouchableOpacity
                            onPress={() => { changeToppingRef.current.close() }}
                        >
                            <Feather name="x" size={30} color='white' />
                        </TouchableOpacity>
                    </View>


                    <View style={styles.content}>
                        <Text style={{ marginTop: 10, fontSize: 16, fontWeight: '600' }}>Ghi chú:</Text>
                        <View style={{ flexDirection: 'row' }}>
                            <TextInput
                                onChangeText={text => setNewComment(text)}
                                value={newComment}
                                autoCorrect={false}
                                placeholder="Nhập ghi chú"
                                maxLength={100}
                                style={{
                                    flex: 1,
                                    height: 40,
                                    color: 'black',
                                    marginBottom: 10,
                                    fontSize: 16,
                                    borderBottomColor: 'gray',
                                    borderBottomWidth: 1,

                                }}
                            />
                            <TouchableOpacity
                                onPress={() => setNewComment('')}
                                style={{ marginTop: 10, marginLeft: 5 }}>
                                <Feather name="x" size={30} />
                            </TouchableOpacity>
                        </View>
                        <FlatList
                            data={orderDishOption}
                            style={{ flex: 1 }}
                            keyExtractor={(item, index) => item.optionId.toString()}
                            renderItem={({ item, index }) => item.optionType == "MONEY"
                                ? <ToppingHavePriceItem item={item} handleChangeTopping={handleChangeToppingHaveMoney} />
                                : <ToppingNoPriceItem item={item} handleChangeTopping={handleChangeToppingNoMoney} />
                            }
                        />

                        <View style={styles.buttonBar}>
                            <TouchableOpacity
                                onPress={handleAddDishWithOption}
                                style={[styles.button, { width: 100, borderRadius: 5 }]}>
                                <Text style={styles.textButton}>Đồng ý</Text>
                            </TouchableOpacity>
                        </View>
                    </View>
                </View>
            </TouchableWithoutFeedback>
        </Modal>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
    },
    titleBar: {
        flex: 1,
        flexDirection: 'row',
        backgroundColor: '#24C3A3',
        alignItems: "center",
        justifyContent: 'space-between',
        paddingHorizontal: 20,
    },
    content: {
        flex: 7,
        flexDirection: 'column',
        marginHorizontal: 20,
    },
    buttonBar: {
        flexDirection: 'row',
        paddingHorizontal: 20,
        marginVertical: 10,
        alignItems: 'center',
        justifyContent: 'center'

    },
    button: {
        height: 40,
        backgroundColor: '#24C3A3',
        justifyContent: 'center',
        alignItems: "center",
    },
    textButton: {
        color: 'white',
        fontSize: 18,
        fontWeight: '600',
        textAlign: "center"
    }
})

export default ChangeTopping = forwardRef(ChangeTopping);



