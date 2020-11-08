import React, { SFC } from 'react';
import { Card } from '@alifd/next';
import styles from './index.module.scss';


const Exception = props => {
  const { statusCode, description, image } = props;

  return (
    <Card free className={styles.exception}>
      <div>
        <img src={image} className={styles.exceptionImage} alt="img" />
        <h1 className={styles.statuscode}>{statusCode}</h1>
        <div className={styles.description}>{description}</div>
      </div>
    </Card>
  );
};

export default Exception;