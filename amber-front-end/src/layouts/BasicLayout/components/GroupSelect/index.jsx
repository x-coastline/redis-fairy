import React, { useState } from 'react';
import { Select } from '@alifd/next';

import styles from './index.module.scss';

export default function GroupSelect() {
  const [groupList] = useState([
    {
      label: 'One',
      value: '1',
    },
    {
      label: 'Two',
      value: '2',
    },
    {
      label: 'Three',
      value: '3',
    },
    {
      label: 'Four',
      value: '4',
    },
  ]);

  function onChange(value) {
    console.log(value);
  };
  function onBlur(e) {
    console.log(/onblur/, e);
  };

  function onToggleHighlightItem(item, type) {
    console.log(item, type);
  };

  return (
    <Select onChange={onChange} onBlur={onBlur} onToggleHighlightItem={onToggleHighlightItem} showSearch size="medium" className={styles.selectGroup} dataSource={groupList} />
  );
}
