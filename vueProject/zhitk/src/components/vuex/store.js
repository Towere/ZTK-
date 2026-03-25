import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex);

const state = {
  isPractice: false, //练习模式标志
  flag: false, //菜单栏左右滑动标志
  userInfo: {
    user: "",
    authCode: -1, // 0管理员  1老师  2学生 
  },
  adminBreakList:[],
  menu: [
    {
      index: '1',
      title: '考试管理',
      icon: 'icon-kechengbiao',
      content:[{item1:'功能介绍',path:'/examDescription'},{item2:'考试查询',path:'selectExam'},{item3:'添加考试',path:'/addExam'}],
    },
    {
      index: '2',
      title: '题库管理',
      icon: 'icon-tiku',
      content:[{item1:'功能介绍',path:'/answerDescription'},{item2:'所有题库',path:'/selectAnswer'},{item3:'增加题库',path:'/addAnswer'},{path: '/addAnswerChildren'}],
    },
    {
      index: '3',
      title: '成绩查询',
      icon: 'icon-performance',
      content:[{item1:'学生成绩查询',path:'/allStudentsGrade'},{path: '/grade'},{item2: '成绩分段查询',path: '/selectExamToPart'},{path: '/scorePart'}],
    },
    {
      index: '4',
      title: '学生管理',
      icon: 'icon-role',
      content:[{item1:'学生管理',path:'/studentsManage'},{item2: '添加学生',path: '/addStudent'}],
    },
  ],
}
const mutations = {
  setMenu(state){
    state.menu=[
      {
        index: '1',
        title: '考试管理',
        icon: 'icon-kechengbiao',
        content:[{item1:'功能介绍',path:'/examDescription'},{item2:'考试查询',path:'/selectExam'},{item3:'添加考试',path:'/addExam'}],
      },
      {
        index: '2',
        title: '题库管理',
        icon: 'icon-tiku',
        content:[{item1:'功能介绍',path:'/answerDescription'},{item2:'所有题库',path:'/selectAnswer'},{item3:'增加题库',path:'/addAnswer'},{path: '/addAnswerChildren'}],
      },
      {
        index: '3',
        title: '成绩查询',
        icon: 'icon-performance',
        content:[{item1:'学生成绩查询',path:'/allStudentsGrade'},{path: '/grade'},{item2: '成绩分段查询',path: '/selectExamToPart'},{path: '/scorePart'}],
      },
      {
        index: '4',
        title: '学生管理',
        icon: 'icon-role',
        content:[{item1:'学生管理',path:'/studentsManage'},{item2: '添加学生',path: '/addStudent'}],
      },
    ]
    if(state.userInfo.authCode==0){
      state.menu.push({
            index: '5',
            title: '教师管理',
             icon: 'icon-Userselect',
            content:[{item1:'教师管理',path:'/teacherManage'},{item2: '添加教师',path: '/addTeacher'}],
        })
    }
  },
  practice(state,status) {
    state.isPractice = status
  },
  toggle(state) {
    state.flag = !state.flag
  },
  changeUserInfo(state,info) {
    state.userInfo = info
  },
  logout(state) {
    state.userInfo.user = "" 
    state.userInfo.authCode = -1 
  }
}
const getters = {
 
}
const actions = {
  getUserInfo(context,info) {
    context.commit('changeUserInfo',info)
  },
  getPractice(context,status) {
    context.commit('practice',status)
  }
}





export default new Vuex.Store({
  name:"auth",
  state,
  mutations,
  getters,
  actions,
  // state: {
  //   examData: {},
  // },
  // mutations: {
  //   setExamData(state, data) {
  //     state.examData = data;
  //   },
  // },
  plugins: [createPersistedState()]
});
