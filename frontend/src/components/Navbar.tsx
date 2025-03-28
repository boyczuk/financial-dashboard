import DashboardIcon from '@mui/icons-material/Dashboard';
import ShowChartIcon from '@mui/icons-material/ShowChart';
import AccountBalanceIcon from '@mui/icons-material/AccountBalance';
import FlagIcon from '@mui/icons-material/Flag';

import { useNavigate } from "react-router-dom";
import './styles/Navbar.css';

const Navbar = () => {
    const navigate = useNavigate();

    return (
        <div className="navbar">
            <ul>
                <li>
                    <DashboardIcon className='navbar-icon' onClick={() => navigate("/")} />
                </li>
                <li>
                    <ShowChartIcon className='navbar-icon' onClick={() => navigate("/investments")} />
                </li>
                <li>
                    <AccountBalanceIcon className='navbar-icon' onClick={() => navigate("/savings")} />
                </li>
                <li>
                    <FlagIcon className='navbar-icon' onClick={() => navigate("/transactions")} />
                </li>
            </ul>
        </div>
    )
}

export default Navbar;