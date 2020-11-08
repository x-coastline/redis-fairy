import React, { useEffect, useState } from 'react';
import {
  ResponsiveGrid,
  Button,
  Drawer,
  Tag,
  Menu,
  Icon,
  Divider
} from '@alifd/next';
import PageHeader from '@/components/PageHeader';
import clusterService from '@/services/cluster';
import { Link } from 'ice';
import Monitor from './components/Monitor'
import SlowLog from './components/SlowLog'
import Manage from './components/Manage'
import ClientList from './components/ClientList'
// import OperationDrawer from './components/OperationDrawer';
import styles from './index.module.scss';

const { SubMenu, Item } = Menu;

const { Cell } = ResponsiveGrid;

const OPERATION = 'operation'
const MONITOR = 'monitor';
const MANAGE = 'manage'
const SLOWLOG = 'slowlog'
const CLIENT_LIST = 'client-list'


const Operation = (props) => {
  const clusterId = props.match.params.clusterId;
  const currentOperation = props.match.params.operation
  const [visible, setVisible] = useState(false);

  const [cluster, setCluster] = useState({})

  const [title, setTitle] = useState('')
  const [breadcrumbs, setBreadcrumbs] = useState([
    {
      name: OPERATION,
    },
    {
      name: MONITOR,
    },
  ])

  // 默认值为 null, 否则可能会报出组件已经销毁却仍然请求的警告
  const [component, setComponent] = useState(null)

  const visibleChange = () => {
    setVisible(!visible);
  }

  const getCluster = async () => {
    const result = await clusterService.getCluster(clusterId);
    const data = result.data
    setCluster(data)
    setTitle(data.clusterName)
  }


  const updateBreadcrumbs = (type) => {
    setBreadcrumbs([{ name: OPERATION }, { name: type }])
  }

  const changeComponent = (name) => {
    setComponent(name);
    props.history.push(`/operation/${name}/${clusterId}`);
    updateBreadcrumbs(name)
    setVisible(!visible);
  }

  const getComponent = () => {
    switch (component) {
      case MONITOR:
        return <Monitor clusterId={clusterId} />
      case SLOWLOG:
        return <SlowLog clusterId={clusterId} />
      case MANAGE:
        return <Manage clusterId={clusterId} />
      case CLIENT_LIST:
        return <ClientList clusterId={clusterId} />
      default:
        return null;
    }
  }

  useEffect(() => {
    getCluster();
    setComponent(currentOperation);
  }, [])

  return (
    <ResponsiveGrid gap={20}>
      <Cell colSpan={12}>
        <PageHeader
          title={title}
          breadcrumbs={breadcrumbs}
        />
      </Cell>
      <Cell colSpan={12} className={styles.infoWrapper}>
        <div>
          <span>
            Sate:  <Tag type="normal" size="small" color='blue'>{cluster.state}</Tag>
          </span>
          <Divider direction="ver" />
          <span>
            Mode:  <Tag type="normal" size="small" color='blue'>{cluster.redisMode}</Tag>
          </span>
          <Divider direction="ver" />
          <span>
            Version:  <Tag type="normal" size="small" color='blue'>{cluster.redisVersion}</Tag>
          </span>
          <Divider direction="ver" />
          <span>
            Master:  <Tag type="normal" size="small" color='blue'>{cluster.masterNumber}</Tag>
          </span>
          <Divider direction="ver" />
          <span>
            Node:  <Tag type="normal" size="small" color='blue'>{cluster.nodeNumber}</Tag>
          </span>
        </div>
        <div>
          <Button type="primary" text onClick={visibleChange} ><Icon type="arrow-double-left" /> Operation</Button>
          <Drawer
            title="Operation"
            placement="right"
            width={300}
            visible={visible}
            onClose={visibleChange}>
            <Menu defaultOpenKeys="0" className={styles.operationWrapper} openMode="single">
              <Item key="0" onSelect={() => { changeComponent(MONITOR) }}>Monitor</Item>
              <Item key="1" onSelect={() => { changeComponent(SLOWLOG) }}>Slow Log</Item>
              <Item key="2" onSelect={() => { changeComponent(MANAGE) }}>Manage</Item>
              <Item key="3" onSelect={() => { changeComponent(CLIENT_LIST) }}>Client List</Item>
              <Item>Alert</Item>
              <Item>Data</Item>
              <Item>Analysis</Item>
              {/* <SubMenu key="1" label="Manage">
                <Item key="1-0">Slot Manage</Item>
                <Item key="1-1">Sub option 2</Item>
                <Item key="1-2">Sub option 3</Item>
              </SubMenu> */}
            </Menu>
          </Drawer>
        </div>
      </Cell>

      <Cell colSpan={12}>
        {getComponent(currentOperation)}
      </Cell>

    </ResponsiveGrid>
  );
};

export default Operation;
