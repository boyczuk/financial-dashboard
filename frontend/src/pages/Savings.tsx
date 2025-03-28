import PieChart from '../components/PieChart';
import TimeSeriesChart from '../components/TimeSeriesChart';
import './styles/Savings.css';

interface Goal {
    id: number;
    name: string;
    desc: string;
    currentAmt: number;
    maxAmt: number;
}

const goals: Goal[] = [
    {
        id: 1,
        name: "Emergency Fund",
        desc: "Save up for 6 months of living expenses in case of job loss or emergency.",
        currentAmt: 3500,
        maxAmt: 10000,
    },
    {
        id: 2,
        name: "Vacation to Japan",
        desc: "Trip planned for 2026 including flights, hotels, and spending money.",
        currentAmt: 1200,
        maxAmt: 5000,
    },
    {
        id: 3,
        name: "First Home Down Payment",
        desc: "Saving 20% down for a future condo or house.",
        currentAmt: 18000,
        maxAmt: 60000,
    },
    {
        id: 4,
        name: "New Laptop",
        desc: "Upgrade to a high-end development machine.",
        currentAmt: 400,
        maxAmt: 2000,
    },
    {
        id: 5,
        name: "Investing Portfolio",
        desc: "Long-term investment account for retirement or wealth building.",
        currentAmt: 15000,
        maxAmt: 100000,
    },
    {
        id: 6,
        name: "Wedding",
        desc: "Save for future wedding expenses including venue and catering.",
        currentAmt: 2500,
        maxAmt: 20000,
    }
];


function Savings() {


    return (
        <div className="home-page">
            <div className="header">
                <h1>Savings</h1>
            </div>

            <div className="content">
                <div className='goals'>
                    {goals.map((goal, index) => (
                        <div className='goal'>
                            <p>{goal.name}</p>
                            <p>{goal.currentAmt} / {goal.maxAmt}</p>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    )
}

export default Savings;