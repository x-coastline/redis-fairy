import * as React from 'react';
import { createApp, config, request } from 'ice';
import LocaleProvider from '@/components/LocaleProvider';
import Exception from '@/components/Exception';
import { getLocale } from '@/utils/locale';
import Cookies from 'universal-cookie';
import { createBrowserHistory } from 'history';

const locale = getLocale();
const cookies = new Cookies();
const history = createBrowserHistory();

const redirectToLogin = () => {
  if (history.location.pathname !== '/user/login') { history.push('/user/login'); }
}

const appConfig = {
  router: {
    type: 'browser',
  },
  app: {
    rootId: 'amber-container',
    // addProvider: ({ children }) => <LocaleProvider locale={locale}>{children}</LocaleProvider>,
    // getInitialData: async () => {

    //   let authData;
    //   const token = cookies.get('token');

    //   if (!token) {
    //     redirectToLogin();
    //   }

    //   console.log(token);

    //   // 模拟服务端返回的数据
    //   await request({
    //     url: '/user/role',
    //     method: 'get',
    //     headers: { Authorization: cookies.get('token') }
    //   }).then(function (res) {

    //     if (!res.success) {
    //       redirectToLogin();
    //     }
    //     // 约定权限必须返回一个 auth 对象
    //     // 返回的每个值对应一条权限
    //     authData = {
    //       auth: {
    //         admin: res.data === 'ADMIN',
    //         group_manager: res.data === 'GROUP_MANAGER',
    //         group_member: res.data === 'GROUP_MEMBER'
    //       }
    //     }
    //   }).catch(function (error) {
    //     redirectToLogin();
    //   });

    //   return authData;
    // },
  },
  // auth: {
  //   // 可选的，设置无权限时的展示组件，默认为 null
  //   NoAuthFallback: <Exception
  //     statusCode='401'
  //     description='you do not have that permission'
  //     image='https://img.alicdn.com/tfs/TB11TaSopY7gK0jSZKzXXaikpXa-200-200.png'
  //   />
  // },
  // https://ice.work/docs/guide/basic/request
  request: [{

    // 可选的，全局设置 request 是否返回 response 对象，默认为 false
    withFullResponse: false,

    baseURL: config.baseURL,
    headers: {
      Authorization: cookies.get('token')
    },
    // ...RequestConfig 其他参数

    // 拦截器
    interceptors: {
      request: {
        onConfig: (config) => {
          // 发送请求前：可以对 RequestConfig 做一些统一处理
          //config.headers = { 'Access-Control-Allow-Origin': '*' };
          return config;
        },
        onError: (error) => {
          return Promise.reject(error);
        }
      },
      response: {
        onConfig: (response) => {
          // 请求成功：可以做全局的 toast 展示，或者对 response 做一些格式化
          // if (!response.data.status !== 1) {
          //   alert('请求失败');
          // }
          return response;
        },
        onError: (error) => {
          // 请求出错：服务端返回错误状态码
          console.log(error)
          return Promise.reject(error);
        }
      },
    }
  },
  {
    instanceName: 'noAuthRequest',
    baseURL: config.baseURL,
  }]
};
createApp(appConfig);
