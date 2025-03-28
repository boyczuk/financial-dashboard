import { Line } from 'react-chartjs-2';
import type { ChartOptions } from 'chart.js';
import { Chart as ChartJS, TimeScale, LinearScale, PointElement, LineElement, Tooltip } from 'chart.js';
import 'chartjs-adapter-date-fns';

ChartJS.register(TimeScale, LinearScale, PointElement, LineElement, Tooltip);

const TimeSeriesChart = () => {
    const data = {
        datasets: [
            {
                label: 'Purchase History',
                data: [
                    { x: new Date('2021-01-01'), y: 100 },
                    { x: new Date('2021-06-01'), y: 200 },
                    { x: new Date('2022-01-01'), y: 150 },
                    { x: new Date('2022-04-01'), y: 160 },
                    { x: new Date('2023-01-01'), y: 350 },
                ],
                borderColor: 'blue',
                tension: 0.3,
                fill: false,
            },
        ],
    };


    const options: ChartOptions<'line'> = {
        responsive: true,
        parsing: false, // You want to handle x/y object data
        scales: {
            x: {
                type: 'time',
                time: {
                    unit: 'month',
                },
                title: {
                    display: true,
                    text: 'Date',
                },
                min: new Date('2021-01-01').toISOString(), 
                max: new Date('2022-06-01').toISOString(),

            },
            y: {
                title: {
                    display: true,
                    text: 'Amount',
                },
            },
        },
    };


    return <Line data={data} options={options} />;
};

export default TimeSeriesChart;
