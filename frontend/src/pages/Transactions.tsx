import PieChart from '../components/PieChart';
import TimeSeriesChart from '../components/TimeSeriesChart';
import './Home.css';

function Transactions() {


    return (
        <div className="home-page">
            <div className="header">
                <h1>Transactions</h1>
            </div>

            <div className="content">
                <div className="spending-total">
                    <TimeSeriesChart />
                </div>

                <div className="smaller-charts">
                    <div className="spending-pie">
                        <PieChart />
                    </div>
                    <div className="spending-pie">
                        <PieChart />
                    </div>
                </div>


                {/*<div className="insights">
                    <p>Maybe add GPT based insights here | or most spend items</p>
                </div>*/}
            </div>
        </div>
    )
}

export default Transactions;