import { useState } from 'react';
import './styles/DateRangeSelector.css';

type Props = {
  selected: string;
  onChange: (value: 'All-time' | 'Yearly' | '6 Months' | 'Monthly' | 'Weekly') => void;
};

const DateRangeSelector = ({ selected, onChange }: Props) => {
  const [isOpen, setIsOpen] = useState(false);

  const handleSelect = (range: 'All-time' | 'Yearly' | '6 Months' | 'Monthly' | 'Weekly') => {
    onChange(range);
    setIsOpen(false);
  };

  return (
    <div className="date-range-selector">
      <button className="dropdown-button" onClick={() => setIsOpen(prev => !prev)}>
        {selected} ▼
      </button>
      {isOpen && (
        <ul className="dropdown-menu">
          {['All-time', 'Yearly', '6 Months', 'Monthly', 'Weekly'].map((range) => (
            <li key={range}>
              <button
                className={`dropdown-item ${selected === range ? 'active' : ''}`}
                onClick={() => handleSelect(range as 'All-time' | 'Yearly' | '6 Months' | 'Monthly' | 'Weekly')}
              >
                {range}
              </button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default DateRangeSelector;
