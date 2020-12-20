import React, { useCallback } from 'react';
import { Button, Field, Table, Card, Pagination, Message, Dialog, Divider, Grid } from '@alifd/next';
import { useFusionTable, useSetState } from 'ahooks';
import groupService from '@/services/group';
import EmptyBlock from './EmptyBlock';
import ExceptionBlock from './ExceptionBlock';
import DialogOperation from './DialogOperation';
import styles from './index.module.scss';

const { Row, Col } = Grid;
const getTableData = ({
  current,
  pageSize
}, formData) => {
  if (!formData.status || formData.status === 'normal') {
    let query = `pageIndex=${current}&pageSize=${pageSize}`;
    Object.entries(formData).forEach(([key, value]) => {
      if (value) {
        query += `&${key}=${value}`;
      }
    });
    return groupService.getGroupList(current, pageSize).then(res => ({
      total: res.data.total,
      list: res.data.groups.slice(0, 10)
    }));
  }

  if (formData.status === 'empty') {
    return Promise.resolve([]);
  }

  if (formData.status === 'exception') {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        reject(new Error('data exception'));
      }, 1000);
    });
  }

  return Promise.resolve([]);
};

const defaultColumnWidth = {
  name: 300,
  ext: 900,
  operation: 300
};

const GroupManageTable = () => {
  const [state, setState] = useSetState({
    columnWidth: defaultColumnWidth,
    optCol: null,
    actionType: 'preview',
    actionVisible: false
  });
  const {
    actionVisible,
    columnWidth,
    optCol
  } = state;
  const field = Field.useField([]);
  const {
    paginationProps,
    tableProps,
    search,
    error,
    refresh
  } = useFusionTable(getTableData, {
    field
  });
  const {
    reset
  } = search;

  const onResizeChange = (dataIndex, width) => {
    const newWidth = {
      ...columnWidth
    };
    newWidth[dataIndex] += width;
    setState({
      columnWidth: newWidth
    });
  };

  const operationCallback = useCallback(({
    actionType,
    dataSource
  }) => {
    setState({
      actionType,
      optCol: dataSource,
      actionVisible: true
    });
  }, [setState]);
  const handleCancel = useCallback(() => {
    setState({
      actionVisible: false
    });
  }, [setState]);
  const handleOk = useCallback(data => {
    const {
      actionType
    } = state;

    if (actionType === 'preview') {
      handleCancel();
      return;
    }

    if (actionType === 'edit') {
      groupService.update(data).then(function () {
        Message.success(`${data.groupName} 编辑成功!`);
      }).catch(function () {
        Message.error('编辑失败!');
      });
    }

    if (actionType === 'add') {
      groupService.insert(data).then(function () {
        Message.success('插入成功!');
      }).catch(function () {
        // TODO: duplicate key
        Message.error('插入失败!');
      });
    }

    reset();
    handleCancel();
  }, [handleCancel, reset, state]);
  const handleDelete = useCallback(data => {
    if (!data) {
      return;
    }

    Dialog.confirm({
      title: '删除提醒',
      content: `确定删除 ${data.groupName} 吗`,

      onOk() {
        groupService.delete(data.groupId).then(function () {
          Message.success(`${data.groupName} 删除成功!`);
        }).catch(function () {
          Message.error('删除失败!');
        });
        reset();
      }
    });
  }, [reset]);

  const cellOperation = (...args) => {
    const record = args[2];
    return <div>
      <Button text type="primary" onClick={() => operationCallback({
        actionType: 'edit',
        dataSource: record
      })}>
        编辑
      </Button>
      &nbsp;&nbsp;
      <Button text type="primary" onClick={() => handleDelete(record)}>
        删除
      </Button>
      &nbsp;&nbsp;
      <Button text type="primary" onClick={() => operationCallback({
        actionType: 'preview',
        dataSource: record
      })}>
        查看
      </Button>
    </div>;
  };

  return <div className={styles.DialogTable}>

    <Card free>
      <div role="grid">
        <Row justify='end'>
          <Col xxs={17} xs={17} s={21} xl={22} />
          <Col xxs={7} xs={7} s={3} xl={2}>
            <Button type="primary"
              style={{ marginTop: '20px' }}
              onClick={() => operationCallback({
                actionType: 'add',
                dataSource: {}
              })}>
              新增
            </Button>
          </Col>

        </Row>
      </div>

      <Divider />
      <Card.Content>
        <Table {...tableProps} onResizeChange={onResizeChange} emptyContent={error ? <ExceptionBlock onRefresh={refresh} /> : <EmptyBlock />} primaryKey="name">
          <Table.Column title="组名" dataIndex="groupName" resizable width={columnWidth.name} />
          <Table.Column title="其他信息" dataIndex="groupExtInfo" resizable width={columnWidth.ext} />
          <Table.Column title="操作" resizable width={columnWidth.operation} cell={cellOperation} />
        </Table>
        <Pagination style={{
          marginTop: 16,
          textAlign: 'right'
        }} totalRender={total => <>共 <Button text type="primary">{total}</Button> 个记录</>} {...paginationProps} />
      </Card.Content>
    </Card>
    <DialogOperation visible={actionVisible} actionType={state.actionType} dataSource={optCol} onOk={handleOk} onClose={handleCancel} onCancel={handleCancel} />
  </div>;
};

export default GroupManageTable;