import React from 'react'
import { StyleSheet, Text, View, Image, TouchableOpacity } from 'react-native'
import Feather from 'react-native-vector-icons/Feather';

export default function DishChildComponent({ item, preparationADish, completedADish }) {
    const isHaveDescription = item.orderDishOptions.length > 0
    const isHaveComment = item.comment != null && item.comment != ""
    const getDescriptionDish = () => {
        let description = item.orderDishOptions.reduce((accumulator, currentValue, currentIndex, array) => {
            let newDescription = currentValue.optionType == "MONEY" ?
                `${currentValue.optionName}: ${currentValue.quantity}` :
                `${currentValue.optionName}`
            newDescription += currentIndex < array.length - 1 ? ', ' : ''
            return accumulator + newDescription
        }, '+ ')
        return description
    }
    let newHeight = isHaveDescription ? 60 : 45
    newHeight += isHaveComment ? 15 : 0


    return (
        <TouchableOpacity
            onPress={() => completedADish(item.orderDishId)}
            style={{
                flex: 1,
                height: newHeight,
                flexDirection: 'row',

            }}>
            <Feather name="corner-down-right" size={22} style={{ alignSelf: 'center' }} />
            <View style={[styles.container]}>
                <View style={{ flex: 1, flexDirection: 'row', alignItems: 'center' }}>
                    <Text
                        style={{ marginHorizontal: 8, fontSize: 20, fontWeight: '600' }}
                    >
                        {item.quantityOk}
                    </Text>
                    <Text
                        style={{ flex: 1, fontSize: 18, paddingHorizontal: 10 }}
                    >
                        {item.dishName}
                    </Text>
                    {item.statusId == 18 &&
                        <TouchableOpacity
                            onPress={() => preparationADish(item.orderDishId)}
                        >
                            <Image style={{ width: 35, height: 35, marginHorizontal: 8 }} source={require('./../../../assets/pan.png')} />
                        </TouchableOpacity>
                    }
                </View>

                {isHaveDescription && <Text>{getDescriptionDish()}</Text>}
                {isHaveComment && <View style={{ flexDirection: 'row' }}>
                    <Feather name="message-square" color='blue' size={14} />
                    <Text style={{ marginLeft: 5, textAlign: 'center' }}>{`${item.comment}`}</Text>
                </View>
                }
            </View>
        </TouchableOpacity>

    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        borderColor: 'rgba(0,0,0,0.5)',
        borderWidth: 0.5,
        backgroundColor: 'white',
        paddingLeft: 5,
        borderRadius: 5
    }
})
