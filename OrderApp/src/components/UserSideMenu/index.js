import React from 'react'
import {
    Dimensions,
    StyleSheet,
    ScrollView,
    View,
    Image,
    Text,
    TouchableOpacity
} from 'react-native'
import AsyncStorage from '@react-native-community/async-storage';
import Feather from 'react-native-vector-icons/Feather';
import { LOGIN_SCREEN } from '../../common/screenName';

function ActionItem({ iconName, text, handle }) {
    return (
        <View style={{ height: 60, marginLeft: 15, borderBottomColor: 'white', borderBottomWidth: 1 }}>
            <TouchableOpacity
                style={{ flex: 1, flexDirection: "row", alignItems: 'center' }}
                onPress={handle}
            >
                <Feather style={{ marginRight: 10 }} name={iconName} size={30} color='white' />
                <Text style={styles.text}>{text}</Text>
            </TouchableOpacity>
        </View>
    )
}


const sceen = Dimensions.get('window');





export default function UserSideMenu({ navigation }) {
    const handleLogout = () => {
        AsyncStorage.removeItem('AccessToken', () => {
            navigation.navigate(LOGIN_SCREEN)
        })

    }
    return (
        <ScrollView scrollsToTop={false} style={styles.container}>
            <View style={styles.container}>
                <View style={styles.userInfo}>
                    <Image style={{ width: 70, height: 70 }} source={require('./../../assets/avatar.png')} />
                    <Text style={styles.text}>Pham Anh Dung</Text>
                </View>
                <View style={styles.userAction}>
                    <ActionItem iconName="plus-circle" text="Tạo đơn" />
                    <ActionItem iconName="log-out" text="Đăng xuất" handle={handleLogout} />
                </View>
            </View>
        </ScrollView>
    )
}



const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        backgroundColor: '#24C3A3',
        width: sceen.width / 3 * 2,
        height: sceen.height,
        paddingLeft: 10

    },
    userInfo: {
        flex: 1,
        flexDirection: 'column',
        borderBottomColor: 'white',
        borderBottomWidth: 1.5,
        justifyContent: 'space-evenly',
        alignItems: 'center'
    },
    userAction: {
        flex: 4,
        flexDirection: 'column',
        marginVertical: 20
    },
    text: {
        fontSize: 20,
        fontWeight: 'bold',
        color: 'white',
        textAlign: 'center'
    }
})
