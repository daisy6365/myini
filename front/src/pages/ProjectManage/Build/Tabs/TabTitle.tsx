/* eslint-disable react/button-has-type */
/* eslint-disable react/function-component-definition */
import './style.scss';

import React, { useCallback } from 'react';

type Props = {
  title: string;
  index: number;
  setSelectedTab: (index: number) => void;
  selectedTab: number;
};

const TabTitle: React.FC<Props> = ({
  title,
  setSelectedTab,
  index,
  selectedTab,
}) => {
  const onClick = useCallback(() => {
    setSelectedTab(index);
  }, [setSelectedTab, index]);

  return (
    <div className="tab-title">
      <li className={`li ${selectedTab === index ? 'active' : ''}`}>
        <button className="title-name" onClick={onClick}>
          {title}
        </button>
      </li>
    </div>
  );
};

export default TabTitle;