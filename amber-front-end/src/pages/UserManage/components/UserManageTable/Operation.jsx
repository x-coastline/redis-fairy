import React, { useEffect, useImperativeHandle } from 'react';
import { Select, Form, Field, Input, Upload } from '@alifd/next';
import { config } from 'ice';
import Img from '@icedesign/img';

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
  const isPreview = actionType === 'preview';
  const isAdd = actionType === 'add';
  const dataSource = props.dataSource || {};
  const field = Field.useField([]);
  useEffect(() => {
    field.reset();

    if (dataSource) {
      const newValues = {
        userId: dataSource.userId,
        userName: dataSource.userName,
        groupUserRole: dataSource.groupUserRole ? 
          dataSource.groupUserRoles.find(role => role.label === dataSource.groupUserRole).value :
          undefined ,
        email: dataSource.email,
        mobile: dataSource.mobile
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
  return <>
    <Form isPreview={isPreview} fullWidth labelAlign={isPreview ? 'left' : 'top'} field={field} {...formItemLayout}>
      {
        isAdd ?
          <FormItem label="是否为管理员" required requiredMessage="必填">
            <Select name="admin" dataSource={[{
              label: '是',
              value: true
            }, {
              label: '否',
              value: false
            }]} />
          </FormItem>
          :
          null
      }
      <FormItem label="用户名:" required={!isPreview} requiredMessage="必填">
        <Input {...field.init('userName')} />
      </FormItem>
      {
        isAdd ?
          <FormItem label="密码" required requiredMessage="必填">
            <Input.Password name="password"  />
          </FormItem>
          :
          null
      }
      {
        isPreview ?
          <FormItem label="头像:" required={false} requiredMessage="必填">
            <Img
              width={100}
              height={100}
              src={dataSource.avatar}
            />
          </FormItem>
          :
          <FormItem label="上传:">
            <Upload.Card
              autoUpload={false}
              useDataURL
              listType="card"
              action={`${config.baseURL}/avatar/upload`}
              accept="image/png, image/jpg, image/jpeg, image/gif, image/bmp"
              limit={1}
              name="file"
              formatter={(res) => {
                return {
                  success: res.success === true,
                  url: `${config.baseURL}/avatar/${res.id}`,
                }
              }}
            />
          </FormItem>
      }
      {
        isAdd ?
          <FormItem label="所在组:" required requiredMessage="必填">
            <Select name="groupId" dataSource={dataSource.groups} />
          </FormItem>
          :
          null
      }
      {
        isPreview ? 
          <FormItem label="组角色:" required={false} >
            <Input name="groupUserRole" value={dataSource.groupUserRole} />
          </FormItem>
          :
          <FormItem label="组角色:" required requiredMessage="必填">
            <Select name="groupUserRole" dataSource={dataSource.groupUserRoles} />
          </FormItem>
      }
      <FormItem label="Email:" required={false} >
        <Input name="email" />
      </FormItem>
      <FormItem label="手机号:" required={false} >
        <Input name="mobile" />
      </FormItem>
    </Form>
  </>;
};

export default React.forwardRef(Operation);