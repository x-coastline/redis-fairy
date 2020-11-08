import { request } from 'ice';

export default {

  async login(data){
    return await request({
      url: '/user/login',
      method: 'post',
      data:{
        username: data.username,
        password: data.password
      }
    });
  },

  async getRole(){
    return await request({
      url: '/user/role',
      method: 'get'
    });
  },

  async authConfig(){
    return await request({
      url: '/user/oauth/config',
      method: 'get',
      instanceName: 'noAuthRequest'
    });
  },

  async neweggLogin(code){
    return await request({
      url: '/user/neweggLogin',
      method: 'post',
      data:{
        code
      }
    });
  },

  async getUserList(data){
    return await request({
      url: '/user/getUserList',
      method: 'get',
      params:{
        groupId: data.groupId,
        pageIndex: data.pageIndex,
        pageSize: data.pageSize
      }
    });
  },

  async adminGetUserList(data){
    return await request({
      url: '/user/admin/getUserList',
      method: 'get',
      params:{
        groupId: data.groupId,
        admin: data.admin,
        pageIndex: data.pageIndex,
        pageSize: data.pageSize
      }
    });
  },

  async adminUpdateUser(data){
    // eslint-disable-next-line global-require
    const FormData = require('form-data');
    // var fs = require('fs');
    const formData = new FormData();
    // formData.append('file', fs.createReadStream(data.file));
    if(data.file !== undefined && data.file !== null){
      formData.append('file', data.file[0].originFileObj);
    }
    if(data.admin !== undefined && data.admin !== null){
      formData.append('admin', data.admin);
    }
    if(data.password !== undefined && data.password !== null){
      formData.append('password', data.password);
    }
    formData.append('groupId', data.groupId);
    if(data.userId !== undefined){
      formData.append('userId', data.userId);
    }
    formData.append('userName', data.userName);
    if(data.email !== undefined && data.email !== null){
      formData.append('email', data.email);
    }
    if(data.mobile !== undefined && data.mobile !== null){
      formData.append('mobile', data.mobile);
    }
    formData.append('groupUserRole', data.groupUserRole);

    return await request({
      url: '/user/admin/updateUser',
      method: 'post',
      data: formData,
      headers: {
        'content-type': 'multipart/form-data'
      }
    });
  },

  async deleteUser(userId){
    return await request({
      url: '/user/admin/deleteUser',
      method: 'post',
      data:{
        userId
      }
    });
  },
}
