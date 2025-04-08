import BarChart from '../components/BarChart';
import PieChart from '../components/PieChart';
import TimeSeriesChart from '../components/TimeSeriesChart';
import UploadFile from '../components/UploadFile';
import './styles/Home.css';

function Home() {


    return (
        <div className="home-page">
            <div className="header">
                <h1>Upload</h1>
            </div>

            <div className="content">
                <UploadFile />


                {/*<div className="insights">
                    <p>Maybe add GPT based insights here | or most spend items</p>
                </div>*/}
            </div>
        </div>
    )
}

export default Home;