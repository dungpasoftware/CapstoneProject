import React from 'react'
import { StyleSheet, Text, View, Image, TouchableOpacity, Alert } from 'react-native'
import Feather from 'react-native-vector-icons/Feather';

export default function TableChildComponent({ item, _handleChangeStatusTable }) {
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
    let newHeight = isHaveDescription ? 65 : 45
    newHeight += isHaveComment ? 20 : 0

    function _clickTableChangeStatus() {
        if (item.statusId == 18) {
            Alert.alert(
                'Cảnh báo',
                `Chuyển bàn ${item.tableName} từ chưa làm sang đã hoàn thành ?`,
                [
                    {
                        text: 'Không',
                        style: 'cancel'
                    },
                    { text: 'Tôi chắc chắn', onPress: () => _handleChangeStatusTable(item.orderDishId, 20) }
                ],
                { cancelable: false }
            );
        } else {
            _handleChangeStatusTable(item.orderDishId, 20)
        }

    }


    return (
        <TouchableOpacity
            onPress={() => _clickTableChangeStatus()}
            style={{
                flex: 1,
                height: newHeight,
                flexDirection: 'row',

            }}>
            <Feather name="corner-down-right" size={22} style={{ alignSelf: 'center' }} />
            <View style={styles.container}>
                <View style={{ flex: 1, flexDirection: 'row', alignItems: 'center' }}>
                    <Text
                        style={{ marginHorizontal: 8, fontSize: 20, fontWeight: '500' }}
                    >
                        {item.quantityOk}
                    </Text>
                    <View
                        style={{ flex: 1, paddingHorizontal: 10, flexDirection: 'row', alignItems: 'center' }}
                    >
                        <Text
                            style={{ fontSize: 18 }}
                        >
                            {`${item.tableName}  (${item.timeOrder})`}
                        </Text>

                        {item.checkNotification &&
                            <Feather name="clock" size={18} color={'red'} style={{ marginLeft: 5, marginTop: 4 }} />
                        }
                    </View>
                    {item.statusId == 18 &&
                        <TouchableOpacity
                            onPress={() => _handleChangeStatusTable(item.orderDishId, 19)}
                        >
                            <Image style={{ width: 40, height: 40, marginHorizontal: 8 }} source={require('./../../../assets/pan.png')} />
                        </TouchableOpacity>
                    }
                </View>
                {isHaveDescription && <Text style={{ fontSize: 16, marginBottom: 3 }}>{getDescriptionDish()}</Text>}
                {isHaveComment &&
                    <View style={{ flexDirection: 'row', marginBottom: 2 }}>
                        <Feather name="edit-3" color='green' size={18} />
                        <Text numberOfLines={1} style={{ marginLeft: 5, textAlign: 'center', fontSize: 16 }}>{`${item.comment}`}</Text>
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
        paddingLeft: 5
    }
})
