import React, { useState, setState, useEffect } from 'react';
import {
  ResponsiveGrid,
  Table,
  Card,
  Select,
  Balloon
} from '@alifd/next';
import { isEmpty } from '@/utils/validate';
import styles from './index.module.scss';
import monitorService from '../../services/monitor'
import manageService from '../../services/manage'

const { Cell } = ResponsiveGrid;
const { Tooltip } = Balloon;


const ClientList = (props) => {
  // TODO: 获取后台 slowlog 数据
  const currentClusterId = props.clusterId;
  const [clientListDataSource, setClientListDataSource] = useState([])

  const [clientListStatistics, setClientListStatistics] = useState([])

  const [clientListParam, setClientListParam] = useState({
    clusterId: currentClusterId,
  });

  const [nodeList, setNodeList] = useState([]);

  const [selectedNodeList, setSelectedNodeList] = useState([]);

  const onSort = (dataIndex, order) => {
    setClientListDataSource(clientListDataSource.sort((a, b) => {
      const result = a[dataIndex] - b[dataIndex];
      // eslint-disable-next-line no-nested-ternary
      return (order === 'asc') ? (result > 0 ? 1 : -1) : (result > 0 ? -1 : 1);
    }));
  }

  const onSelectNodesBlur = () => {
    const newClientListParam = { ...clientListParam };
    newClientListParam.nodeList = selectedNodeList;
    setClientListParam(newClientListParam)
  }

  const getClientListMap = async () => {
    if (isEmpty(clientListParam.nodeList)) {
      return;
    }
    clientListParam.clusterId = currentClusterId;
    const result = await monitorService.listClients(clientListParam);
    if (result.code === 0) {
      const data = result.data;
      let clientList = []
      const clientStatistics = []
      Object.entries(data).forEach(([key, value]) => {
        clientList = [...clientList, ...value]
        clientStatistics.push({
          serverAddress: key,
          clientNumber: value.length
        })
      })
      setClientListStatistics(clientStatistics)
      setClientListDataSource(clientList)
    }
  }

  const getAllNodeList = async () => {
    const result = await manageService.getRedisNodes(props.clusterId)
    if (result.code === 0) {
      const newNodeList = [];
      Object.values(result.data).forEach(value => {
        value.forEach(node => {
          const newNode = { ...node };
          newNode.value = `${node.host}:${node.port}`;
          newNode.label = `${node.host}:${node.port} ${node.nodeRole}`;
          newNodeList.push(newNode)
        })
      });
      setNodeList(newNodeList)
    }
  }

  const maxTagPlaceholder = (selectedValues, totalValues) => {
    const trigger = <span>{`${selectedValues.length}/${totalValues.length}`}</span>;
    const labels = selectedValues.map(obj => obj.label);
    return <Tooltip trigger={trigger}>{labels.join(', ')}</Tooltip>;
  };


  useEffect(() => {
    getClientListMap();
  }, [clientListParam])

  useEffect(() => {
    getAllNodeList();
  }, [currentClusterId])

  return (
    <ResponsiveGrid gap={20}>
      <Cell colSpan={12}>
        <Card free className={styles.backgroundWrapper}>
          <Select
            placeholder="Please select nodes"
            id="node-list"
            dataSource={nodeList}
            mode="multiple"
            maxTagCount={1}
            tagInline
            maxTagPlaceholder={maxTagPlaceholder}
            showSearch
            hasSelectAll
            style={{ width: 260 }}
            className={styles.nodeListSelect}
            onChange={(value) => setSelectedNodeList(value)}
            onBlur={onSelectNodesBlur}
          />
          <Table
            dataSource={clientListStatistics}
            // eslint-disable-next-line react/jsx-no-bind
            primaryKey='serverAddress'
            className={styles.statisticsTable}
          >
            <Table.Column title="serverAddress" dataIndex="serverAddress" sortable />
            <Table.Column title="Client Number" dataIndex="clientNumber" sortable />
          </Table>
          <Table
            dataSource={clientListDataSource}
            // eslint-disable-next-line react/jsx-no-bind
            onSort={onSort.bind(this)}
            primaryKey='id'
          >
            <Table.Column title="Server Address" dataIndex="serverAddress" sortable />
            <Table.Column title="Client Address" dataIndex="clientAddress" sortable />
            <Table.Column title="Client Name" dataIndex="clientName" sortable />
            <Table.Column title="Command" dataIndex="command" sortable />
            <Table.Column title="Age(s)" dataIndex="age" sortable />
            <Table.Column title="Idle" dataIndex="idle" sortable />
            <Table.Column title="Description" dataIndex="description" sortable />
          </Table>
          {/* <Pagination onChange={onPageChange} className="page-demo" /> */}
        </Card>

      </Cell>
    </ResponsiveGrid>
  )

};

export default ClientList;
