import React from 'react';
import { Card } from '@alifd/next';
import styles from './index.module.scss';
const StatisticsCard = (props) => {
  const statistics = props.statistics
  return (
    <Card free className={styles.areaChart}>
      {/* {statistics.title ? (
        <>
          <Card.Header title={statistics.title} />
          <Card.Divider />
        </>
      ) : null} */}
      <Card.Content className={styles.content}>
        <div><div className={styles.subTitle}>{statistics.title}</div>
          <div className={styles.value}>{statistics.number}</div>
          <div className={styles.des}>Increasement<span>{statistics.increasement}↑</span>
          </div></div>
        {statistics.icon}
      </Card.Content>

    </Card>
  );
};

export default StatisticsCard;
