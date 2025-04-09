import PieChart from '../components/PieChart';
import TimeSeriesChart from '../components/TimeSeriesChart';
import EnhancedTable from '../components/TopInvestments';
import TransactionTable from '../components/TransactionsTable';
import { useState } from 'react';
import './styles/Transactions.css';

function Transactions() {
    const [selectedAccount, setSelectedAccount] = useState('chequing');

    const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedAccount(e.target.value);
    };


    return (
        <div className="home-page">
            <div className="header">
                <h1>Transactions</h1>
            </div>

            <div className="content">
                <label htmlFor="account">Select Account:</label>
                <select id="account" value={selectedAccount} onChange={handleChange}>
                    <option value="chequing">Chequing</option>
                    <option value="savings">Savings </option>
                </select>
                <TransactionTable accountType={selectedAccount} />
            </div>
        </div>
    )
}

export default Transactions;