import { useEffect, useState } from 'react';
import axios from 'axios';
import { Pie } from 'react-chartjs-2';
import {
	Chart as ChartJS,
	ArcElement,
	Tooltip,
	Legend,
	ChartOptions,
	ChartData,
} from 'chart.js';

import { DEV_AUTH } from '../constants';

// Register necessary chart elements
ChartJS.register(ArcElement, Tooltip, Legend);

type Transaction = {
	id: number;
	name: string;
	date: string;
	amount: number;
}

const PieChart = () => {
	const [transactions, setTransactions] = useState<Transaction[]>([]);
	const [chartData, setChartData] = useState<ChartData<'pie'>>();
	const [chartOptions, setChartOptions] = useState<ChartOptions<'pie'>>();

	const startDate: string = "2025-01-01";
	const endDate: string = "2025-03-31";



	useEffect(() => {
		const fetchTransactions = async () => {
			try {
				const response = await axios.get<Transaction[]>(`/api/transactions?startDate=${startDate}&endDate=${endDate}`, {
					auth: DEV_AUTH,
				});
				const transactions = response.data;

				setTransactions(transactions);

				const amounts = transactions.map(tx => tx.amount);
				// find a way to organize these, probably on backend to sort through and optimize or whatever using regex maybe?
				const names = transactions.map(tx => tx.name);

				console.log(transactions);

				setChartData({
					labels: names,
					datasets: [
						{
							label: 'Spending by Category',
							data: amounts,
							backgroundColor: ['#36A2EB', '#FF6384', '#FFCE56', '#4BC0C0'],
							borderWidth: 1,
						},
					],
				});

				setChartOptions({
					responsive: true,
					maintainAspectRatio: false,
					plugins: {
						legend: {
							position: 'bottom',
						},
						tooltip: {
							callbacks: {
								label: (tooltipItem) => {
									const value = tooltipItem.raw;
									return `$${value}`;
								},
							},
						},
					},
				});
			} catch (err) {
				console.error(err);
			}


		}
		
		fetchTransactions();
	}, []);


	if (!chartData || !chartOptions || chartData.labels?.length === 0) {
		return <div>Loading chart data...</div>;
	}


	return <Pie data={chartData} options={chartOptions} />;
};

export default PieChart;
