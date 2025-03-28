import BarChart from '../components/BarChart';
import PieChart from '../components/PieChart';
import TimeSeriesChart from '../components/TimeSeriesChart';
import './styles/Home.css';

function Home() {


    return (
        <div className="home-page">
            <div className="header">
                <h1>Dashboard</h1>
            </div>

            <div className="content">
                <div className="spending-total">
                    <TimeSeriesChart />
                </div>

                <div className="smaller-charts">
                    <div className="spending-pie">
                        <PieChart />
                    </div>
                    <div className="bar-wrapper">
                        <BarChart />
                    </div>
                </div>


                {/*<div className="insights">
                    <p>Maybe add GPT based insights here | or most spend items</p>
                </div>*/}
            </div>
        </div>
    )
}

export default Home;