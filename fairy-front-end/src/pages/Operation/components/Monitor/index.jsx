import React, { useState, useEffect } from 'react';
import {
  Button,
  ResponsiveGrid,
  Card,
  Select,
  DatePicker,
  Divider,
  Balloon,
  Icon
} from '@alifd/next';
import moment from 'moment';
import { isEmpty, isNotEmpty } from '@/utils/validate';
import manageService from '../../services/manage';
import monitorService from '../../services/monitor';
import DataCard from './components/DataCard';
import styles from './index.module.scss';

const { Tooltip } = Balloon;
const { RangePicker } = DatePicker;
const Option = Select.Option;
const { Cell } = ResponsiveGrid;

// metrics
const metricsItemList = [
  {
    value: 'used_memory',
    title: 'used_memory(MB)'
  },
  {
    value: 'used_memory_rss',
    title: 'used_memory_rss(MB)'
  },
  {
    value: 'used_memory_overhead',
    title: 'used_memory_overhead(MB)'
  },
  {
    value: 'used_memory_dataset',
    title: 'used_memory_dataset(MB)'
  },
  {
    value: 'mem_fragmentation_ratio',
    title: 'mem_fragmentation_ratio'
  },
  {
    value: 'connections_received',
    title: 'connections_received'
  },
  {
    value: 'rejected_connections',
    title: 'rejected_connections'
  },
  {
    value: 'connected_clients',
    title: 'connected_clients'
  },
  {
    value: 'blocked_clients',
    title: 'blocked_clients'
  },
  {
    value: 'commands_processed',
    title: 'commands_processed'
  },
  {
    value: 'instantaneous_ops_per_sec',
    title: 'instantaneous_ops_per_sec'
  },
  {
    value: 'sync_full',
    title: 'sync_full'
  },
  {
    value: 'sync_partial_ok',
    title: 'sync_partial_ok'
  },
  {
    value: 'sync_partial_err',
    title: 'sync_partial_err'
  },
  {
    value: 'keyspace_hits_ratio',
    title: 'keyspace_hits_ratio'
  },
  {
    value: 'keys',
    title: 'keys'
  },
  {
    value: 'expires',
    title: 'expires'
  },
  {
    value: 'cpu_sys',
    title: 'cpu_sys'
  },
  {
    value: 'cpu_user',
    title: 'cpu_user'
  },
]
const DEFAULT_SELECTED_METRICS = [
  {
    value: 'used_memory',
    title: 'used_memory(MB)'
  },
  // {
  //   value: 'used_memory_rss',
  //   title: 'used_memory_rss(MB)'
  // },
  // {
  //   value: 'used_memory_overhead',
  //   title: 'used_memory_overhead(MB)'
  // },
  // {
  //   value: 'used_memory_dataset',
  //   title: 'used_memory_dataset(MB)'
  // },
  // {
  //   value: 'mem_fragmentation_ratio',
  //   title: 'mem_fragmentation_ratio'
  // },
  // {
  //   value: 'keyspace_hits_ratio',
  //   title: 'keyspace_hits_ratio'
  // },
  // {
  //   value: 'keys',
  //   title: 'keys'
  // },
  // {
  //   value: 'expires',
  //   title: 'expires'
  // },
  // {
  //   value: 'connections_received',
  //   title: 'connections_received'
  // },
  // {
  //   value: 'rejected_connections',
  //   title: 'rejected_connections'
  // },
  // {
  //   value: 'connected_clients',
  //   title: 'connected_clients'
  // },
  // {
  //   value: 'blocked_clients',
  //   title: 'blocked_clients'
  // },
  // {
  //   value: 'commands_processed',
  //   title: 'commands_processed'
  // },
  // {
  //   value: 'instantaneous_ops_per_sec',
  //   title: 'instantaneous_ops_per_sec'
  // },


]

// time type
const TIME_TYPE_MINUTE = 0;
const TIME_TYPE_REAL_TIME = 1;

// role type
const MASTER = 'MASTER';
const SLAVE = 'SLAVE';
const ALL = 'ALL';
const NODES = 'NODES'

// time
const MILLIS_SECOND_ONE_MINUTE = 60 * 1000;
const MINUTE_15 = 15;
const MINUTE_30 = 30;
const HOUR_1 = 60;
const HOUR_2 = 60 * 2;
const HOUR_3 = 60 * 3;
const HOUR_6 = 60 * 6;
const HOUR_12 = 60 * 12;
const DAY_1 = 60 * 24;
const DAY_2 = DAY_1 * 2;
const DAY_3 = DAY_1 * 3;
const DAY_7 = DAY_1 * 7;

const DEFAULT_START_TIME = moment().subtract(15, 'minute');
const DEFAULT_END_TIME = moment();

const Monitor = (props) => {

  const [nodeInfoParams, setNodeInfoParams] = useState({
    clusterId: props.clusterId,
    startTime: DEFAULT_START_TIME.valueOf() - MILLIS_SECOND_ONE_MINUTE,
    endTime: DEFAULT_END_TIME.valueOf(),
    timeType: TIME_TYPE_MINUTE
  })

  const [nodeList, setNodeList] = useState([])

  // 单独选择 nodes 时使用
  const [selectedNodeList, setSelectedNodeList] = useState([])

  const [selectedMetricsItemList, setSelectedMetricsItemList] = useState(DEFAULT_SELECTED_METRICS)

  const [metricsSelectVisible, setMetricsSelectVisible] = useState(true)

  const [timeRange, setTimeRange] = useState([DEFAULT_START_TIME, DEFAULT_END_TIME])

  const [infoTimer, setInfoTimer] = useState(null);

  const [timeRangeDisable, setTimeRangeDisable] = useState(false);

  const [beforeMergeInfoList, setBeforeMergeInfoList] = useState([])

  const resetMetricsSelect = () => {
    setMetricsSelectVisible(true);
    setSelectedNodeList([])
  }

  /**
   * role type 变化后更改 nodeParams 中的 nodeList 的值
   * @param {*} value
   */
  const handleRoleTypeChange = (roleType) => {
    const newNodeInfoParams = { ...nodeInfoParams };
    let needNodeList = [];
    resetMetricsSelect()
    if (roleType === ALL) {
      nodeList.forEach(node => {
        needNodeList.push(`${node.host}:${node.port}`)
      })
    } else if (roleType === MASTER || roleType === SLAVE) {
      nodeList.forEach(node => {
        if (node.nodeRole === roleType) {
          needNodeList.push(`${node.host}:${node.port}`)
        }
      })
    } else {
      setMetricsSelectVisible(false);
      needNodeList = selectedNodeList;
      if (isEmpty(needNodeList) || needNodeList.length === 0) {
        return;
      }
    }
    newNodeInfoParams.nodeList = needNodeList;
    setNodeInfoParams(newNodeInfoParams);
  }

  /**
   * 设置仅显示 master 节点的数据
   * @param nodes 所有节点
   */
  const setMasterNodeListToParams = (nodes) => {
    const newNodeInfoParams = { ...nodeInfoParams };
    const needNodeList = []
    nodes.forEach(node => {
      if (node.nodeRole === MASTER) {
        needNodeList.push(`${node.host}:${node.port}`)
      }
    })
    newNodeInfoParams.nodeList = needNodeList;
    setNodeInfoParams(newNodeInfoParams);
  }

  const handleSelectNodesBlur = () => {
    const newNodeInfoParams = { ...nodeInfoParams };
    newNodeInfoParams.nodeList = selectedNodeList;
    setNodeInfoParams(newNodeInfoParams);
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
      // 默认仅展示master
      setMasterNodeListToParams(newNodeList)
    }
  }

  // onClick 方法封装后会被react判断方法深度过大而报错
  const footerRender = () => {
    return (
      <div className={styles.customFooter} >
        <Button type="normal" text onClick={() => { setTimeRange([moment().subtract(MINUTE_15, 'minute'), moment()]) }}>Last 15 Min</Button>
        <Divider direction="ver" />
        <Button type="normal" text onClick={() => { setTimeRange([moment().subtract(MINUTE_30, 'minute'), moment()]) }}>Last 30 Min</Button>
        <Divider direction="ver" />
        <Button type="normal" text onClick={() => { setTimeRange([moment().subtract(HOUR_1, 'minute'), moment()]) }}>Last 1 Hour</Button>
        <Divider direction="ver" />
        <Button type="normal" text onClick={() => { setTimeRange([moment().subtract(HOUR_2, 'minute'), moment()]) }}>Last 2 Hours</Button>
        <Divider direction="ver" />
        <Button type="normal" text onClick={() => { setTimeRange([moment().subtract(HOUR_3, 'minute'), moment()]) }}>Last 3 Hours</Button>
        <Divider direction="ver" />
        <Button type="normal" text onClick={() => { setTimeRange([moment().subtract(HOUR_6, 'minute'), moment()]) }}>Last 6 Hours</Button>
        <Divider direction="ver" />
        <Button type="normal" text onClick={() => { setTimeRange([moment().subtract(HOUR_12, 'minute'), moment()]) }}>Last 12 Hours</Button>
        <Divider direction="ver" />
        <Button type="normal" text onClick={() => { setTimeRange([moment().subtract(DAY_1, 'minute'), moment()]) }}>Last 1 Day</Button>
        <Divider direction="ver" />
        <Button type="normal" text onClick={() => { setTimeRange([moment().subtract(DAY_2, 'minute'), moment()]) }}>Last 2 Days</Button>
        <Divider direction="ver" />
        <Button type="normal" text onClick={() => { setTimeRange([moment().subtract(DAY_3, 'minute'), moment()]) }}>Last 3 Days</Button>
        <Divider direction="ver" />
        <Button type="normal" text onClick={() => { setTimeRange([moment().subtract(DAY_7, 'minute'), moment()]) }}>Last Week</Button>
      </div >
    );
  }

  const maxTagPlaceholder = (selectedValues, totalValues) => {
    const trigger = <span>{`${selectedValues.length}/${totalValues.length}`}</span>;
    const labels = selectedValues.map(obj => obj.label);
    return <Tooltip trigger={trigger}>{labels.join(', ')}</Tooltip>;
  };

  const isTimeRangeCompleted = () => {
    return isNotEmpty(timeRange[0]) && isNotEmpty(timeRange[1]);
  }

  const handleRefresh = () => {
    if (isTimeRangeCompleted()) {
      const duration = timeRange[1].valueOf() - timeRange[0].valueOf();
      if (duration < MILLIS_SECOND_ONE_MINUTE) {
        return;
      }
      const newEndTime = moment();
      const newStartTime = moment(newEndTime.valueOf() - duration)
      setTimeRange([newStartTime, newEndTime])
    } else {
      // 默认值
      setTimeRange([moment().subtract(MINUTE_15, 'minute'), moment()])
    }
  }

  const getRealTimeNodeInfoList = async () => {
    const params = { ...nodeInfoParams }
    const result = await monitorService.getRealTimeInfoList(params);
    if (result.code === 0) {
      const oneTimeData = result.data
      if (isNotEmpty(oneTimeData)) {
        if (beforeMergeInfoList.length === 30) {
          // 弹出第一个数据
          beforeMergeInfoList.shift()
        }
        beforeMergeInfoList.push(oneTimeData)
        setBeforeMergeInfoList([...beforeMergeInfoList])
      }
    }
  }


  const createTimeout = () => {
    let delay = 2000;
    if (nodeInfoParams.timeType === TIME_TYPE_REAL_TIME) {
      setInfoTimer(setTimeout(async function requestData() {
        const startTime = moment().valueOf();
        await getRealTimeNodeInfoList();
        const endTime = moment().valueOf();
        delay = endTime - startTime;
        if (delay < 2000) {
          delay = 2000;
        }
        setInfoTimer(setTimeout(requestData, delay));
      }, delay))
    }
  }

  const handleTimeTypeChange = (value) => {
    setNodeInfoParams({ ...nodeInfoParams, timeType: value });
    if (value === TIME_TYPE_MINUTE) {
      // TODO: 禁用其他条件
      setTimeRangeDisable(false)
      setBeforeMergeInfoList([])
    } else {
      // TODO:开启定时器
      setTimeRangeDisable(true)
      createTimeout()
    }
  }



  // 监听time range 变化
  useEffect(() => {
    if (isTimeRangeCompleted()) {
      const newNodeInfoParams = { ...nodeInfoParams }
      newNodeInfoParams.startTime = timeRange[0].valueOf() - MILLIS_SECOND_ONE_MINUTE;
      newNodeInfoParams.endTime = timeRange[1].valueOf();
      setNodeInfoParams(newNodeInfoParams)
    }
  }, [timeRange])

  useEffect(() => {
    getAllNodeList();
  }, [props.clusterId])

  /**
   * node list 发生变化重新创建定时器
   */
  useEffect(() => {
    clearTimeout(infoTimer)
    if (nodeInfoParams.timeType === TIME_TYPE_REAL_TIME) {
      createTimeout()
    }
  }, [nodeInfoParams])

  useEffect(() => {
    return async function cleanup() {
      clearTimeout(infoTimer)
    };
  });

  return (
    <ResponsiveGrid gap={20}>
      <Cell colSpan={12}>
        <Card free className={styles.conditionWrapper}>
          <div>
            <Select id="timeType" defaultValue={TIME_TYPE_MINUTE} style={{ width: '120px' }} onChange={handleTimeTypeChange}>
              <Option value={TIME_TYPE_MINUTE}>Minute</Option>
              <Option value={TIME_TYPE_REAL_TIME}>Real Time</Option>
            </Select>
            <Divider direction="ver" />
            <Select id="roleType" defaultValue="Master" aria-label="name is" onChange={handleRoleTypeChange} showSearch>
              <Option value={MASTER}>Master</Option>
              <Option value={SLAVE}>Slave</Option>
              <Option value={ALL}>All</Option>
              <Option value={NODES}>Nodes</Option>
            </Select>
            <Divider direction="ver" />
            <Select
              placeholder="Select nodes"
              mode="multiple"
              maxTagCount={1}
              tagInline
              maxTagPlaceholder={maxTagPlaceholder}
              showSearch
              value={selectedNodeList}
              dataSource={nodeList}
              style={{ width: 260 }}
              onChange={(value) => { setSelectedNodeList(value) }}
              onBlur={handleSelectNodesBlur}
              disabled={metricsSelectVisible} />
            <Divider direction="ver" />
            <Select placeholder="Select metrics"
              mode="multiple"
              maxTagCount={1}
              tagInline
              maxTagPlaceholder={maxTagPlaceholder}
              defaultValue={DEFAULT_SELECTED_METRICS}
              showSearch
              dataSource={metricsItemList}
              style={{ width: 220 }}
              onChange={(value, actionType, item) => { setSelectedMetricsItemList(item) }}
            />
            <Divider direction="ver" />
            <RangePicker
              defaultValue={timeRange}
              value={timeRange}
              showTime={{ format: 'HH:mm', minuteStep: 5 }}
              onChange={value => setTimeRange(value)}
              footerRender={footerRender}
              disabled={timeRangeDisable}
            />
          </div>
          <Button text onClick={handleRefresh}><Icon type="refresh" /> Refresh</Button>
        </Card>

      </Cell>
      {
        selectedMetricsItemList.map((metricsItem) => {
          return (
            <Cell colSpan={6} key={metricsItem.value}>
              <DataCard title={metricsItem.title} item={metricsItem.value} nodeInfoParams={nodeInfoParams} beforeMergeInfoList={beforeMergeInfoList} />
            </Cell>
          )
        })
      }
    </ResponsiveGrid>

  );
};

export default Monitor;
