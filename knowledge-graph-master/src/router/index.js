import Vue from 'vue'
import Router from 'vue-router'
import MainPage from '@/pages/main/index'
import ProjectPage from '@/pages/main/project/project'
import DataPage from '@/pages/main/data/data'
import DataDetail from '@/pages/main/data/dataDetail'
import ModelPage from '@/pages/main/model/model'
import ModelDetailPage from '@/pages/main/model/modelDetail'
import GraphPage from '@/pages/main/graph/graph'
import GraphDetailPage from '@/pages/main/graph/graphDetail'
import TaskPage from '@/pages/main/task/task'
import TaskDetailPage from '@/pages/main/task/taskDetail'
import Pool from '@/pages/main/pool/pool'
import Company from '@/pages/main/pool/company'
import Intelproperty from '@/pages/main/pool/intelproperty'
import Expertgraph from '@/pages/main/pool/expertgraph'

Vue.use(Router)

export default new Router({
    routes: [{
            path: '/',
            name: 'main',
            component: MainPage
        }, {
            path: '/projectdetail',
            name: 'project',
            component: ProjectPage
        }, {
            path: '/data',
            name: 'data',
            component: DataPage
        },
        {
            path: '/datadetail',
            name: 'dataDetail',
            component: DataDetail
        },
        {
            path: '/model',
            name: 'model',
            component: ModelPage
        }, {
            path: '/modeldetail',
            name: 'modelDetail',
            component: ModelDetailPage
        }, {
            path: '/graph',
            name: 'graph',
            component: GraphPage
        }, {
            path: '/graphdetail',
            name: 'graphDetail',
            component: GraphDetailPage
        }, {
            path: '/task',
            name: 'task',
            component: TaskPage
        }, {
            path: '/taskdetail',
            name: 'taskDetail',
            component: TaskDetailPage
        }, {
            path: '/pool',
            name: 'pool',
            component: Pool
        }, {
            path: '/company',
            name: 'company',
            component: Company
        }, {
            path: '/intelproperty',
            name: 'intelproperty',
            component: Intelproperty
        }, {
            path: '/expertgraph',
            name: 'expertgraph',
            component: Expertgraph
        }
    ]
})