import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        used: [],
        canUse: 0,
        username: 'initname',
        page:-1,
        pagename:[],
        result_name: [],
        result_infor: [],
        loginStatus: Boolean(sessionStorage.getItem('loginStatus')) || false,
        basicUrl: 'http://127.0.0.1:3000',
        getAllTechUrl: 'http://192.168.1.118:8080/database/neo4j/findTechnologyAll', // 获取所有技术标签（扁平化）
        getAllNewsUrl: 'http://192.168.1.118:8080/database/neo4j/findNewsAll', // 获取所有新闻标签
        getAllCompanyUrl: 'http://192.168.1.118:8080/database/neo4j/findCompanysAll' // 获取所有企业信息
    },
    mutations: {
        login: (state, payload) => {
            const {
                userId,
                userName
            } = payload;
            state.loginStatus = true;
            sessionStorage.setItem('loginStatus', true)
            sessionStorage.setItem('userId', userId)
            sessionStorage.setItem('username', userName)
        },
        logout: (state) => {
            state.loginStatus = false;
            sessionStorage.clear();
        }
    }
})