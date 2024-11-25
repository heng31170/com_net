// store.js
import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        curUser: JSON.parse(sessionStorage.getItem('curUser')) || null, // 从 localStorage 初始化
    },
    mutations: {
        setCurUser(state, user) {
            state.curUser = user;
            sessionStorage.setItem('curUser', JSON.stringify(user)); // 保存到 localStorage
        }
    },
    actions: {
        updateCurUser({ commit }, user) {
            commit('setCurUser', user);
        }
    },
    getters: {
        getCurUser: state => state.curUser,
    }
});