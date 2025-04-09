import BarChart from '../components/BarChart';
import PieChart from '../components/PieChart';
import TimeSeriesChart from '../components/TimeSeriesChart';
import UploadFile from '../components/UploadFile';
import { useState } from 'react';
import './styles/Home.css';

function Home() {
    const [selectedAccount, setSelectedAccount] = useState('chequing');

    const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedAccount(e.target.value);
    };

    return (
        <div className="home-page">
            <div className="header">
                <h1>Upload</h1>
            </div>

            <div className="content">
                <label htmlFor="account">Select Account:</label>
                <select id="account" value={selectedAccount} onChange={handleChange}>
                    <option value="chequing">Chequing</option>
                    <option value="savings">Savings </option>
                </select>
                <UploadFile accountName={selectedAccount} />

                {/*<div className="insights">
                    <p>Maybe add GPT based insights here | or most spend items</p>
                </div>*/}
            </div>
        </div>
    )
}

export default Home;