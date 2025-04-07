import { useEffect, useState } from 'react';
import axios from 'axios';

import {
    Table, TableBody, TableCell, TableContainer,
    TableHead, TableRow, Paper
} from '@mui/material';

import { DEV_AUTH } from '../constants';

type Transaction = {
    id: number;
    name: string;
    date: string;
    amount: number;
}

const TransactionTable = () => {
    const [transactions, setTransactions] = useState<Transaction[]>([]);

    const startDate: string = "2023-01-01";
    const endDate: string = "2025-03-31";

    useEffect(() => {
        const fetchTransactions = async () => {
            try {
                const response = await axios.get<Transaction[]>(`/api/transactions?startDate=${startDate}&endDate=${endDate}`, {
                    auth: DEV_AUTH,
                });
                const transactions = response.data;

                setTransactions(transactions);

                const amounts = transactions.map(tx => tx.amount);
                // find a way to organize these, probably on backend to sort through and optimize or whatever using regex maybe?
                const names = transactions.map(tx => tx.name);

                console.log(transactions);


            } catch (err) {
                console.error(err);
            }


        }

        fetchTransactions();
    }, []);

    return (
        <TableContainer component={Paper} className='table-whole'>
            <Table stickyHeader aria-label="stock table">
                <TableHead>
                    <TableRow>
                        <TableCell><strong>Desc</strong></TableCell>
                        <TableCell><strong>Amount CAD</strong></TableCell>
                        <TableCell><strong>Date</strong></TableCell>
                        <TableCell><strong>Category</strong></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody className='table-body'>
                    {transactions.map((transaction) => (
                        <TableRow key={transaction.id}>
                            <TableCell>{transaction.name}</TableCell>
                            <TableCell>${transaction.amount}</TableCell>
                            <TableCell>{transaction.date}</TableCell>
                            <TableCell>temp category</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default TransactionTable;
