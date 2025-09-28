import { StyleSheet, Text, TextInput, View } from 'react-native'
import React, { useState } from 'react'
import CustomBox from '../Components/CustomBox'
import CustomText from '../Components/CustomText'
import { Button } from '@/components/ui/button'
import AsyncStorage from '@react-native-async-storage/async-storage'

const SignUp = ({navigation}) => {
    const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [userName, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');


  const navigateToLoginScreen = async () => {
    try {
      const requestBody = {
        'username': userName,
        'lastname': lastName,
        'email': email,
        'phone_number': phoneNumber,
        'password': password,
      };
      
      // Log what you're sending
      console.log('Sending request body:', JSON.stringify(requestBody));
      
      const response = await fetch('http://192.168.1.5:9898/auth/v1/signup', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'X-requested-With': 'XMLHttpRequest'
        },
        body: JSON.stringify(requestBody),
      });

      console.log('Response status:', response.status);
      
      if (!response.ok) {
        const errorText = await response.text();
        console.log('Error response:', errorText);
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      console.log('Response data:', data);
      
      await AsyncStorage.setItem('accessToken', data["acessToken"]);
      await AsyncStorage.setItem('refreshToken', data["token"]);
      
      navigation.navigate('Home', { name: 'Home' });
    } catch (error) {
      console.error("Error during SignUp: " + error);
    }
  }

  const gotLoginWithoutValidation = ()=>{
    navigation.navigate('Login' , {name:'Login'})
  }
  return (
    <View style={styles.signupContainer}>
        <CustomBox style={signUpBox}>
          <CustomText style={styles.heading}>Sign Up</CustomText>
          <TextInput
            placeholder="First Name"
            value={firstName}
            onChangeText={text => setFirstName(text)}
            style={styles.textInput}
            placeholderTextColor="#888"
          />
          <TextInput
            placeholder="Last Name"
            value={lastName}
            onChangeText={text => setLastName(text)}
            style={styles.textInput}
            placeholderTextColor="#888"
          />
                <TextInput
            placeholder="User Name"
            value={userName}
            onChangeText={text => setUserName(text)}
            style={styles.textInput}
            placeholderTextColor="#888"
          />
          <TextInput
            placeholder="Email"
            value={email}
            onChangeText={text => setEmail(text)}
            style={styles.textInput}
            placeholderTextColor="#888"
            keyboardType="email-address"
          />
          <TextInput
            placeholder="Password"
            value={password}
            onChangeText={text => setPassword(text)}
            style={styles.textInput}
            placeholderTextColor="#888"
            secureTextEntry
          />
          <TextInput
            placeholder="Phone Number"
            value={phoneNumber}
            onChangeText={text => setPhoneNumber(text)}
            style={styles.textInput}
            placeholderTextColor="#888"
            keyboardType="phone-pad"
          />
        </CustomBox>
        <Button onPressIn={() => navigateToLoginScreen()} style={styles.button}>
            <CustomBox style={buttonBox}>
                <CustomText style={{textAlign: 'center'}}>Sign Up</CustomText>
            </CustomBox>
          </Button>
          <Button onPressIn={() => gotLoginWithoutValidation()} style={styles.button}>
            <CustomBox style={buttonBox}>
                <CustomText style={{textAlign: 'center'}}>Login</CustomText>
            </CustomBox>
          </Button>
    </View>
  )
}

export default SignUp

const styles = StyleSheet.create({
    signupContainer: {
      flex: 1,
      justifyContent: 'center',
      alignItems: 'center',
      padding: 20,
    },
    button: {
      marginTop: 20,
      width: "30%",
    },
    heading: {
      fontSize: 24,
      fontWeight: 'bold',
      marginBottom: 20,
    },
    textInput: {
      backgroundColor: '#f0f0f0',
      borderRadius: 5,
      padding: 10,
      marginBottom: 10,
      width: '100%',
      color: 'black',
    },
  });
  
  const signUpBox = {
    mainBox: {
      backgroundColor: '#fff',
      borderColor: 'black',
      borderWidth: 1,
      borderRadius: 10,
      padding: 20,
    },
    shadowBox: {
      backgroundColor: 'gray',
      borderRadius: 10,
    },
  };
  
  const buttonBox = {
    mainBox: {
      backgroundColor: '#fff',
      borderColor: 'black',
      borderWidth: 1,
      borderRadius: 10,
      padding: 10,
      
    },
    shadowBox: {
      backgroundColor: 'gray',
      borderRadius: 10,
    },
  };