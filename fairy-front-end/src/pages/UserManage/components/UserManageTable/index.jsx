import React, { useCallback, useEffect } from 'react';
import { Button, Select, Form, Field, Table, Card, Pagination, Message, Dialog } from '@alifd/next';
import { useFusionTable, useSetState } from 'ahooks';
import userService from '@/services/user';
import groupService from '@/services/group';
import { config } from 'ice';
import Img from '@icedesign/img';
import EmptyBlock from './EmptyBlock';
import ExceptionBlock from './ExceptionBlock';
import DialogOperation from './DialogOperation';
import styles from './index.module.scss';

const FormItem = Form.Item;
const urlpattern = new RegExp('^(?!mailto:)(?:(?:http|https|ftp)://|//)(?:\\S+(?::\\S*)?@)?(?:(?:(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[0-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,})))|localhost)(?::\\d{2,5})?(?:(/|\\?|#)[^\\s]*)?$', 'i');

const defaultGroupUserRoleData = [{
  label: 'GROUP_MANAGER',
  value: 0
}, {
  label: 'GROUP_MEMBER',
  value: 1
}];

const getTableData = ({
  current,
  pageSize
}, formData) => {
  return userService.adminGetUserList({
    groupId: formData.groupId,
    admin: formData.admin,
    pageIndex: current,
    pageSize
  }).then(res => ({
    total: res.data.total,
    list: res.data.users.slice(0, 10).map(x => {
      const user = { ...x };
      if (!urlpattern.test(x.avatar)) {
        user.avatar = `${config.baseURL}/user/avatar/${x.avatar}`;
      }
      user.groupUserRole = x.groupUserRole ? defaultGroupUserRoleData.find(role => role.value === x.groupUserRole).label : null
      return user;
    })
  }));
};

const defaultColumnWidth = {
  userName: 300,
  avatar: 300,
  groupUserRole: 200,
  operation: 440
};

const UserManageTable = () => {

  const [state, setState] = useSetState({
    columnWidth: defaultColumnWidth,
    optCol: null,
    actionType: 'preview',
    actionVisible: false,
    selectAdmin: false,
    groups: []
  });
  const {
    actionVisible,
    columnWidth,
    optCol,
    selectAdmin,
    groups
  } = state;
  const field = Field.useField([]);
  const {
    paginationProps,
    tableProps,
    search,
    error,
    refresh
  } = useFusionTable(getTableData, {
    field,
    defaultParams: [
      { current: 1, pageSize: 10 },
      { admin: false },
    ],
  });

  const {
    submit,
    reset
  } = search;

  const operationCallback = useCallback(({
    actionType,
    dataSource
  }) => {
    setState({
      actionType,
      optCol: Object.assign(dataSource, {groupUserRoles: defaultGroupUserRoleData}),
      actionVisible: true
    });
  }, [setState]);
  const renderAvatar = (value, index, record) => {
    return (
      <div>
        <Img
          width={100}
          height={100}
          src={record.avatar}
          type='contain'
        />
      </div>
    );
  }
  const handleCancel = useCallback(() => {
    setState({
      actionVisible: false
    });
  }, [setState]);
  const onAdminSelectChange = useCallback(data => {
    setState({
      selectAdmin: data
    });
  }, [setState]);
  const onGroupSelectChange = useCallback(data => {
    setState({
      tempGroup: data
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
      userService.adminUpdateUser(Object.assign(data, { groupId: field.getValue('groupId') })).then(function () {
        Message.success('编辑成功!');
        refresh();
      }).catch(function () {
        Message.error('编辑失败!');
      });
    }

    if (actionType === 'add') {
      userService.adminUpdateUser(Object.assign(data)).then(function () {
        Message.success('新增成功!');
        refresh();
      }).catch(function () {
        Message.error('新增失败!');
      });
    }
    handleCancel();
  }, [field, handleCancel, refresh, state]);
  const handleDelete = useCallback(data => {
    if (!data) {
      return;
    }

    Dialog.confirm({
      title: '删除提醒',
      content: `确定删除 ${data.userName} 吗`,

      onOk() {
        userService.deleteUser(data.userId).then(function(){
          Message.success(`用户${data.userName} 删除成功!`);
          refresh();
        }).catch(function(){
          Message.error('删除失败!');
        })
      }
    });
  }, [refresh]);

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

  const onResizeChange = (dataIndex, width) => {
    const newWidth = { ...columnWidth };
    newWidth[dataIndex] += width;
    setState({
      columnWidth: newWidth,
    });
  };

  useEffect(() => {
    let isMounted = true;
    groupService.getAllGroups().then(function (res) {
      if (res.success === true && res.data != null && isMounted) {
        setState({
          groups: res.data.map(x => {
            return {
              label: x.groupName,
              value: x.groupId
            }
          })
        });
      }
    }).catch(function () {
      Message.error('internal server error')
    });
    return () => { isMounted = false };
  }, [setState]);

  return <div className={styles.FilterTable}>
    <Card free>
      <Card.Content>
        <Form className="filter-form" responsive fullWidth labelAlign="top" field={field}>
          <FormItem colSpan={2} label="是否获取管理员" required requiredMessage="必填">
            <Select name="admin" onChange={onAdminSelectChange} dataSource={[{
              label: '是',
              value: true
            }, {
              label: '否',
              value: false
            }]} />
          </FormItem>
          {
            selectAdmin ? <FormItem colSpan={8} /> :
            <>
              <FormItem colSpan={2} label="获取哪个组的" required requiredMessage="必填">
                <Select name="groupId" onChange={onGroupSelectChange} dataSource={groups} />
              </FormItem>
              <FormItem colSpan={6} />
            </>
          }

          <FormItem colSpan={1} style={{
            display: 'flex',
            alignItems: 'center'
          }}>
            <Form.Submit type="primary" onClick={submit} validate>
              筛选
            </Form.Submit>
          </FormItem>
          <FormItem colSpan={1} style={{
            display: 'flex',
            alignItems: 'center'
          }}>
            <Form.Submit type="primary" onClick={() => operationCallback({
              actionType: 'add',
              dataSource: {groups}
            })} validate>
              新增用户
            </Form.Submit>
          </FormItem>
        </Form>
      </Card.Content>
    </Card>
    <Card free>
      <Card.Content>
        <Table {...tableProps} onResizeChange={onResizeChange} emptyContent={error ? <ExceptionBlock onRefresh={refresh} /> : <EmptyBlock />} primaryKey="userName">
          <Table.Column title="用户名" dataIndex="userName" resizable width={columnWidth.userName} />
          <Table.Column title="头像" cell={renderAvatar} resizable width={columnWidth.avatar} />
          {
            selectAdmin ? null :
            <Table.Column title="组角色" dataIndex="groupUserRole" resizable width={columnWidth.groupUserRole} />
          }
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

export default UserManageTable;