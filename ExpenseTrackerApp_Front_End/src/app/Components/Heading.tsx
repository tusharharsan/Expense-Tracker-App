import {View, StyleSheet} from 'react-native';
import React from 'react';
import CustomBox from './CustomBox';
import CustomText from './CustomText';

const Heading = () => {
  return (
    <View style={styles.container}>
      <CustomBox style={styles.headingBox}>
        <CustomText style={styles.headingText}>Your Recent Spends</CustomText>
      </CustomBox>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    marginVertical: 16,
  },
  headingBox: {
    backgroundColor: '#f5f5f5',
    borderRadius: 12,
    padding: 16,
  },
  headingText: {
    color: '#FFFF',
    fontSize: 24,
    fontFamily: 'System',
    fontWeight: 'bold',
  },
});

export default Heading;