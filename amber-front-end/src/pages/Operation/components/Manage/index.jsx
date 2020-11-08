import React from 'react';
import { ResponsiveGrid, Icon } from '@alifd/next';
import NodeListTable from './components/NodeListTable'

const { Cell } = ResponsiveGrid;

const Manage = (props) => {
  // console.log('clusterId:',props.clusterId);
  return (
    < ResponsiveGrid gap={20} >
      <Cell colSpan={20}> Manage </Cell>
      <Cell colSpan={2} rowSpan={1}> <Icon type='edit' /> Config </Cell>
      <Cell colSpan={2}> <Icon type='add' /> Import </Cell>
      <Cell colSpan={1}> <Icon type="refresh" /> </Cell>
      <Cell colSpan={20}>
        <NodeListTable clusterId={props.clusterId}/>
      </Cell>
    </ResponsiveGrid >
  )
};

export default Manage;
