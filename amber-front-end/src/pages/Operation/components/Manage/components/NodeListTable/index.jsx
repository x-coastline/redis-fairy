import React, { useState, useCallback, useEffect } from 'react';
import { Card, Table, Field, Button, MenuButton, Message, Dialog, Tag, Icon } from '@alifd/next';
import { useFusionTable, useSetState } from 'ahooks';

import { node } from 'prop-types';
import manageService from '@/pages/Operation/services/manage';

import styles from './index.module.scss';
import MetaDialog from './MetaDialog';
import MoveSlotDialog from './MoveSlotDialog';
import ReplicaOfDialog from './ReplicaOfDialog';
import NodeOperationDialog from './NodeOperationDialog';

const NodeListTable = (props) =>{

  const [state, setState] = useSetState({
    clusterId: props.clusterId,
    isLoading: false,
    redisNodes: [],
    record: null,
    metaType: '',
    metaVisible: false,
    moveSlotVisible: false,
    replicaOfVisible: false,
    nodeOperatrionVisible: false,
    nodeOperationType: '',
  });

  const { clusterId,isLoading, redisNodes, metaVisible, metaType, record, moveSlotVisible, replicaOfVisible, nodeOperatrionVisible, nodeOperationType } = state;

  const fetchClusterList = async (id) => {
    const res = await manageService.getRedisNodes(id)
    const data = res.data;
    const nodesTmp = []
    Object.keys(data).forEach(function (key) {
      const replicaNodes = data[key];
      const masterNode = replicaNodes.shift();
      masterNode.children = replicaNodes
      nodesTmp.push(masterNode)
    });

    
    setState({
      redisNodes: nodesTmp,
      // isLoading: false
    });
  }

  useEffect(() => {
    // 页面加载时获取数据
    fetchClusterList(clusterId);
    // setState({
    //   isLoading: true
    // })
  }, []);


  // let redisNodeList = manageState.redisNodes;
  // console.log("==========", redisNodeList)
  const stateCell = (value) => (
    <div>
      {value ? <Icon type="success" style={{ color: '#1DC11D', marginRight: '10px' }} /> : <Icon type="error" style={{ color: '#FF3333', marginRight: '10px' }} />}
    </div>
  );

  const metaCallback = useCallback(
    ({ metaType, record }) => {
      setState({
        metaType,
        record,
        metaVisible: true
      })
    },
    [setState],
  )

  const moveSlotCallback = useCallback(() => {
    setState({
      moveSlotVisible: true,
    });
  }, [setState]);

  const replicaOfCallback = useCallback(() => {
    setState({
      replicaOfVisible: true,
    });
  }, [setState]);

  const nodeOperationCallback = useCallback(({ nodeOperationType, record }) => {
    setState({
      nodeOperatrionVisible: true,
      nodeOperationType: nodeOperationType,
      record: record
    });
  }, [setState]);

  const handleCancel = useCallback(() => {
    setState({
      metaVisible: false,
      moveSlotVisible: false,
      replicaOfVisible: false,
      nodeOperatrionVisible: false,
    });
  }, [setState]);

  const moveSlot = (record) => {
    moveSlotCallback({

    })
  };

  const startNode = (record) => {
    Message.success('start node:' + record.host + ':' + record.port);
  };

  const stopNode = (record) => {
    Message.success('stop node:' + record.host + ':' + record.port);
  };

  const restartNode = (record) => {
    Message.success('restart node:' + record.host + ':' + record.port);
  };

  const deleteNode = (record) => {
    Message.success('Delete node:' + record.host + ':' + record.port);
  };

  const slotRangeCell = (value, index, record) => (
    <div>
      <span> {record.slotRange} </span>
      {
        record.master && (
          <>
            <Tag size="small" color="blue" >
              {record.slotNumber}
            </Tag>
          </>)
      }
    </div>
  );

  const metaCell = (value, index, record) => (
    <div>
      <Tag size="small" color="blue" onClick={() => metaCallback({
        metaType: 'Info',
        record: record,
      })} >
        Info
      </Tag>
      <Tag size="small" color="blue" onClick={() => metaCallback({
        metaType: 'Config',
        record: record,
      })} >
        Config
      </Tag>
    </div>
  );

  const tableOperation = (value, index, record) => (
    <div className={styles.buttonGroup}>
      <MenuButton
        type="primary"
        label="Cluster"
      >
        <MenuButton.Item onClick={() => moveSlotCallback()}>MoveSlot</MenuButton.Item>
        {!record.master && (
          <MenuButton.Item onClick={() => replicaOfCallback()}>ReplicaOf</MenuButton.Item>
        )}
      </MenuButton>
      <MenuButton
        type="primary"
        // text
        popupProps={{
          autoFit: true,
        }}
        label="Node"
      >
        <MenuButton.Item onClick={() => nodeOperationCallback({ nodeOperationType: 'Start', record: record })}>Start</MenuButton.Item>
        <MenuButton.Item onClick={() => nodeOperationCallback({ nodeOperationType: 'Stop', record: record })}>Stop</MenuButton.Item>
        <MenuButton.Item onClick={() => nodeOperationCallback({ nodeOperationType: 'Restart', record: record })}>Restart</MenuButton.Item>
        <MenuButton.Item onClick={() => nodeOperationCallback({ nodeOperationType: 'Delete', record: record })}>Delete</MenuButton.Item>
      </MenuButton>
    </div>
  );

  return (
    <div>
      <Card free className={styles.container}>
        <Card.Content>
          <Table
            dataSource={redisNodes}
            hasBorder={false}
            // primaryKey=''
            isTree
            loading={isLoading}
          >
            <Table.Column title="Link State" dataIndex="linkState" width={120} />
            <Table.Column title="Flags" dataIndex="flags" width={100} />
            <Table.Column title="In Cluster" dataIndex="inCluster" cell={stateCell} />
            <Table.Column title="Run State" dataIndex="runState" cell={stateCell} />
            <Table.Column title="Host" dataIndex="host" />
            <Table.Column title="Port" dataIndex="port" width={70} />
            <Table.Column title="Slot Range" cell={slotRangeCell} sortable />
            <Table.Column title="Meta" cell={metaCell} />
            {/* width={240} */}
            <Table.Column title="Operation" dataIndex="operation" cell={tableOperation} />
          </Table>


        </Card.Content>
      </Card>
      <MetaDialog
        metaVisible={metaVisible}
        metaType={metaType}
        record={record}
        onClose={handleCancel}
        onCancel={handleCancel}
      />
      <MoveSlotDialog
        visible={moveSlotVisible}
        record={record}
        onClose={handleCancel}
      />

      <ReplicaOfDialog
        visible={replicaOfVisible}
        record={record}
        onClose={handleCancel}
      />

      <NodeOperationDialog
        visible={nodeOperatrionVisible}
        nodeOperationType={nodeOperationType}
        record={record}
        onClose={handleCancel}
      />

    </div>
  );
}

export default NodeListTable;