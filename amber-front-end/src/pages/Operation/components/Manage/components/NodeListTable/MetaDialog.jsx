import React, { useEffect } from 'react';
import { Dialog, Input, Select, Icon } from '@alifd/next';
import { useSetState } from 'ahooks';

import manageService from '@/pages/Operation/services/manage';
import { isEmpty, isNotEmpty } from '@/utils/validate';


const DialogOperation = props => {

  const { metaVisible, metaType, record, ...lastProps } = props;

  // const [kvPairsMap, setKVPairsMap] = useState({})

  const [state, setState] = useSetState({
    kvPairsMap: {},
    filterKvPairMap: {},
    categorySelect: 'all',
    search: ''
  })

  const { kvPairsMap, filterKvPairMap, categorySelect, search } = state

  const fetchDetail = async (redisNode) => {
    const node = `${redisNode.host}:${redisNode.port}`
    if (metaType === 'Info') {
      const res = await manageService.nodeInfo(redisNode.clusterId, node)
      // setKVPairsMap(res.data)
    } else {
      const res = await manageService.configs(redisNode.clusterId, node)
      setState({ kvPairsMap: res.data, filterKvPairMap: res.data })
    }
  }

  // const fetchConfigs = async (redisNode) =>{
  //   const node = `${redisNode.host}:${redisNode.port}`
  //   const res = await manageService.configs(redisNode.clusterId, node)
  //   console.log('configs',res.data)
  // }

  const getCategory = () => {
    if (metaType === 'Info') {
      return [
        { value: 'all', label: 'All' },
        { value: 'server', label: 'Server' },
        { value: 'clients', label: 'Clients' },
        { value: 'memory', label: 'Memory' },
        { value: 'persistence', label: 'Persistence' },
        { value: 'stats', label: 'Stats' },
        { value: 'replication', label: 'Replication' },
        { value: 'cpu', label: 'CPU' },
        { value: 'cluster', label: 'Cluster' },
        { value: 'keyspace', label: 'Keyspace' },
      ];
    } else {
      return [
        { value: 'all', label: 'All Category' },
        { value: 'network', label: 'Network' },
        { value: 'general', label: 'General' },
        { value: 'snapshotting', label: 'Snapshotting' },
        { value: 'replication', label: 'Replication' },
        { value: 'security', label: 'Security' },
        { value: 'clients', label: 'Clients' },
        { value: 'lazyFreeing', label: 'Lazy Freeing' },
        { value: 'appendOnlyMode', label: 'APPEND ONLY MODE' },
        { value: 'LuaScripting', label: 'Lua Scripting' },
        { value: 'RedisCluster', label: 'Redis Cluster' },
        { value: 'ClusterDocker', label: 'Cluster Docker/Nat' },
        { value: 'Slowlog', label: 'Slowlog' },
        { value: 'latencyMonitor', label: 'Latency Monitor' },
        { value: 'eventNotification', label: 'Event Notification' },
        { value: 'defragmentation', label: 'Defragmentation' },
        { value: 'advancedconfig', label: 'Advanced Config' },
      ]
    }

  }


  const getDialogTitle = () => {
    switch (metaType) {
      case 'Info':
      default:
        return 'Info';
      case 'Config':
        return 'Config';
    }
  };

  useEffect(() => {
    if (record) {
      console.log(record)
      fetchDetail(record, metaType);
    }
  }, [metaVisible, record, metaType]);

  const onCategoryChange = (selectKey) => {
    setState({ categorySelect: selectKey })
    if (selectKey === 'all') {
      setState({ filterKvPairMap: kvPairsMap })
    } else {
      Object.entries(kvPairsMap).forEach(([category, pairs]) => {
        if (selectKey.toUpperCase() === category.toUpperCase()) {
          const tmp = {}
          tmp[category] = pairs;
          setState({ filterKvPairMap: tmp })
        }
      })
    }
  }
  const onSearchChange = () => {
    console.log(categorySelect)
    onCategoryChange(categorySelect)
    if (searchInput !== '') {
      const tmpMap = {}
      Object.entries(filterKvPairMap).forEach(([category, pairs]) => {
        const tmpPair = []
        // console.log('tmppair', pairs)
        pairs.forEach(pair => {
          if (pair.key.search(searchInput) !== -1) {
            tmpPair.push(pair)
          }
        })
        if (tmpPair.length !== 0) {
          tmpMap[category] = tmpPair;
        }
      })
      setState({ filterKvPairMap: tmpMap })
    }

  }

  const onFilter = (c, s) => {
    setState({ filterKvPairMap: {} })
    console.log('====', c, s)
    let filterMap = {}
    if (c === 'all' || isEmpty(c)) {
      filterMap = kvPairsMap
    } else {
      Object.entries(kvPairsMap).forEach(([category, pairs]) => {
        if (c.toUpperCase() === category.toUpperCase()) {
          filterMap[c] = pairs;
        }
      })
    }
    if (isEmpty(s)) {
      setState({ filterKvPairMap: filterMap })
      return;
    }
    console.log(filterMap)
    Object.values(filterMap).forEach((pairs) => {
      console.log(pairs)
      const filterPairs = pairs.filter(pair => pair.key.search(s) > -1 );
      filterMap[c] = filterPairs;
      setState({ filterKvPairMap: filterMap })
    })
  }

  return (
    <Dialog
      visible={metaVisible}
      shouldUpdatePosition
      isFullScreen
      title={getDialogTitle(metaType)}
      style={{
        width: 600,
      }}
      footer={false}
      {...lastProps}
    >
      <div>
        <Select
          id="basic-demo"
          defaultValue="all"
          aria-label="name is"
          dataSource={getCategory()}
          showSearch
          hasClear
          style={{ width: '32%' }}
          onChange={(value) => {console.log(value); setState({ categorySelect: value }); onFilter(value, search) }} />

        <Input placeholder="search" aria-label="Medium" style={{ width: '60%' }} onChange={(value) => {console.log(value); setState({ search: value }); onFilter(categorySelect, value) }} />
        {
          Object.entries(filterKvPairMap).map(([category, pairs]) => {
            return (
              <div key={category}>
                {
                  categorySelect === 'all' && <span style={{ color: 'red' }}>{category}</span>}
                {
                  pairs.map((pair, index) => {
                    return (
                      <div key={index}>
                        <span >{pair.key}:</span> <Icon size="small" type="help" aria-label="hello" /><span style={{ color: 'blue' }}>{pair.value}</span>
                      </div>
                    )
                  })
                }
              </div>
            )
          })
        }
      </div>
      {/* <Operation ref={operationRef} actionType={actionType} dataSource={dataSource} /> */}
    </Dialog>
  );
};

export default DialogOperation;