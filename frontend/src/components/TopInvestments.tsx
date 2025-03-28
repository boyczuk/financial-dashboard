import React from 'react';
import {
    Table, TableBody, TableCell, TableContainer,
    TableHead, TableRow, Paper
} from '@mui/material';

interface Stock {
    id: number;
    ticker: string;
    price: number;
    totalValue: number;
}

const rows: Stock[] = [
    { id: 1, ticker: 'AAPL', price: 217.40, totalValue: 4348 },
    { id: 2, ticker: 'AC', price: 14.13, totalValue: 282 },
    { id: 3, ticker: 'AMZN', price: 192.28, totalValue: 3846 },
    { id: 4, ticker: 'GOOG', price: 155.94, totalValue: 3118 },
    { id: 5, ticker: 'KO', price: 70.25, totalValue: 1405 },
    { id: 6, ticker: 'LMT', price: 440.15, totalValue: 8803 },
    { id: 7, ticker: 'NET', price: 114.90, totalValue: 2298 },
    { id: 8, ticker: 'NVDA', price: 109.16, totalValue: 2183 },
    { id: 9, ticker: 'PEP', price: 175.00, totalValue: 3500 },
    { id: 10, ticker: 'VFV', price: 110.50, totalValue: 2210 },
    { id: 11, ticker: 'VOO', price: 420.25, totalValue: 8405 },
];


const SimpleStockTable = () => {

    rows.sort((a: Stock, b: Stock) => b.totalValue - a.totalValue);

    return (
        <TableContainer component={Paper}>
            <Table aria-label="stock table">
                <TableHead>
                    <TableRow>
                        <TableCell><strong>Stock Ticker</strong></TableCell>
                        <TableCell><strong>Price per Share ($) CAD</strong></TableCell>
                        <TableCell><strong>Total Value ($) CAD</strong></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rows.map((row) => (
                        <TableRow key={row.id}>
                            <TableCell>{row.ticker}</TableCell>
                            <TableCell>{row.price}</TableCell>
                            <TableCell>{row.totalValue}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default SimpleStockTable;
