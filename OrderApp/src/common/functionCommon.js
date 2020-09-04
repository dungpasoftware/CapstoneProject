import Toast from 'react-native-root-toast';


// Add a Toast on screen.
export function showToast(message) {
    Toast.show(message, {
        duration: Toast.durations.SHORT,
        position: Toast.positions.BOTTOM,
        shadow: true,
        animation: true,
        hideOnPress: true,
        delay: 0,
    });
}

