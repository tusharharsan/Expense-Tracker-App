import { Alert, StyleSheet, Text, TextInput, View } from 'react-native'
import { SafeAreaView } from 'react-native-safe-area-context'
import React, { useEffect, useState } from 'react'
import CustomBox from '../Components/CustomBox'
import CustomText from '../Components/CustomText'
import { Button } from '@/components/ui/button'
import AsyncStorage from '@react-native-async-storage/async-storage'

const Login = ({navigation}) => {
  const [userName , setUserName] = useState('');
  const [password , setPassword] = useState('');
  const [loggedIn , setloggedIn] = useState(false);

  const gotoSignup = ()=>{
    navigation.navigate('Signup' , {name:'Signup'})
  }

  const isLoggedIn= async()=>{
    const accesstoken = await AsyncStorage.getItem('accessToken');
    const response = await fetch('http://192.168.1.5:9898/auth/v1/ping',{
      method:'GET',
      headers:{
          Accept:'application/json',
          'Content-type':'application/json',
          Authorization:'Bearer '+accesstoken,
          'X-Requested-With':'XMLHttpRequest'
      }
    })
    return response.ok;
  }

  const gotoHomeWithLogin = async()=>{
    const response = await fetch('http://192.168.1.5:9898/auth/v1/login',{
      method:'POST',
      headers:{
        Accept:'application/json',
        'Content-Type':'application/json',
        'X-Requested-With':'XMLHttpRequest',
      },
      body:JSON.stringify({
        username:userName,
        password:password,
      }),
    });

    if(response.ok){
      const data = await response.json();
      await AsyncStorage.setItem('refreshToken' , data['token']);
      await AsyncStorage.setItem('accessToken' , data['accessToken']);
      navigation.navigate('Home',{name:'Home'});
    }
  }

  const refreshToken = async() =>{
    const refreshtoken = await AsyncStorage.getItem('refreshToken');
    const response = await fetch('http://192.168.1.5:9898/auth/v1/refreshToken',{
      method:'POST',
      headers:{
          Accept:'application/json',
          'Content-type':'application/json',
          'X-Requested-With':'XMLHttpRequest'
      },
      body:JSON.stringify({
        token:refreshtoken,
      }),
    });
    if(response.ok){
      const data = await response.json();
      await AsyncStorage.setItem('accessToken' , data['accessToken']);
      await AsyncStorage.setItem('refreshToken' , data['token']);
      const refreshToken = await AsyncStorage.getItem('refreshToken');
      const accessToken = await AsyncStorage.getItem('accessToken');
      console.log('Tokens after refresh are '+ refreshToken +' '+accessToken,)
      
    }
    return response.ok;
  }

  useEffect(()=>{
    const handleLogin = async()=>{
      const loggedIn = await isLoggedIn();
      setloggedIn(loggedIn);
      if(loggedIn){
        navigation.navigate('Home' , {name:'Home'})
      }else{
        const refreshtoken = await refreshToken();
        setloggedIn(refreshtoken);
        if(refreshtoken){
          navigation.navigate('Home',{name:'Home'});
        }
      }
    };
    handleLogin();
  },[])


  return (
    
      <View style={styles.loginContainer}>
        <CustomBox style={loginBox}>
          <CustomText style={styles.heading}>Login</CustomText>
          <TextInput
            placeholder='User name'
            value={userName}
            onChangeText={text=>setUserName(text)}
            style={styles.textInput}
            placeholderTextColor='#888'
          />
          <TextInput
            placeholder='Password'
            value={password}
            onChangeText={text=>setPassword(text)}
            style={styles.textInput}
            placeholderTextColor='#888'
          />
        </CustomBox>
        <Button onPressIn={() => gotoHomeWithLogin()} style={styles.button} >
          <CustomBox style={buttonBox}>
            <CustomText style={{textAlign:'center'}}>
              Submit
            </CustomText>
          </CustomBox>
        </Button>

        <Button onPressIn={() => gotoSignup()} style={styles.button} >
          <CustomBox style={buttonBox}>
            <CustomText style={{textAlign:'center'}}>
              Goto Signup
            </CustomText>
          </CustomBox>
        </Button>
      </View>
    
  )
}

export default Login

const loginBox = {
  mainBox:{
    backgroundColor:'#fff',
    borderColor:'black',
    borderWidth:1,
    borderRadius:10,
    padding:20,
  },

  shadowBox:{
    backgroundColor:'gray',
    borderRadius:10,
  },
};

const styles = StyleSheet.create({
      heading:{
        fontSize:24,
        fontWeight:'bold',
        marginBottom:20,
      },
      textInput:{
        backgroundColor:'#f0f0f0',
        borderRadius:5,
        padding:10,
        marginBottom:10,
        width:'100%',
        color:'black',
      },
      loginContainer:{
        flex:1,
        justifyContent:'center',
        alignItems:'center',
        padding:20,
      },
      button: {
        marginTop: 20,
        width: '30%',
      },
      
})

const buttonBox={
  mainBox:{
    backgroundColor:'#fff',
    borderColor:'black',
    borderWidth:1,
    borderRadius:10,
    padding:10,
  },

  shadowBox:{
    backgroundColor:'gray',
    borderRadius:10,
  }
}