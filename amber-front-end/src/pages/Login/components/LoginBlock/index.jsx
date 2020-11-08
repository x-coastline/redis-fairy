import React, { useState, useEffect } from 'react';
import { Input, Message, Form, Button } from '@alifd/next';
import userService from '@/services/user';
import Cookies from 'universal-cookie';
import { request, history, useAuth } from 'ice';
import  { Redirect } from 'react-router-dom'
import styles from './index.module.scss';

const { Item } = Form;
const DEFAULT_DATA = {
  username: '',
  password: ''
};
const cookies = new Cookies();

const LoginBlock = (
  props = {
    dataSource: DEFAULT_DATA,
  },
) => {
  const { dataSource = DEFAULT_DATA } = props;
  const [postData, setValue] = useState(dataSource);
  const [oauthConfig, setOauthConfig] = useState({ enable: false, name: '', url: '' });
  const [auth] = useAuth();

  const formChange = values => {
    setValue(values);
  };

  const handleSubmit = (values, errors) => {
    if (errors) {
      return;
    }
    userService.login(values).then(function (res) {
      cookies.set('token', res.data.sessionId, { path: '/' });

      request({
        url: '/user/role',
        method: 'get',
        headers: { Authorization: res.data.sessionId }
      }).then(function () {

        if(!res.success){
          Message.error('登陆失败');
        }else{
          history.go(0);
        }
      }).catch(function () {
        Message.error('登陆失败');
      });

    }).catch(function (error) {
      Message.error(error.response.data.msg)
    });
    setValue({ username: '', password: '' });
  };

  const redirectToOAuthURL = () => {
    window.location = oauthConfig.url;
  }

  useEffect(() => {
    let isMounted = true;
    userService.authConfig().then(function (res) {
      if (res.code === 0 && res.data != null && isMounted) {
        setOauthConfig({
          enable: res.data.enable,
          name: res.data.name,
          url: res.data.url
        });
      }
    }).catch(function () {
      Message.error('internal server error')
    });
    return () => { isMounted = false };
  }, []);

  const accountForm = (
    <>
      <Item required requiredMessage="required">
        <Input name="username" maxLength={20} placeholder="username" />
      </Item>
      <Item
        required
        requiredMessage="required"
        style={{
          marginBottom: 0,
        }}
      >
        <Input.Password name="password" htmlType="password" placeholder="password" />
      </Item>
    </>
  );

  if(auth.admin){
    return <Redirect to='/admin/group/manage' />;
  }else if(auth.group_manager || auth.group_member ){
    return <Redirect to='/dashboard' />;
  }else{
    return (
      <div className={styles.LoginBlock}>
        <div className={styles.innerBlock}>
          <a href="#">
            <img
              className={styles.logo}
              src="https://img.alicdn.com/tfs/TB1KtN6mKH2gK0jSZJnXXaT1FXa-1014-200.png"
              alt="logo"
            />
          </a>
          <div className={styles.desc}>
            <span className={styles.active}>
              账户密码登录
            </span>
          </div>
  
          <Form value={postData} onChange={formChange} size="large">
            {accountForm}
  
            {/* <div className={styles.infoLine}>
              <Item
                style={{
                  marginBottom: 0,
                }}
              >
                <Checkbox name="autoLogin" className={styles.infoLeft}>
                  自动登录
                </Checkbox>
              </Item>
              <div>
                <a href="/" className={styles.link}>
                  忘记密码
                </a>
              </div>
            </div> */}
  
            <Item
              style={{
                marginTop: 20,
                marginBottom: 10,
              }}
            >
              <Form.Submit
                type="primary"
                onClick={handleSubmit}
                className={styles.submitBtn}
                validate
              >
                Login
              </Form.Submit>
            </Item>
  
            <Item>
              {oauthConfig.enable ? <div>
                <Button
                  type="primary"
                  size="large"
                  onClick={redirectToOAuthURL}
                  className={styles.submitBtn}
                >
                  Sign in with&nbsp;&nbsp;{oauthConfig.name}
                </Button>
              </div>
              : null}
            </Item>
          </Form>
        </div>
      </div>
    );
  }
};

export default LoginBlock;
