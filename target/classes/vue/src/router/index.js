import Vue from 'vue'
import VueRouter from 'vue-router'

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import(/* webpackChunkName: "about" */ '../views/Manager.vue'),
    redirect:'/home',
    children :[
      { path:'403',name:'Auth',meta:{name:'无权限'}, component:() =>import('../views/manager/Auth')},
      { path:'home',name:'Home',meta:{name:'主页'},component:() =>import('../views/manager/Home')},
      { path:'user',name:'User',meta:{name:'用户管理'},component:() =>import('../views/manager/User')},
      { path:'person',name:'Person',meta:{name:'用户编辑'},component:() =>import('../views/manager/Person')},
      { path:'admin',name:'Admin',meta:{name:'管理员管理'},component:() =>import('../views/manager/Admin')},
      { path:'password',name:'Password',meta:{name:'修改密码'}, component:() =>import('../views/manager/Password')},
      { path:'adminPerson',name:'AdminPerson',meta:{name:'管理员编辑'},component:() =>import('../views/manager/AdminPerson')},

    ]
  },
  {
    path: '/element',
    name: 'Element',
    meta:{name:'element'},
    component: () => import(/* webpackChunkName: "about" */ '../views/Element.vue')
  },
  {
    path: '/login',
    name:'Login',
    meta:{name:'登录'},
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/register',
    name:'Register',
    meta:{name:'注册'},
    component: () => import(/* webpackChunkName: "about" */ '../views/Register.vue')
  },
  {
    path: '/*',
    name:'404',
    meta:{name:'找不到页面'},
    component: () => import(/* webpackChunkName: "about" */ '../views/404.vue')
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


router.beforeEach((to, from, next) => {
  // to 是到达路由信息
  // form 是开原的路由信息
  // next 是帮我们跳转的路由函数
  let adminPaths = ['/admin']
  let loginUser= JSON.parse(localStorage.getItem('honey-user') || '{}')
  if (loginUser.employeeType !=='管理员' && adminPaths.includes(to.path)){
      next('/403')
  }else {
    next()
  }

})

export default router
