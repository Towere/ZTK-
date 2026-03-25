import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/components/vuex/store'
//调用 Vue.use() 函数，把VueRouter安装为VUE的插件
//vue.use函数的 用于安装插件 
Vue.use(VueRouter)


//创建路由实例
const router = new VueRouter({
  //routes是一个数组 ,作用：定义“hash地址”和“组件”之间的对应关系
  routes: [
    {
      path: '/',
      name: 'login', //登录界面
      component: () => import('@/components/common/login'),
      meta: {
        auth: false,
      }
    },


    {
      path: '/index', //教师主页
      component: () => import('@/views/admin/index'),
      children: [
        {
          path: '/', component: () => import('@/components/common/new'), meta: {
            auth: true,
            authCode: 1,
          }
        },//设置默认路由
        {
          path: '/addStudent', component: () => import('@/views/teacher/addStudent'), meta: {
            auth: true,
            authCode: 1,
          }
        },//添加学生
        {
          path: '/studentsManage', component: () => import('@/views/teacher/studentsManage'), meta: {
            auth: true,
            authCode: 1,
          }
        },
        {
          path: '/addExam', component: () => import('@/views/teacher/addExam'), meta: {
            auth: true,
            authCode: 1,
          }
        },//学生管理
        {
          path: '/selectExam', component: () => import('@/views/teacher/selectExam'), meta: {
            auth: true,
            authCode: 1,
          }
        }//学生管理
      ]
    },

    {
      path: '/student',//学生界面
      component: () => import('@/views/student/index'),
      //子路由规则
      //“redirec或者默认子路由：如果children数组中，某个路由规则的path值为空字符串，则这条规则为某认子路由”
      children: [
        {
          path: '/', component: () => import('@/views/student/myExam'), meta: {
            auth: true,
            authCode: 2,
          }
        },
        {
          path: '/message', component: () => import('@/views/student/message'), meta: {
            auth: true,
            authCode: 2,
          }
        },
        {
          path: '/listening', component: () => import('@/views/student/myListening'), meta: {
            auth: true,
            authCode: 2,
          }
        },
        {
          path: '/studentScore', component: () => import("@/views/student/answerScore"), meta: {
            auth: true,
            authCode: 2,
          }
        },
        {
          path: '/examMsg/:examCode', component: () => import('@/views/student/examMsg'), meta: {
            auth: true,
            authCode: 2,
          }
        },
        {
          path: '/startAnswer/:examCode', component: () => import('@/views/student/startAnswer'), meta: {
            auth: true,
            authCode: 2,
          }
        },
        {
          path: '/myListening copy/:paperId', component: () => import('@/views/student/myListening copy'), meta: {
            auth: true,
            authCode: 2,
          }
        },

      ]
    },
  ]
})

//全局前置导航守卫
//所以路由在真正渲染前 都会经过全局前置导航守卫 只有放行了 才会到指定页面
//to: 从哪里去，从哪里来的完整路由信息对象(路径，参数)
//from: 从哪里来，从哪里来的完整路由信息对象(路径，参数)
//next(): 是否放行
//(1)nexy()   直接放行，放行到to要去的路径
//(2)nexy(路径) 进行拦截，拦截到next里面配置的路径  

router.beforeEach((to, from, next) => {

  if (!to.meta?.auth) {
    next()
    return
  }

  if (!store.state.userInfo.user) {
    next("/")
  }
  // 权限判断
  // 0管理员
  // 1教师
  // 2 学生
  if (to.meta?.authCode && +to.meta?.authCode === store.state.userInfo.authCode) {
  } else {
    // 权限不成立，可以进行一些操作，比如跳转到无权限页或者显示提示信息
    
  }
  next()
})




//向外共享路由实例对象
export default router


