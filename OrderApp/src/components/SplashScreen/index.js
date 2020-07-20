import React, { useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { Text, StyleSheet, View, ActivityIndicator } from 'react-native'
import AsyncStorage from '@react-native-community/async-storage';
import { MAIN_COLOR } from '../../common/color';
import { checkToken } from '../../actions/loginAction';
import { LOGIN_SCREEN, LIST_TABLE_SCREEN, KITCHEN_SCREEN } from '../../common/screenName';
import { ROLE_ORDER_TAKER, ROLE_CHEF } from '../../common/roleType';




export default function SplashScreen({ navigation }) {
    const dispatch = useDispatch()
    const { userInfo, authenticated, isLoading } = useSelector(state => state.loginReducer)

    if (authenticated) {
        switch (userInfo.role) {
            case ROLE_ORDER_TAKER: navigation.navigate(LIST_TABLE_SCREEN, { userInfo })
                break;
            case ROLE_CHEF: navigation.navigate(KITCHEN_SCREEN, { userInfo })
                break;
            default:
                break;
        }
    }

    useEffect(() => {
        async function _handleStart() {
            try {
                const value = await AsyncStorage.getItem('AccessToken');
                if (value !== null) {
                    dispatch(checkToken({
                        token: value
                    }))
                } else {
                    navigation.navigate(LOGIN_SCREEN)
                }
            } catch (error) {
                console.log('Đọc token thất bại', error)
            }
        }
        _handleStart()
    }, [])
    return (
        <View style={styles.container}>
            <Text style={styles.title}> ASM </Text>
            {isLoading && <ActivityIndicator style={{ marginTop: 15 }} size="large" color='white' />}
        </View>
    )

}

const styles = StyleSheet.create({
    container: {
        backgroundColor: MAIN_COLOR,
        flex: 1,
        alignItems: 'center',
        justifyContent: "center",
    },
    title: {
        fontWeight: 'bold',
        fontSize: 18
    },
})

