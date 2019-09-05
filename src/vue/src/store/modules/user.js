import {getInfo, login, logout} from '@/api/login'
import {getToken, removeToken, setToken} from '@/utils/auth'
import {default as api} from '../../utils/api'
import store from '../../store'
import router from '../../router'

const user = {
  state: {
    nickname: "",
    userId: "",
    // avatar: 'https://www.gravatar.com/avatar/6560ed55e62396e40b34aac1e5041028',
    avatar: 'http://b-ssl.duitang.com/uploads/item/201805/13/20180513224039_tgfwu.png',
    role: '',
    menus: [],
    permissions: [],
    badId: '10009',
    badname: '傻屌'
  },
  mutations: {
    SET_USER: (state, userInfo) => {
      state.nickname = userInfo.nickname;
      state.userId = userInfo.userId;
      state.role = userInfo.roleName;
      state.menus = userInfo.menuList;
      state.permissions = userInfo.permissionList;
    },
    RESET_USER: (state) => {
      state.nickname = "";
      state.userId = "";
      state.role = '';
      state.menus = [];
      state.permissions = [];
    }
  },
  actions: {
    // 登录
    Login({commit, state}, loginForm) {
      return new Promise((resolve, reject) => {
        api({
          url: "Login/login",
          method: "post",
          data: loginForm
        }).then(data => {
          if (data.result === "success") {
            //cookie中保存前端登录状态
            setToken();
          }
          resolve(data);
        }).catch(err => {
          reject(err)
        })
      })
    },
    // 获取用户信息
    GetInfo({commit, state}) {
      return new Promise((resolve, reject) => {
        api({
          url: '/Login/getinfo',
          method: 'post'
        }).then(data => {
          //储存用户信息
          commit('SET_USER', data.userPermission);
          //cookie保存登录状态,仅靠vuex保存的话,页面刷新就会丢失登录状态
          setToken();
          //生成路由
          let userPermission = data.userPermission ;
          store.dispatch('GenerateRoutes', userPermission).then(() => {
            //生成该用户的新路由json操作完毕之后,调用vue-router的动态新增路由方法,将新路由添加
            router.addRoutes(store.getters.addRouters)
          })
          resolve(data)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 登出
    LogOut({commit}) {
      return new Promise((resolve) => {
        api({
          url: "Login/logout",
          method: "post"
        }).then(data => {
          commit('RESET_USER')
          removeToken()
          resolve(data);
        }).catch(() => {
          commit('RESET_USER')
          removeToken()
        })
      })
    },
    // 前端 登出
    FedLogOut({commit}) {
      return new Promise(resolve => {
        commit('RESET_USER')
        removeToken()
        resolve()
      })
    }
  }
}
export default user
