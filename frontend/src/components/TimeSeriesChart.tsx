import { Line } from 'react-chartjs-2';
import type { ChartData, ChartOptions } from 'chart.js';
import { Chart as ChartJS, TimeScale, LinearScale, PointElement, LineElement, Tooltip } from 'chart.js';
import 'chartjs-adapter-date-fns';
import { useEffect, useState } from 'react';
import axios from 'axios';

import { DEV_AUTH } from '../constants';
import { start } from 'repl';
import DateRangeSelector from './DateRangeSelector';

ChartJS.register(TimeScale, LinearScale, PointElement, LineElement, Tooltip);

type Networth = {
    id: number;
    amount: number;
    date: string;
}

const TimeSeriesChart = () => {
    const [NWHistory, setNWHistory] = useState<Networth[]>([]);
    const [chartData, setChartData] = useState<ChartData<'line'>>();
    const [chartOptions, setChartOptions] = useState<ChartOptions<'line'>>();
    const [selectedRange, setSelectedRange] = useState<'All-time' | 'Yearly' | '6 Months' | 'Monthly' | 'Weekly'>('Monthly');



    useEffect(() => {
        const today = new Date();
        let startDate: string;

        const yyyy = today.getFullYear();
        const mm = String(today.getMonth() + 1).padStart(2, '0');
        const dd = String(today.getDate()).padStart(2, '0');
        const endDate = `${yyyy}-${mm}-${dd}`;

        if (selectedRange === 'Yearly') {
            const lastYear = new Date(today);
            lastYear.setFullYear(today.getFullYear() - 4);
            startDate = lastYear.toISOString().slice(0, 10);
        } else if (selectedRange == '6 Months') {
            const last6Months = new Date(today);
            last6Months.setMonth(today.getMonth() - 6);
            startDate = last6Months.toISOString().slice(0, 10);
        } else if (selectedRange === 'Monthly') {
            const lastMonth = new Date(today);
            lastMonth.setMonth(today.getMonth() - 1);
            startDate = lastMonth.toISOString().slice(0, 10);
        } else if (selectedRange === 'Weekly') {
            const lastWeek = new Date(today);
            lastWeek.setDate(today.getDate() - 7);
            startDate = lastWeek.toISOString().slice(0, 10);
        } else if (selectedRange === "All-time") {
            const allTime = new Date(today);
            allTime.setFullYear(today.getFullYear() - 5);
            startDate = allTime.toISOString().slice(0, 10);
        }

        const fetchNetworth = async () => {
            try {
                const response = await axios.get<Networth[]>(`/api/networthHistory?startDate=${startDate}&endDate=${endDate}`, {
                    auth: DEV_AUTH,
                });
                const networthHistory = response.data;

                setNWHistory(networthHistory);

                const amounts = networthHistory.map(nw => nw.amount);
                // find a way to organize these, probably on backend to sort through and optimize or whatever using regex maybe?
                const names = networthHistory.map(nw => nw.date);

                console.log(networthHistory);

                const timeSeriesPoints = networthHistory.map(nw => ({
                    x: new Date(nw.date).getTime(),
                    y: nw.amount,
                }));

                const data = {
                    datasets: [
                        {
                            label: 'Net Worth',
                            data: timeSeriesPoints,
                            borderColor: 'blue',
                            tension: 0.3,
                            fill: true,
                        },
                    ],
                };

                setChartData(data);


                const options: ChartOptions<'line'> = {
                    responsive: true,
                    maintainAspectRatio: false,
                    parsing: false,
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
                            min: new Date(startDate).toISOString(),
                            max: new Date(endDate).toISOString(),

                        },
                        y: {
                            title: {
                                display: true,
                                text: 'Amount',
                            },
                        },
                    },
                };

                setChartOptions(options);
            } catch (err) {
                console.error(err);
            }


        }
        fetchNetworth();
    }, [selectedRange]);

    return (
        <>
            <div className='date-range-selector'>
                <DateRangeSelector selected={selectedRange} onChange={setSelectedRange} />
            </div>
            {chartData && chartOptions ? (
                <Line data={chartData} options={chartOptions} />
            ) : (
                <p>Loading chart...</p>
            )}
        </>
    );
};

export default TimeSeriesChart;
