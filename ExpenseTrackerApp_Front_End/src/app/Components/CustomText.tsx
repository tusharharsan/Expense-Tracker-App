import { StyleSheet, Text, View } from 'react-native'
import React from 'react'

const CustomText = ({style , children , ...props}) => {
  return (
    <Text style ={[styles.text , style]} {...props}>
        {children}
    </Text>
    
  )
}



const styles = StyleSheet.create({
    text:{
        color:'black',
        fontFamily:'Helvetica',
    }
});

export default CustomText