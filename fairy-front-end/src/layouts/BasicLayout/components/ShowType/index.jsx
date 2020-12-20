import React from 'react';
import { Switch } from '@alifd/next';
import styles from './index.module.scss';

const ShowType = () => {
  // const onChange = (checked) => {
  //   console.log(`switch to ${checked}`);
  // }
  return (
    <Switch className={styles.largeWidth} size="small" checkedChildren="List" unCheckedChildren="Card" />
  )
}

export default ShowType;
