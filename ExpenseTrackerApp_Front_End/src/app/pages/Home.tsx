import { SafeAreaView, StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { GluestackUIProvider } from '@/components/ui/gluestack-ui-provider'
import ExpenseTrackerGraph from './ExpenseTrackerGraph'
import SpendsInsights from './SpendsInsights'
import Spends from './Spends'
import Nav from './Nav'

const Home = () => {
  return (
    
      <SafeAreaView style={styles.safeArea}>
        <View style={styles.container}>
          <Nav/>
          <View style={styles.contentContainer}>
            <View style={styles.graphContainer}>
              <ExpenseTrackerGraph />
            </View>
            <View style={styles.insightsContainer}>
              <SpendsInsights />
            </View>
          </View>
          <View style={styles.spendsContainer}>
            <Spends />
          </View>
        </View>
      </SafeAreaView>
    
  )
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: 'white',
  },
  container: {
    flex: 1,
    paddingHorizontal: 16,
    flexDirection: 'column',
  },
  contentContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginTop: 20,
  },
  graphContainer: {
    flex: 1,
    marginRight: 10,
  },
  insightsContainer: {
    flex: 1,
    marginLeft: 10,
  },
  spendsContainer: {
    marginTop: 20,
  },
  text: {
    color: 'black',
    fontFamily: 'Roboto',
  },
});


export default Home

