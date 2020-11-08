import UserLayout from '@/layouts/UserLayout';
import Login from '@/pages/Login';
import Register from '@/pages/Register';
import BasicLayout from '@/layouts/BasicLayout';

import Dashboard from '@/pages/Dashboard';
import Operation from '@/pages/Operation';
import GroupManage from '@/pages/GroupManage';
import UserManage from '@/pages/UserManage';

const routerConfig = [
  {
    path: '/user',
    component: UserLayout,
    children: [
      {
        path: '/login',
        component: Login,
      },
      {
        path: '/register',
        component: Register,
      },
      {
        path: '/',
        redirect: '/user/login',
      },
    ],
  },
  {
    path: '/admin',
    component: BasicLayout,
    children: [
      {
        path: '/group/manage',
        component: GroupManage,
      },
      {
        path: '/user/manage',
        component: UserManage,
      }
    ],
  },
  {
    path: '/',
    component: BasicLayout,
    children: [
      {
        path: '/dashboard',
        component: Dashboard,
      },
      {
        path: '/operation/:operation/:clusterId',
        component: Operation,
      }
      // {
      //   path: '/monitor',
      //   component: Monitor,
      // }
    ],
  }
];
export default routerConfig;
