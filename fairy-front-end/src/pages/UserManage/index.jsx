import React, { useState, useEffect } from 'react';
import UserManageTable from './components/UserManageTable';
import {
    Box,
    Card,
    Tag,
    ResponsiveGrid,
    Icon,
    Button,
    Dialog
  } from '@alifd/next';

const { Cell } = ResponsiveGrid;

const UserManage = () =>{
return (
        <>
        <ResponsiveGrid>
            <Cell colSpan={12}>
                <UserManageTable /> 
            </Cell>
        </ResponsiveGrid>
        </>
    );        
}

UserManage.pageConfig = {
   auth: ['admin']
};

export default UserManage;
