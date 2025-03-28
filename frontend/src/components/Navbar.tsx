import DashboardIcon from '@mui/icons-material/Dashboard';
import ShowChartIcon from '@mui/icons-material/ShowChart';
import AccountBalanceIcon from '@mui/icons-material/AccountBalance';
import FlagIcon from '@mui/icons-material/Flag';
import { useState } from 'react';
import { useNavigate } from "react-router-dom";
import './styles/Navbar.css';

const Navbar = () => {
    const [highlight, setHighlight] = useState<number>(0);
    const navigate = useNavigate();

    return (
        <div className="navbar">
            <ul>
                <li>
                    <DashboardIcon className={highlight === 0 ? 'navbar-icon-highlight' : 'navbar-icon'} onClick={() => { navigate("/"); setHighlight(0) }} />
                </li>
                <li>
                    <ShowChartIcon className={highlight === 1 ? 'navbar-icon-highlight' : 'navbar-icon'} onClick={() => {navigate("/investments"); setHighlight(1)}} />
                </li>
                <li>
                    <AccountBalanceIcon className={highlight === 2 ? 'navbar-icon-highlight' : 'navbar-icon'} onClick={() => {navigate("/savings"); setHighlight(2) }} />
                </li>
                <li>
                    <FlagIcon className={highlight === 3 ? 'navbar-icon-highlight' : 'navbar-icon'} onClick={() => {navigate("/transactions"); setHighlight(3) }} />
                </li>
            </ul>
        </div>
    )
}

export default Navbar;