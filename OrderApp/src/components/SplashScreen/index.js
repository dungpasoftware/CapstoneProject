import React, { useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { Text, StyleSheet, View, ActivityIndicator, Alert, Image, AsyncStorage } from 'react-native'
import { MAIN_COLOR } from '../../common/color';
import { checkToken } from '../../actions/loginAction';
import { LOGIN_SCREEN, LIST_TABLE_SCREEN, KITCHEN_SCREEN } from '../../common/screenName';
import { ROLE_ORDER_TAKER, ROLE_CHEF } from '../../common/roleType';
import { loadDish } from '../../actions/listDish';




export default function SplashScreen({ navigation }) {
    const dispatch = useDispatch()
    const { userInfo, authenticated, isLoading, errorMessageToken } = useSelector(state => state.loginReducer)

    if (authenticated) {
        switch (userInfo.role) {
            case ROLE_ORDER_TAKER: {
                dispatch(loadDish({ accessToken: userInfo.accessToken }))
                navigation.navigate(LIST_TABLE_SCREEN, { userInfo })
            }
                break;
            case ROLE_CHEF: navigation.navigate(KITCHEN_SCREEN, { userInfo })
                break;
            default:
                break;
        }
    } else {
        if (errorMessageToken != '') {
            Alert.alert(
                'Lỗi',
                errorMessageToken,
                [
                    {
                        text: 'Thử lại',
                        onPress: async () => {
                            let value = ''
                            try {
                                value = await AsyncStorage.getItem('AccessToken');
                            } catch (error) {
                                console.log('Đọc token thất bại', error)
                            }
                            if (value !== null) {
                                dispatch(checkToken({
                                    token: value
                                }))
                            } else {
                                navigation.navigate(LOGIN_SCREEN)
                            }
                        },
                        style: 'cancel'
                    },
                    {
                        text: "Đăng nhập lại",
                        onPress: () => {
                            AsyncStorage.removeItem('AccessToken', () => {
                                navigation.navigate(LOGIN_SCREEN)
                            })
                            return;
                        }
                    }

                ],
                { cancelable: false }
            );
        }
    }

    useEffect(() => {
        async function _handleStart() {
            let value = ''
            try {
                value = await AsyncStorage.getItem('AccessToken');
            } catch (error) {
                console.log('Đọc token thất bại', error)
            }
            if (value !== null) {
                dispatch(checkToken({
                    token: value
                }))

            } else {
                navigation.navigate(LOGIN_SCREEN)
            }
        }
        _handleStart()
    }, [])
    return (
        <View style={styles.container}>
            <View>
                <Image
                    style={{
                        width: 200,
                        height: 200,
                        borderRadius: 100
                    }}
                    source={require('../../assets/logo.jpeg')} />
                <Text style={{
                    color: 'white',
                    fontSize: 30,
                    fontWeight: 'bold',
                    textAlign: 'center',
                    marginTop: 10
                }}>ORDER APP</Text>
            </View>
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

})

