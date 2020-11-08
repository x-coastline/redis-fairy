import React, { useEffect, useImperativeHandle } from 'react';
import { Select, Form, Field, Input } from '@alifd/next';
const FormItem = Form.Item;
const formItemLayout = {
  labelCol: {
    span: 4
  },
  wrapperCol: {
    span: 20
  }
};

const Operation = (props, ref) => {
  const {
    actionType
  } = props;
  const dataSource = props.dataSource || {};
  const field = Field.useField([]);
  useEffect(() => {
    field.reset();

    if (dataSource) {
      const newValues = {
        name: dataSource.groupName,
        ext: dataSource.groupExtInfo,
        groupId: dataSource.groupId
      };
      field.setValues(newValues);
    }
  }, [field, dataSource]);
  useImperativeHandle(ref, () => {
    return {
      getValues(callback) {
        field.validate((errors, values) => {
          if (errors) {
            return;
          }

          callback(values);
        });
      }

    };
  });
  const isPreview = actionType === 'preview';
  return <>
      <Form isPreview={isPreview} fullWidth labelAlign={isPreview ? 'left' : 'top'} field={field} {...formItemLayout}>
        <FormItem label="组名:" required={!isPreview} requiredMessage="必填">
          <Input {...field.init('name')} />
        </FormItem>
        <FormItem label="其他信息:" required={false} requiredMessage="必填">
          <Input name="ext" />
        </FormItem>
      </Form>
    </>;
};

export default React.forwardRef(Operation);