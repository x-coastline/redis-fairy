import React, { useCallback } from 'react';
import { Dialog, Form, Input, Select } from '@alifd/next';

const FormItem = Form.Item;

const getDialogTitle = (metaType, record) => {
  switch (metaType) {
    case 'Info':
    default:
      return 'Info';
    case 'Config':
      return 'Config';
  }
};

const receivingNode='10.16.50.222:8888';

const dataSource = [
  { value: '10.16.50.223:8008', label: '10.16.50.223:8008' },
  { value: '10.16.50.224:8008', label: '10.16.50.224:8008' },
  { value: '10.16.50.223:8008', label: '10.16.50.223:8008' },
  { value: '10.16.50.224:8008', label: '10.16.50.224:8008' }
];

const DialogOperation = props => {
  const { metaType, record, ...lastProps } = props;

  // const handleSubmit = (values) => {
  //   console.log('Get form value:', values);
  // };

  let node = '';
  if (record) {
    node = record.host + ":" + record.port;
  }

  const formItemLayout = {
    labelCol: {
      fixedSpan: 10
    },
    wrapperCol: {
      span: 14
    }
  };

  function handleChange(value) {
    console.log(value);
  }

  return (
    <Dialog
      // visible
      shouldUpdatePosition
      isFullScreen
      title='MoveSlot'
      style={{
        width: 600,
      }}
      footer={false}
      {...lastProps}
    >
      <Form style={{ width: '60%' }} {...formItemLayout} >
        <FormItem label="Receiving Node:">
          <Input htmlType="text" name="receivingNode" value={node} disabled />
        </FormItem>
        <FormItem label="Source Nodes:">
          <Select hasSelectAll tagInline mode="multiple" showSearch  onChange={handleChange} dataSource={dataSource} style={{ width: 300 }} />
          {/* <Input htmlType="password" name="sourceNodes" placeholder="Please Enter Password" /> */}
        </FormItem>
        <FormItem label="Move Slot Count:">
          <Input htmlType="text" name="moveSlotCount" placeholder="1000" />
        </FormItem>
        {/* <FormItem label="Agreement:">
          <Checkbox name="baseAgreement" defaultChecked>Agree</Checkbox>
        </FormItem> */}
        <FormItem label=" ">
          <Form.Submit>Confirm</Form.Submit>
        </FormItem>
      </Form>
    </Dialog>
  );
};

export default DialogOperation;