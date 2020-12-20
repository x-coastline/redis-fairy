import React, { useState, useEffect } from 'react';
import { Card } from '@alifd/next';
import {
  Chart,
  LineAdvance,
  Axis
} from 'bizcharts';
import { isNotEmpty } from '@/utils/validate';
import styles from './index.module.scss';
import monitorService from '../../../../services/monitor'

const scale = {
  value: {
    min: 0, // 定义数值范围的最小值
  },
  time: {
    tickCount: 10
  }
};
const lable = {
  // x 轴lable不旋转
  autoRotate: false
}
// time type
const TIME_TYPE_MINUTE = 0;
const TIME_TYPE_REAL_TIME = 1;

const DataCard = (props) => {
  const { title, item, nodeInfoParams, beforeMergeInfoList } = props
  const [infoList, setInfoList] = useState([])

  const getInfoList = async () => {
    const params = {
      ...nodeInfoParams,
      infoItem: item
    };
    const result = await monitorService.listInfo(params)
    // 处理后台返回数据
    if (result.code === 0) {
      let list = []
      result.data.forEach(nodeData => {
        // 合并数据
        list = [...list, ...nodeData];
      });
      setInfoList(list)
    }
  }

  useEffect(() => {
    const timeType = nodeInfoParams.timeType;
    if (timeType === TIME_TYPE_MINUTE) {
      if (isNotEmpty(nodeInfoParams.nodeList)) {
        getInfoList();
      }
    } else {
      setInfoList([])
    }
  }, [nodeInfoParams]);

  useEffect(() => {
    console.log(beforeMergeInfoList)
    const timeType = nodeInfoParams.timeType;
    if (timeType === TIME_TYPE_REAL_TIME) {
      const completedInfoList = []
      const field = monitorService.mapUnderscoreToCamelCase(item)
      // let count = 0;
      beforeMergeInfoList.forEach(oneTimeList => {
        // count++;
        oneTimeList.forEach(nodeInfo => {
          completedInfoList.push(
            {
              time: nodeInfo.time,
              node: nodeInfo.node,
              // value: count === 1 ? 0 : nodeInfo[field]
              value: nodeInfo[field]
            }
          )
        })
      })
      setInfoList(completedInfoList)
    }
  }, [beforeMergeInfoList])

  return (
    <Card free className={styles.conditionWrapper}>
      <div>
        <span className={styles.chartTitle}>{title}</span>
      </div>
      <Chart
        scale={scale}
        // scale={{value: {min: 0}}}
        padding={[30, 30, 50, 40]}
        autoFit
        height={360}
        data={infoList}
      >
        <LineAdvance
          shape="smooth"
          position='time*value'
          color='node'
          autoRotate={false}
        />

        {/* <Line shape='smooth' position='time*value' color='node' label='value' />
        <Tooltip showCrosshairs /> */}
        <Axis name="time" label={lable} />
      </Chart>
    </Card>
  )
}

export default DataCard;
