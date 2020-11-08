import React, { useState, useEffect } from 'react';
import GroupManageTable from './components/GroupManageTable';
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

const GroupManage = () =>{
return (
        <>
        <ResponsiveGrid>
            <Cell colSpan={12}>
                <GroupManageTable /> 
            </Cell>
        </ResponsiveGrid>
        </>
    );        
}

GroupManage.pageConfig = {
   auth: ['admin']
};

export default GroupManage;
