import React, { useEffect } from 'react';
import {
  Box,
  Card,
  Tag,
  ResponsiveGrid,
  Icon,
  Button,
  Dialog
} from '@alifd/next';
import { store, Link } from 'ice';
import { store as dashboardStore } from 'ice/Dashboard'
import StatisticsCard from './components/StatisticsCard';
import ClusterForm from './components/ClusterForm';
import styles from './index.module.scss';

const { Cell } = ResponsiveGrid;

const MONITOR = 'monitor';
const MANAGE = 'manage'
const SLOWLOG = 'slowlog'
const Dashboard = () => {
  const state = store.useModelState('group');
  const [dashboardState, dashboardDispatchers] = dashboardStore.useModel('dashboard');

  useEffect(() => {
    // 页面加载时获取数据
    dashboardDispatchers.fetchClusterList(state.currentGroup.groupId);
    dashboardDispatchers.fetchStatisticsList();
  }, []);

  const onOpen = () => {
    dashboardDispatchers.setClusterFormVisible(true);
  }

  const onClose = () => {
    dashboardDispatchers.setClusterFormVisible(false);
  }

  const onFormChange = (form) => {
    dashboardDispatchers.setClusterForm(form)
  }
  return (
    <>
      <ResponsiveGrid gap={20}>
        {
          dashboardState.statisticsCardList.map((statisticsCard, index) => {
            return (
              <Cell colSpan={3} key={index}>
                <StatisticsCard statistics={statisticsCard} />
              </Cell>
            )
          })
        }
        <Cell colSpan={3} className={styles.ListItem}>
          <Box className={styles.add} justify="center" align="center" onClick={onOpen}>
            <Icon type="add" className={styles.icon} />
            <div className={styles.addText}>Add Redis</div>
          </Box>
        </Cell>

        {
          dashboardState.clusterList &&
          dashboardState.clusterList.map((cluster, index) => {
            return (
              <Cell colSpan={3} className={styles.ListItem} key={index}>
                <div className={styles.main}>
                  <Card free >
                    {/* TODO: 告警个数可在此进行展示 */}
                    <Card.Header title={cluster.clusterName} extra={cluster.requirePass ? <Icon type="lock" style={{ color: '#36cfc9' }} /> : null} />
                    <Card.Divider />
                    <Card.Content>
                      <div className={styles.item}>
                        <span>State: </span>
                        <Tag size="small" color={cluster.state === 'HEALTH' ? 'turquoise' : 'red'}>
                          {cluster.state}
                        </Tag>
                      </div>
                      <div className={styles.item}>
                        <span>Mode: </span>
                        <Tag size="small">
                          {cluster.redisMode.toLowerCase()}
                        </Tag>
                      </div>
                      <div className={styles.item}>
                        <span>Version: </span>
                        <Tag size="small">
                          {cluster.redisVersion}
                        </Tag>
                      </div>
                      <div className={styles.item}>
                        <span>Master: </span>
                        <Tag size="small">
                          {cluster.masterNumber}
                        </Tag>
                      </div>
                      <div className={styles.item}>
                        <span>Node: </span>
                        <Tag size="small">
                          {cluster.nodeNumber}
                        </Tag>
                      </div>
                      <div className={styles.item}>
                        <span>Env: </span>
                        <Tag size="small">
                          {cluster.environment.toLowerCase()}
                        </Tag>
                      </div>
                      <div className={styles.item}>
                        <span>From: </span>
                        <Tag size="small">
                          {cluster.importType.toLowerCase()}
                        </Tag>
                      </div>
                    </Card.Content>
                    <Card.Divider />
                    <Card.Actions>
                      <div className={styles.operationWrapper}>
                        <div>
                          <Button type="primary" text><Link to={`/operation/${MONITOR}/${cluster.clusterId}`}>Monitor</Link></Button>
                          <Button type="primary" text><Link to={`/operation/${MANAGE}/${cluster.clusterId}`}>Manage</Link></Button>
                          <Button type="primary" text><Link to={`/operation/${MONITOR}/${cluster.clusterId}`}>More</Link></Button>
                        </div>
                        <div>
                          <Button type="primary" text>Edit</Button>
                          <Button type="primary" text>Delete</Button>
                        </div>

                        {/* <Dropdown trigger={<a>Click me <Icon type="arrow-down" /></a>} triggerType="click">
                          <Menu>
                            <Menu.Item>Option 1</Menu.Item>
                            <Menu.Item>Option 2</Menu.Item>
                            <Menu.Item>Option 3</Menu.Item>
                            <Menu.Item>Option 4</Menu.Item>
                          </Menu>
                        </Dropdown> */}
                      </div>
                    </Card.Actions>
                  </Card>
                </div>
              </Cell>
            )
          })
        }

      </ResponsiveGrid>
      <Dialog
        isFullScreen
        title="Add Cluster"
        visible={dashboardState.clusterFormVisible}
        style={{
          width: 600,
        }}
        // 隐藏底部按钮
        footer={false}
        // onOk={handleSubmitForm}
        // onCancel={onClose}
        onClose={onClose}
      >
        <ClusterForm onClose={onClose} onFormChange={onFormChange} />
      </Dialog>
    </>
  )

};

// Dashboard.pageConfig = {
//    auth: ['group_manager', 'group_member']
// };

export default Dashboard;
