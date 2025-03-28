import PieChart from '../components/PieChart';
import EnhancedTable from '../components/TableTemp';
import TimeSeriesChart from '../components/TimeSeriesChart';
import './styles/Investments.css';

function Investments() {


    return (
        <div className="home-page">
            <div className="header">
                <h1>Investments</h1>
            </div>

            <div className="content">
                <div className="spending-total">
                    <TimeSeriesChart />
                </div>

                <div className='top-performers'>
                    <EnhancedTable />
                </div>


                {/*<div className="insights">
                    <p>Maybe add GPT based insights here | or most spend items</p>
                </div>*/}
            </div>
        </div>
    )
}

export default Investments;