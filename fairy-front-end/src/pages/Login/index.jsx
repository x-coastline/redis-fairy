import React, { useState, useEffect } from 'react';
import { getSearchParams, history } from 'ice';
import { ResponsiveGrid, Loading, Message } from '@alifd/next';
import LoginBlock from './components/LoginBlock';
import userService from '@/services/user';
import Cookies from 'universal-cookie';

const { Cell } = ResponsiveGrid;

const Login = () => {

  const [loading, setLoading] = useState(true);
  const { code } = getSearchParams();
  const cookies = new Cookies();

  useEffect(() => {
    if (code) {
      setLoading(true);

      userService.neweggLogin(code).then(function (res) {
        setLoading(false);
        if (res.success && res.data != null) {
          cookies.set('token', res.data.sessionId, { path: '/' });
          //domain account can not be admin.
          history.push('/dashboard');
        }
      }).catch(function (error) {
        setLoading(false);
        Message.error('auth server error')
      });

    }else{
      setLoading(false);
    }
  }, []);

  return (

    <ResponsiveGrid gap={20}>
      <Cell colSpan={12}>
        {
        loading ? 
        <span>
            <Loading
                fullScreen
                shape="fusion-reactor">
            </Loading>
        </span>
        :
        <LoginBlock />
        }
      </Cell>
    </ResponsiveGrid>
  )
};

export default Login;
