import PieChart from '../components/PieChart';
import TimeSeriesChart from '../components/TimeSeriesChart';
import EnhancedTable from '../components/TopInvestments';
import TransactionTable from '../components/TransactionsTable';
import './styles/Transactions.css';

function Transactions() {


    return (
        <div className="home-page">
            <div className="header">
                <h1>Transactions</h1>
            </div>

            <div className="content">
                <TransactionTable />
            </div>
        </div>
    )
}

export default Transactions;