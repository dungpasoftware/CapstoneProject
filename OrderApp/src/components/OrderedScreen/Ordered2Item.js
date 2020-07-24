import React from 'react'
import { StyleSheet, Text, View, TouchableOpacity, Dimensions } from 'react-native'
import Feather from 'react-native-vector-icons/Feather';


function DishOptionItem({ dishOption, isCancel }) {
    return (
        <View style={{ flexDirection: 'row', marginHorizontal: 15, marginBottom: 2 }}>
            <Text style={[isCancel && styles.textLineThrough, { marginLeft: 3 }]}>{dishOption.optionType == "MONEY" ? dishOption.quantity : '+'}</Text>
            <Text style={[{ flex: 1, marginLeft: 25 }, isCancel && styles.textLineThrough]}>{dishOption.optionName}</Text>
            {dishOption.optionType == "MONEY" &&
                <Text style={[{ color: 'red', marginRight: 10 }, isCancel && styles.textLineThrough]}>
                    {`${new Intl.NumberFormat().format(dishOption.sumPrice)} đồng`}
                </Text>}
        </View>
    )
}


export default function Ordered2Item({ item, showOptionDish }) {
    var screen = Dimensions.get('window')
    const isCancel = item.statusStatusId == 22
    const sizeOfDishOption = item.orderDishOptions != null ? item.orderDishOptions.length : 0
    const sizeOfDishCancel = item.orderDishCancels != null ? item.orderDishCancels.length : 0
    const isNoComment = item.comment == null || item.comment == "" || item.comment == undefined
    let heightCaculate = 50 + (sizeOfDishOption * 22)
    heightCaculate = isNoComment ? heightCaculate : heightCaculate + 22
    heightCaculate += (sizeOfDishCancel * 22)

    const convertDate = (date) => {
        var d = new Date(date);
        return `${d.getHours()}:${d.getMinutes()}`
    }


    return (
        <View style={[styles.container, { height: heightCaculate }]}>
            <TouchableOpacity
                disabled={item.statusStatusId == 22 ? true : false}
                style={{
                    flex: 1,
                    flexDirection: 'column',

                    backgroundColor: '#f2f2f2',
                    borderColor: 'gray',
                    borderWidth: 0.5,
                    marginBottom: 5
                }}
                onPress={() => showOptionDish(item)}>
                <View style={{ flex: 1, flexDirection: 'row', height: 50, borderBottomColor: 'gray', borderBottomWidth: 0.5 }}>
                    <View
                        style={{
                            flex: 1,
                            justifyContent: 'center',
                            borderRightColor: 'gray',
                            borderRightWidth: 0.5,
                            alignItems: "center"
                        }}>
                        <Text style={[styles.text, isCancel && styles.textLineThrough]}>{item.quantityOk}</Text>
                    </View>
                    <View
                        style={{
                            flex: 5,
                            justifyContent: 'center',
                            marginHorizontal: 8,
                            justifyContent: 'space-evenly'
                        }}>
                        <Text numberOfLines={1} style={[styles.text, isCancel && styles.textLineThrough]}>{item.dish.dishName}</Text>

                    </View>
                    <View style={{ flex: 3, justifyContent: 'center' }}>
                        <Text
                            style={[{ color: 'red', textAlign: 'center', fontSize: 16 }, isCancel && styles.textLineThrough]}
                            numberOfLines={1}
                        >
                            {`${new Intl.NumberFormat().format(item.sumPrice)} đồng`}
                        </Text>
                    </View>
                </View>
                {item.orderDishCancels != null && item.orderDishCancels.map(dishCancel => {
                    return (<View key={dishCancel.orderDishCancelId}
                        style={{ height: 22, flexDirection: 'row', marginHorizontal: 5, alignItems: 'center' }}>
                        <Text
                            style={{
                                color: 'red',
                                fontSize: 16,
                                fontWeight: '500',
                                flex: 2,
                            }}>
                            {`- ${dishCancel.quantityCancel}`}
                        </Text>
                        <Feather name="message-square" color='red' size={14} />
                        <Text
                            style={{ flex: 12, marginLeft: 5, fontSize: 14 }}
                            numberOfLines={1}
                        >
                            {`${dishCancel.commentCancel != null ? dishCancel.commentCancel : ''}`}
                        </Text>
                        <Feather name="clock" color='red' size={14} />
                        <Text style={{ flex: 2, marginLeft: 2 }}>{convertDate(dishCancel.cancelDate)}</Text>
                    </View>)
                })
                }


            </TouchableOpacity>
            <View style={{ flexDirection: 'column' }}>
                {
                    item.orderDishOptions != null && item.orderDishOptions.map(dishOption => {
                        return <DishOptionItem dishOption={dishOption} key={dishOption.orderDishOptionId} isCancel={isCancel} />
                    })
                }
                {!isNoComment && <Text style={{ height: 22 }}>{`- ${item.comment}`}</Text>}
                {
                    (sizeOfDishOption > 0 || !isNoComment) && <View style={{ borderBottomColor: 'gray', borderBottomWidth: 0.5 }}></View>
                }

            </View>
        </View>
    )
}

const styles = StyleSheet.create({

    container: {
        flex: 1,
        flexDirection: 'column',


    },
    text: {
        fontSize: 16,
        fontWeight: '600',

    },
    textLineThrough: {
        textDecorationLine: 'line-through',
        textDecorationStyle: 'solid'
    }

})
