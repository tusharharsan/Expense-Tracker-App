import { StyleSheet, Text, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import AsyncStorage from '@react-native-async-storage/async-storage'
import { ExpenseDto } from './dto/ExpenseDto';
import Heading from '../Components/Heading';
import CustomBox from '../Components/CustomBox';
import CustomText from '../Components/CustomText';
import Expense from '../Components/Expense';

const Spends = () => {
    const [expense, setExpense] = useState<ExpenseDto[]>([]);
    const [isLoading, setisLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetchExpenses();
    }, [])
    const fetchExpenses = async () => {
        try {
            const accessToken = await AsyncStorage.getItem('accessToken');
            if (!accessToken) {
                throw new Error("No acess token found");
                
            }

            const response = await fetch('http://192.168.1.5:9820/expense/v1/getExpense?user_id=1bb11c86-dc61-4940-a332-4d93febb6982', {
                method: 'GET',
                headers: {
                    'X-Requested-With': 'XMLHttpRequest',
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${accessToken}`,
                }
            });

            if (!response.ok) {
                throw new Error(`Failed to fetch expenses. Status:${response.status}`);
            }

            const data = await response.json();
            console.log('expese fetch:', data);

            const transformedExpense: ExpenseDto[] = data.map((expense: any, index: number) => ({
                key: index + 1,
                amount: Number(expense["amount"]) || 0,
                merchant: expense["merchant"] || 'Unknown Merchant',
                currency: expense["currency"] || 'USD',
                createdAt: new Date(expense["created_at"]),
            }));

            console.log("Transformed expenses:", transformedExpense);

            setExpense(transformedExpense);
            setisLoading(false);
            setError(null);
        } catch (err) {
            setError(err instanceof Error ? err.message : 'An unknown error ocured');
            console.error('Error fetching expenses:', err);
            setisLoading(false);
        }
    }
    if (isLoading) {
        return (
            <View>
                <Heading props={{ heading: 'spends' }} />
                <CustomBox style={headingBox}>
                    <CustomText style={{}}>Loading expenses...</CustomText>
                </CustomBox>
            </View>
        )
    }

    if(error){
        return (
            <View>
              <Heading props={{ heading: 'spends' }} />
              <CustomBox style={headingBox}>
                <CustomText style={{}}>Error: {error}</CustomText>
              </CustomBox>
            </View>
          );
    }

    return(
        <View>
      <Heading
        props={{
          heading: 'spends',
        }}
      />
      <CustomBox style={headingBox}>
        <View style={styles.expenses}>
          {expense.map(expense => (
            <Expense key={expense.key} props={expense} />
          ))}
        </View>
      </CustomBox>
    </View>
    )
}

export default Spends

const styles = StyleSheet.create({
    expenses: {
        marginTop: 20,
    },
});

const headingBox = {
    mainBox: {
        backgroundColor: 'white',
        borderColor: 'black',
    },
    shadowBox: {
        backgroundColor: 'gray',
    },
    styles: {
        marginTop: 20,
    },
};