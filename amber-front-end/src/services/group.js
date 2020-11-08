import { request } from 'ice';

export default {

    async getAllGroups(){
        return await request({
            url: '/group/all',
            method: 'get'
        });
    },

    async getGroupList(pageIndex, pageSize){
        return await request({
            url: '/group/list',
            method: 'get',
            params:{
                pageIndex: pageIndex,
                pageSize: pageSize
            }
        });
    },
    async insert(data){
        return await request({
            url: `/group/insert`,
            method: 'post',
            data: {
                name: data.name,
                ext: data.ext
            }
        });
    },
    async update(data){
        return await request({
            url: `/group/update/${data.groupId}`,
            method: 'post',
            data: {
                name: data.name,
                ext: data.ext
            }
        });
    },
    async delete(groupId){
        return await request({
            url: `/group/delete/${groupId}`,
            method: 'post'
        });
    }
}