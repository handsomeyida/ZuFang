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
    name: '基础模块',
    meta: {title: '基础模块', icon: 'table'},
    children: [
      {
        path: '',
        name: '后台用户管理',
        component: _import('basics/user'),
        meta: {title: '后台用户管理', icon: 'user'}, menu: 'backstageuser'
      },
      {
        path: 'role',
        name: '菜单管理',
        component: _import('basics/role'),
        meta: {title: '菜单&权限管理', icon: 'check'}, menu: 'menu'
      }
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/article',
    name: '用户管理',
    meta: {title: '用户管理', icon: 'tree'},
    children: [
      {
        path: 'user',
        name: '用户管理',
        component: _import('article/article'),
        meta: {title: '用户管理', icon: 'user'},
        menu: 'user'
      },
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/advert',
    name: '广告管理',
    meta: {title: '广告管理', icon: 'tree'},
    children: [
      {
        path: 'advert',
        name: '广告管理',
        component: _import('advert/advert'),
        meta: {title: '广告管理', icon: 'new'},
        menu: 'advert'
      },
    ]
  },
  {
    path: '/car',
    component: Layout,
    redirect: '/car/',
    name: '平台管理',
    meta: {title: '平台管理', icon: 'house'},
    children: [
      {
        path: '',
        name: '帖子类别管理',
        component: _import('basics/user'),
        meta: {title: '帖子类别管理', icon: 'server'}, menu: 'user'
      },
      {
        path: 'label',
        name: '帖子标签管理',
        component: _import('basics/role'),
        meta: {title: '帖子标签管理', icon: 'check'}, menu: 'user'
      }
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/article',
    name: '帖子管理',
    meta: {title: '帖子管理', icon: 'tree'},
    children: [
      {
        path: 'posts',
        name: '帖子管理',
        component: _import('article/article'),
        meta: {title: '帖子管理', icon: 'contract'},
        menu: 'posts'
      },
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/article',
    name: '统计模块',
    meta: {title: '统计模块', icon: 'tree'},
    children: [
      {
        path: 'statistics',
        name: '统计模块',
        component: _import('article/article'),
        meta: {title: '统计模块', icon: 'analysis'},
        menu: 'statistics'
      },
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/',
    name: '系统信息',
    meta: {title: '系统信息', icon: 'mansion'},
    children: [
      {
        path: 'template',
        name: '模版管理',
        component: _import('basics/user'),
        meta: {title: '模版管理', icon: 'folder'}, menu: 'template'
      },
      {
        path: 'guide',
        name: '引导语设置',
        component: _import('basics/user'),
        meta: {title: '引导语设置', icon: 'group'}, menu: 'guide'
      },
      {
        path: 'groupsending',
        name: '群发系统消息',
        component: _import('basics/user'),
        meta: {title: '群发系统消息', icon: 'email'}, menu: 'groupsending'
      }
    ]
  },
  {path: '*', redirect: '/404', hidden: true}
]
