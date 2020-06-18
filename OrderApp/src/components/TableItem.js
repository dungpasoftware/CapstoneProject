import React, { Component } from 'react'
import { Text, StyleSheet, View, TouchableOpacity } from 'react-native'
import Feather from 'react-native-vector-icons/Feather';

export default class TableItem extends Component {
    render() {
        if (this.props.item.empty === true) {
            return <View style={styles.itemInvisible} />
        }
        return (
            <View style={styles.container}>
                <View style={styles.header}>
                    <Text>duc111</Text>
                    <TouchableOpacity>
                        <Feather name="chevron-right" size={20} />
                    </TouchableOpacity>
                </View>
                <View>
                    <Text>{this.props.item.name}</Text>
                </View>

                <Text>111111</Text>
            </View>
        )
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        height: 80,
        marginRight: 30,
        flexDirection: 'column',
        borderWidth: 2,
        marginVertical: 10
    },
    header: {
        flex: 1,
        flexDirection: 'row',
        backgroundColor: 'gray'
    },
    
    itemInvisible: {
        flex: 1,
        height: 80,
        marginRight: 30,
        marginVertical: 10,
        backgroundColor: 'transparent',

    }
})
