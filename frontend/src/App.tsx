import React from 'react';
import logo from './logo.svg';
import './App.css';
import Home from './pages/Home';
import Navbar from './components/Navbar';
import { BrowserRouter as Router, Routes, Route, useNavigate, Navigate } from "react-router"
import Investments from './pages/Investments';
import Savings from './pages/Savings';
import Transactions from './pages/Transactions';

function App() {
	return (
		<div className="App">
			<Router>
				<Navbar />
				<Routes>
					<Route path="/" element={<Home />} />
					<Route path="investments" element={<Investments />} />
					<Route path="savings" element={<Savings />} />
					<Route path="transactions" element={<Transactions />} />
				</Routes>
			</Router>

		</div>
	);
}

export default App;
