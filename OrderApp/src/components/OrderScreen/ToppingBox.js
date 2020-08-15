import React, { forwardRef, useRef, useImperativeHandle, useState } from 'react'
import { useDispatch } from 'react-redux'
import {
    View, StyleSheet, Text, Dimensions, Platform, TouchableOpacity,
    TextInput, TouchableWithoutFeedback, Keyboard, FlatList
} from 'react-native'
import Feather from 'react-native-vector-icons/Feather';
import Modal from 'react-native-modalbox'

import { addNewDish, changeOptionDishOrdering } from '../../actions/dishOrdering'

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
                    alignItems: 'flex-end'
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

function ToppingBox(props, ref) {

    const [dishOption, setDishOption] = useState({})
    const [newComment, setNewComment] = useState('')
    const [orderDishOption, setOrderDishOption] = useState([])

    var isNewDish = true;

    const toppingBoxRef = useRef(null);
    useImperativeHandle(ref, () => ({
        showToppingBox: (item) => {
            isNewDish = item.quantity == undefined
            let newArrayOptions = isNewDish ? item.options : item.orderDishOptions
            !isNewDish && setNewComment(item.comment)
            setDishOption(item)
            newArrayOptions = newArrayOptions.map(option => {
                return {
                    optionId: option.optionId,
                    optionName: option.optionName,
                    optionType: option.optionType,
                    optionUnit: isNewDish ? option.unit : option.optionUnit,
                    quantity: isNewDish ? 0 : option.quantity,
                    optionPrice: isNewDish ? option.price : option.optionPrice,
                    sumPrice: isNewDish ? 0 : option.sumPrice
                }
            })
            setOrderDishOption(newArrayOptions)
            toppingBoxRef.current.open();
        }
    }));


    const dispatch = useDispatch();
    const caculateSellPrice = (options) => {
        let infoOption = options.reduce((accumulator, currentValue,) => {
            if (currentValue.quantity > 0) {
                if (currentValue.optionType == "MONEY") {
                    return {
                        sellPrice: accumulator.sellPrice + currentValue.sumPrice,
                        codeCheck: accumulator.codeCheck.concat(`${currentValue.optionId}${currentValue.quantity}`)
                    }
                }
                return {
                    ...accumulator,
                    codeCheck: accumulator.codeCheck.concat(`${currentValue.optionId}${currentValue.quantity}`)
                }
            } else {
                return accumulator
            }
        }, { sellPrice: 0, codeCheck: '' })
        return infoOption
    }
    function gennerateKey(number) {
        return Math.floor((Math.random() * 1000000000) + 1) + number;
    }

    const handleAddDishWithOption = () => {
        const infoOption = caculateSellPrice(orderDishOption)
        const newDishOrder = {
            orderDishId: gennerateKey(dishOption.dishId),
            quantity: 1,
            notEnoughMaterial: false,
            codeCheck: `${dishOption.dishId}`.concat(infoOption.codeCheck).concat(newComment),
            sellPrice: infoOption.sellPrice + dishOption.defaultPrice,
            sumPrice: infoOption.sellPrice + dishOption.defaultPrice,
            comment: newComment.trim(),
            dish: {
                dishId: dishOption.dishId,
                dishName: dishOption.dishName,
                dishUnit: dishOption.dishUnit,
                defaultPrice: dishOption.defaultPrice,
            },
            orderDishOptions: orderDishOption
        }
        const action = addNewDish(newDishOrder)
        dispatch(action)
        setNewComment("")
        toppingBoxRef.current.close()
    }


    const handleEditDishWithOption = () => {
        const infoOption = caculateSellPrice(orderDishOption)
        let codeCheck = `${dishOption.dish.dishId}`.concat(infoOption.codeCheck).concat(newComment)
        // check xem mấy thằng lòn có thay đổi gì không, nếu không thay đổi thì del làm gì cả
        if (codeCheck == dishOption.codeCheck && newComment.trim() == dishOption.comment) {
            toppingBoxRef.current.close()
            return;
        }
        const newDishOrder = {
            orderDishId: dishOption.orderDishId,
            quantity: dishOption.quantity,
            codeCheck: codeCheck,
            sellPrice: infoOption.sellPrice + dishOption.dish.defaultPrice,
            sumPrice: (infoOption.sellPrice + dishOption.dish.defaultPrice) * dishOption.quantity,
            comment: newComment.trim(),
            dish: { ...dishOption.dish },
            orderDishOptions: orderDishOption
        }
        const action = changeOptionDishOrdering({ newDishOrder })
        dispatch(action)
        setNewComment("")
        toppingBoxRef.current.close()
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
            ref={toppingBoxRef}
            style={{
                borderRadius: Platform.OS == 'ios' ? 15 : 0,
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
                            {dishOption.codeCheck == undefined ? dishOption.dishName : dishOption.dish.dishName}
                        </Text>
                        <TouchableOpacity
                            onPress={() => { toppingBoxRef.current.close() }}
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
                                maxLength={40}
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
                                onPress={dishOption.codeCheck == undefined ? handleAddDishWithOption : handleEditDishWithOption}
                                style={[styles.button, { width: 100 }]}>
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

export default ToppingBox = forwardRef(ToppingBox);
