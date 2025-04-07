// src/components/BarChart.tsx
import {
	Chart as ChartJS,
	CategoryScale,
	LinearScale,
	BarElement,
	Title,
	Tooltip,
	Legend,
	ChartOptions,
	ChartData,
} from 'chart.js';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { Bar } from 'react-chartjs-2';
import { DEV_AUTH } from '../constants';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const options: ChartOptions<'bar'> = {
	responsive: true,
	maintainAspectRatio: false,
	plugins: {
		legend: {
			position: 'top' as const,
		},
		title: {
			display: true,
			text: 'Placeholder Bar Chart',
		},
	},
};

const labels = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

type monthly = {
	month: number;
	amount: number;
}

const BarChart = () => {
	const [chartData, setChartData] = useState<ChartData<'bar'>>();

	useEffect(() => {
		const grabMonthly = async () => {
			const response = await axios.get<number[]>("/api/monthlySpending", {
				auth: DEV_AUTH,
			});

			const monthlyAmount = Object.entries(response.data).map(([_, value]) => value);

			const data: ChartData<'bar'> = {
				labels,
				datasets: [
					{
						label: 'Spending',
						data: monthlyAmount,
						backgroundColor: 'rgba(53, 162, 235, 0.5)',
					},
				],
			};
			setChartData(data);
		}

		grabMonthly();
	}, [])

	if (!chartData || chartData.labels?.length === 0) {
		return <div>Loading chart data...</div>;
	}

	return <Bar options={options} data={chartData} />;
};

export default BarChart;
