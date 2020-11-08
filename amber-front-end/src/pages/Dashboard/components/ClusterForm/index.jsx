import React from 'react';
import {
  Form,
  Field,
  Input,
  Radio,
  Tag
} from '@alifd/next';

import { isNotEmpty, validateIpAndPort } from '@/utils/validate';

import { store as dashboardStore } from 'ice/Dashboard'

const RadioGroup = Radio.Group;
const FormItem = Form.Item;

const ENVIRONMENT = [
  {
    label: 'Docker',
    value: 'DOCKER',
  },
  {
    label: 'Machine',
    value: 'MACHINE',
  },
];

const ClusterForm = props => {

  const { actionType, onFormChange } = props;

  const dashboardDispatcher = dashboardStore.useModelDispatchers('dashboard');

  const field = Field.useField(this);
  // const field = new Field(this);
  // 是否是查看模式
  const isPreview = actionType === 'preview';

  const formChange = values => {
    onFormChange(values)
  };

  // 表单校验相关
  const clusterNameExists = (rule, value) => {
    return new Promise((resolve, reject) => {
      if (value === 'test') {
        // eslint-disable-next-line prefer-promise-reject-errors
        reject([new Error('Sorry, this cluster name is already exist.')]);
      } else {
        resolve();
      }
    });
  }

  const checkSeed = (rule, value) => {
    return new Promise((resolve, reject) => {
      let validateSeed = true;
      const ipAndPortArr = value.split(',')
      ipAndPortArr.forEach(ipAndPort => {
        if (isNotEmpty(ipAndPort.trim()) && !validateIpAndPort(ipAndPort.trim())) {
          validateSeed = false;
        }
      })
      if (!validateSeed) {
        // eslint-disable-next-line prefer-promise-reject-errors
        reject([new Error('The format of the node is incorrect')]);
      } else {
        resolve();
      }
    });
  }

  // 提交表单
  const handleSubmit = (form, e) => {
    if (e == null) {
      // eslint-disable-next-line no-param-reassign
      form.groupId = 1;
      dashboardDispatcher.addCluster(form)
      console.log(form);
      // onClose()
    } else {
      console.log(e)
    }
  };

  return (

    <Form
      // input 是否可写
      isPreview={isPreview}
      fullWidth
      labelAlign={isPreview ? 'left' : 'top'}
      field={field}
      onChange={formChange}
    >
      <FormItem
        label="Current Group:"
      >
        <Tag size="small" color="blue">Bigdata</Tag>
      </FormItem>
      <FormItem
        label="Cluster Name:"
        hasFeedback
        required
        requiredMessage='Please input cluster name'
        // eslint-disable-next-line react/jsx-no-bind
        validator={clusterNameExists.bind(this)}
      >
        {/* <Input {...field.init('name')} /> */}
        <Input name="clusterName" maxLength={50} placeholder="Input cluster name" />
      </FormItem>
      <FormItem
        label="Seed Nodes:"
        hasFeedback
        required
        requiredMessage="Please input redis seed nodes"
        // eslint-disable-next-line react/jsx-no-bind
        validator={checkSeed.bind(this)}
      >
        <Input name="seed" placeholder="192.168.5.2:8000,192.168.5.3:8000" />
      </FormItem>
      <FormItem label="Redis Password:" >
        <Input htmlType="password" name="redisPassword" />
      </FormItem>
      <FormItem
        label="Environment:"
        hasFeedback
        required
        requiredMessage="Please select redis running environment"
      >
        <RadioGroup name="environment" dataSource={ENVIRONMENT} />
      </FormItem>
      <FormItem label="Info:">
        <Input name="info" maxLength={255} />
      </FormItem>

      <FormItem style={{ textAlign: 'right' }}>
        <Form.Submit validate type="primary" onClick={(v, e) => handleSubmit(v, e)} style={{ marginRight: 10 }}>Submit</Form.Submit>
        <Form.Reset>Reset</Form.Reset>
      </FormItem>

    </Form>
  );
};

export default ClusterForm;
