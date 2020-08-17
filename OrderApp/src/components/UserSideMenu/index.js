import React from 'react'
import { useDispatch } from 'react-redux'
import {
    Dimensions,
    StyleSheet,
    ScrollView,
    View,
    Image,
    Text,
    TouchableOpacity,
    AsyncStorage
} from 'react-native'

import Feather from 'react-native-vector-icons/Feather';


import { LOGIN_SCREEN } from '../../common/screenName';
import { actionLogout } from '../../actions/loginAction'


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


export default function UserSideMenu({ openMenu, navigation, userInfo }) {
    const dispatch = useDispatch()
    const _handleLogout = () => {
        openMenu()
        AsyncStorage.removeItem('AccessToken', () => {
            dispatch(actionLogout())
            navigation.navigate(LOGIN_SCREEN)
        })

    }
    const _handleLoadNotification = () => {
        openMenu()


    }
    return (
        <ScrollView scrollsToTop={false} style={styles.container}>
            <View style={styles.container}>
                <View style={styles.userInfo}>
                    <Image style={{ width: 70, height: 70 }} source={require('./../../assets/avatar.png')} />
                    <Text style={styles.text}>{userInfo.staffCode}</Text>
                </View>
                <View style={styles.userAction}>
                    {/* <ActionItem iconName="bell" text="Thông báo" handle={_handleLoadNotification} /> */}
                    <ActionItem iconName="log-out" text="Đăng xuất" handle={_handleLogout} />
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
        textAlign: 'center',
    }
})
