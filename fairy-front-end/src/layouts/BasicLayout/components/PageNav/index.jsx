import React from 'react';
import PropTypes from 'prop-types';
import { Link, withRouter, useAuth } from 'ice';
import { Nav } from '@alifd/next';

const { SubNav } = Nav;
const NavItem = Nav.Item;

function getNavMenuItems(menusData, initIndex) {
  if (!menusData) {
    return [];
  }

  return menusData
    .filter(item => item.name && !item.hideInMenu)
    .map((item, index) => getSubMenuOrItem(item, `${initIndex}-${index}`));
}

function getSubMenuOrItem(item, index) {
  if (item.children && item.children.some(child => child.name)) {
    const childrenItems = getNavMenuItems(item.children, index);

    if (childrenItems && childrenItems.length > 0) {
      const subNav = (
        <SubNav key={index} icon={item.icon} label={item.name}>
          {childrenItems}
        </SubNav>
      );
      return subNav;
    }

    return null;
  }

  const navItem = (
    <NavItem key={item.path} icon={item.icon}>
      <Link to={item.path}>{item.name}</Link>
    </NavItem>
  );
  return navItem;
}

const Navigation = (props, context) => {
  const { location } = props;
  const { pathname } = location;
  const { isCollapse } = context;

  const [auth, setAuth] = useAuth();
  const asideMenuConfig = [];
  // for develop
  asideMenuConfig.push({
    name: 'Dashboard',
    path: '/dashboard',
    icon: 'chart-pie',
  });

  if (auth.admin) {
    asideMenuConfig.push({
      name: 'GroupManage',
      path: '/admin/group/manage',
      icon: 'account',
    });
    asideMenuConfig.push({
      name: 'UserManage',
      path: '/admin/user/manage',
      icon: 'account',
    });
  } else if (auth.group_manager) {
    asideMenuConfig.push({
      name: 'Dashboard',
      path: '/dashboard',
      icon: 'chart-pie',
    });
    asideMenuConfig.push({
      name: 'System',
      path: '/system',
      icon: 'set',
      children: [
        {
          name: 'Node',
          path: '/node',
        }
      ]
    });
  } else if (auth.group_member) {
    asideMenuConfig.push({
      name: 'Dashboard',
      path: '/dashboard',
      icon: 'chart-pie',
    });
  }

  return (
    <Nav
      type="normal"
      selectedKeys={[pathname]}
      defaultSelectedKeys={[pathname]}
      embeddable
      activeDirection="right"
      openMode="single"
      iconOnly={isCollapse}
      hasArrow={false}
      mode={isCollapse ? 'popup' : 'inline'}
    >
      {getNavMenuItems(asideMenuConfig, 0)}
    </Nav>
  );
};

Navigation.contextTypes = {
  isCollapse: PropTypes.bool,
};
const PageNav = withRouter(Navigation);
export default PageNav;
