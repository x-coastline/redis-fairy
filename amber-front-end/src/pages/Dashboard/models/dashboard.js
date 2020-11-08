/* eslint-disable indent */
import clusterService from '@/services/cluster';

export default {
    state: {
        statisticsCardList: [],
        clusterList: [],
        clusterFormVisible: false,
        clusterForm: {}
    },

    reducers: {
        updateClusterList(prevState, payload) {
            return { ...prevState, ...payload };
            // Object.assign(prevState, payload);
        },
        updateStatisticsList(prevState, payload) {
            return { ...prevState, ...payload };
            // Object.assign(prevState, payload);
        },
        updateClusterFormVisible(prevState, payload) {
            return { ...prevState, ...payload };
            // Object.assign(prevState, payload);
        },
        updateClusterForm(prevState, payload) {
            return { ...prevState, ...payload };
            // Object.assign(prevState, payload);
        },
    },
    effects: () => ({
        async fetchClusterList(groupId) {
            const res = await clusterService.getClusterList(groupId)
            //  if (res.status === 'SUCCESS') {
            //      dispatch.user.update(res.data);
            //  }
            console.log(res)
            this.updateClusterList({
                clusterList: res.data
            });
        },
        async fetchStatisticsList() {
            const res = await clusterService.getStatisticsList('12345')
            //  if (res.status === 'SUCCESS') {
            //      dispatch.user.update(res.data);
            //  }
            this.updateStatisticsList({
                statisticsCardList: res
            });
        },
        async setClusterFormVisible(visible) {
            this.updateClusterFormVisible({
                clusterFormVisible: visible
            });
        },
        async setClusterForm(form) {
            this.updateClusterForm({
                clusterForm: form
            });
        },
        async addCluster(form) {
            console.log(form);
            await clusterService.addCluster(form, (result) => {
                console.log(result)
            })
        },

    }),
};
