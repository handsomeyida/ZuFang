import Vue from 'vue'
import Router from 'vue-router'
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading
/* layout */
import Layout from '../views/layout/Layout'

const _import = require('./_import_' + process.env.NODE_ENV)
Vue.use(Router)
export const constantRouterMap = [
  {path: '/login', component: _import('login/index'), hidden: true},
  {path: '/404', component: _import('404'), hidden: true},
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '首页',
    hidden: true,
    children: [{
      path: 'dashboard', component: _import('dashboard/index')
    }]
  }
]
export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})
export const asyncRouterMap = [
  {
    path: '/basics',
    component: Layout,
    redirect: '/basics/',
    meta: {title: '基础模块', icon: 'table'},
    menu: 'basics',
    children: [
      {
        path: 'backstageuser',
        component: _import('basics/user'),
        meta: {title: '后台用户管理', icon: 'user'},
        menu: 'backstageuser'
      },
      {
        path: 'role',
        component: _import('basics/role'),
        meta: {title: '菜单&权限管理', icon: 'check'},
        menu: 'menu'
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    redirect: '/user/',
    meta: {title: '用户管理', icon: 'tree'},
    children: [
      {
        path: 'user',
        component: _import('article/article'),
        meta: {title: '用户管理', icon: 'user'},
        menu: 'user'
      },
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/advert/',
    meta: {title: '广告管理', icon: 'tree'},
    children: [
      {
        path: 'advert',
        component: _import('advert/advert'),
        meta: {title: '广告管理', icon: 'new'},
        menu: 'advert'
      },
    ]
  },
  {
    path: '/labels',
    component: Layout,
    redirect: '/labels/',
    meta: {title: '平台管理', icon: 'house'},
    children: [
      {
        path: 'category',
        component: _import('labels/category'),
        meta: {title: '帖子类别管理', icon: 'server'}, menu: 'posts'
      },
      {
        path: 'label',
        component: _import('basics/role'),
        meta: {title: '帖子标签管理', icon: 'check'}, menu: 'posts'
      }
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/posts/',
    meta: {title: '帖子管理', icon: 'tree'},
    children: [
      {
        path: 'posts',
        component: _import('article/article'),
        meta: {title: '帖子管理', icon: 'contract'},
        menu: 'posts'
      },
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/statistics/',
    meta: {title: '统计模块', icon: 'tree'},
    children: [
      {
        path: 'statistics',
        component: _import('article/article'),
        meta: {title: '统计模块', icon: 'analysis'},
        menu: 'statistics'
      },
    ]
  },
  {
    path: '/message',
    component: Layout,
    redirect: '/message/',
    meta: {title: '信息管理', icon: 'mansion'},
    children: [
      {
        path: 'template',
        component: _import('template/MsgTemplate'),
        meta: {title: '模版管理', icon: 'folder'},
        menu: 'message'
      },
      {
        path: 'guide',
        component: _import('message/LeadingWords'),
        meta: {title: '引导语设置', icon: 'group'},
        menu: 'guide'
      },
      {
        path: 'groupsending',
        component: _import('message/AllSendMsg'),
        meta: {title: '群发系统消息', icon: 'email'},
        menu: 'groupsending'
      },
      {
        path: 'myMessage',
        component: _import('message/myMessage'),
        meta: {title: '收到的消息', icon: 'chat'},
        menu: 'myMessage'
      }
    ]
  },
  {path: '*', redirect: '/404', hidden: true}
]
