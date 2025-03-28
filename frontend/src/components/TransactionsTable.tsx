import React from 'react';

import {
    Table, TableBody, TableCell, TableContainer,
    TableHead, TableRow, Paper
} from '@mui/material';

interface Transaction {
    id: number;
    description: string;
    amount: number;
    date: string;
    category: string;
}

const transactions: Transaction[] = [
    { id: 1, description: "Starbucks", amount: -6.45, date: "2025-03-01", category: "Dining" },
    { id: 2, description: "Paycheque Deposit", amount: 2400.00, date: "2025-03-01", category: "Income" },
    { id: 3, description: "Spotify", amount: -10.99, date: "2025-03-02", category: "Subscriptions" },
    { id: 4, description: "Grocery Store", amount: -92.34, date: "2025-03-03", category: "Groceries" },
    { id: 5, description: "Wealthsimple Trade Deposit", amount: -300.00, date: "2025-03-03", category: "Investments" },
    { id: 6, description: "Uber", amount: -18.70, date: "2025-03-04", category: "Transport" },
    { id: 7, description: "Freelance Payout", amount: 600.00, date: "2025-03-04", category: "Income" },
    { id: 8, description: "Netflix", amount: -15.49, date: "2025-03-05", category: "Subscriptions" },
    { id: 9, description: "Electric Bill", amount: -120.22, date: "2025-03-06", category: "Utilities" },
    { id: 10, description: "Restaurant", amount: -55.80, date: "2025-03-06", category: "Dining" },
];


const TransactionTable = () => {

    return (
        <TableContainer component={Paper}>
            <Table aria-label="stock table">
                <TableHead>
                    <TableRow>
                        <TableCell><strong>Desc</strong></TableCell>
                        <TableCell><strong>Amount CAD</strong></TableCell>
                        <TableCell><strong>Date</strong></TableCell>
                        <TableCell><strong>Category</strong></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {transactions.map((transaction) => (
                        <TableRow key={transaction.id}>
                            <TableCell>{transaction.description}</TableCell>
                            <TableCell>{transaction.amount}</TableCell>
                            <TableCell>{transaction.date}</TableCell>
                            <TableCell>{transaction.category}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default TransactionTable;
