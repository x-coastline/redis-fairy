import React, { useState, setState, useEffect } from 'react';
import {
  ResponsiveGrid,
  Table,
  Card,
  Select,
  Balloon,
  Pagination
} from '@alifd/next';
import { isEmpty, isNotEmpty } from '@/utils/validate';
import moment from 'moment';
import styles from './index.module.scss';
import monitorService from '../../services/monitor'
import manageService from '../../services/manage'

const { Cell } = ResponsiveGrid;
const { Tooltip } = Balloon;

const SLOW_LOG_SIZE_DATASOURCE = [
  {
    value: 10
  },
  {
    value: 20,
  },
  {
    value: 50
  },
  {
    value: 100
  },
  {
    value: 200
  },
  {
    value: 500
  },
  {
    value: -1,
    label: 'Max'
  }
]

const SlowLog = (props) => {
  // TODO: 获取后台 slowlog 数据
  const currentClusterId = props.clusterId;
  const [slowLogDataSource, setSlowLogDataSource] = useState([])

  const [slowLogParam, setSlowLogParam] = useState({
    clusterId: currentClusterId,
    size: 10,
  });

  const [nodeList, setNodeList] = useState([]);

  const [selectedNodeList, setSelectedNodeList] = useState([]);

  const onSort = (dataIndex, order) => {
    setSlowLogDataSource(slowLogDataSource.sort((a, b) => {
      const result = a[dataIndex] - b[dataIndex];
      // eslint-disable-next-line no-nested-ternary
      return (order === 'asc') ? (result > 0 ? 1 : -1) : (result > 0 ? -1 : 1);
    }));
  }

  const onSelectNodesBlur = () => {
    const newSlowLogParam = { ...slowLogParam };
    newSlowLogParam.nodeList = selectedNodeList;
    setSlowLogParam(newSlowLogParam)
  }

  const getSlowLogList = async () => {
    if (isEmpty(slowLogParam.nodeList)) {
      return;
    }
    slowLogParam.clusterId = currentClusterId;
    const result = await monitorService.listSlowLog(slowLogParam);
    if (result.code === 0) {
      const data = result.data;
      for (let i = 0; i < data.length; i++) {
        const slowLog = data[i];
        slowLog.updateTime = moment(slowLog.updateTime).format('YYYY-MM-DD HH:mm:ss')
        if (isEmpty(slowLog.slowLogId)) {
          slowLog.slowLogId = i;
        }
      }
      setSlowLogDataSource(data)
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
    console.log(slowLogParam)
    getSlowLogList();
  }, [slowLogParam])

  useEffect(() => {
    getAllNodeList();
  }, [])

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
          <Select
            placeholder="Please select size"
            label="node size:"
            defaultValue={10}
            dataSource={SLOW_LOG_SIZE_DATASOURCE}
            className={styles.sizeSelect}
            onChange={value => { setSlowLogParam({...slowLogParam, ...{ size: value }}); }}
          />
          <Table
            dataSource={slowLogDataSource}
            // eslint-disable-next-line react/jsx-no-bind
            onSort={onSort.bind(this)}
            primaryKey='slowLogId'
          >
            {/* <Table.Column title="Id" htmlTitle="Unique Id" dataIndex="slowlog_id" /> */}
            <Table.Column title="Node" dataIndex="node" sortable />
            <Table.Column title="Type" dataIndex="type" sortable />
            <Table.Column title="Duration (ms)" dataIndex="duration" sortable />
            <Table.Column title="Command" dataIndex="command" sortable />
            <Table.Column title="Client Name" dataIndex="clientName" sortable />
            <Table.Column title="Time" dataIndex="updateTime" sortable />
          </Table>
          {/* <Pagination onChange={onPageChange} className="page-demo" /> */}
        </Card>

      </Cell>
    </ResponsiveGrid>
  )

};

export default SlowLog;
