import { NewAppScreen } from '@react-native/new-app-screen';

import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { SafeAreaView, StatusBar, StyleSheet, useColorScheme, View } from 'react-native';
import { Text } from 'react-native';
import Login from './src/app/pages/Login';
import SignUp from './src/app/pages/SignUp';
import Home from './src/app/pages/Home';


function App(): React.JSX.Element {
  const Stack = createNativeStackNavigator();

  return (
    <NavigationContainer>
        <Stack.Navigator>
          <Stack.Screen name='Login' component={Login} />
          <Stack.Screen name='Signup' component={SignUp} />
          <Stack.Screen name='Home' component={Home}/>
        </Stack.Navigator>
      </NavigationContainer>
  );
}
export default App;
