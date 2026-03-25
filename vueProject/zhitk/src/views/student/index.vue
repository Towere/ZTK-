<!--学生考试首页-->
<template>
  <div id="student">
    <el-row class="padding-50">
      <el-col :span="24">
        <ul class="list">
          <li class="logo">
            <Icon icon="solar:test-tube-bold-duotone" /><span>ZhiTK</span>
          </li>
          <li><router-link to="/student">我的试卷</router-link></li>
          <li><router-link to="/listening">我的练习</router-link></li>
          <li><router-link to="/scoreTable">我的分数</router-link></li>
          <li><router-link to="/message">给我留言</router-link></li>

          <li><a href="javascript:;">待定</a></li>
          <li class="right" @mouseenter="flag = !flag" @mouseleave="flag = !flag">
            <a href="javascript:;" style="display: flex;align-items: center;">
              <Icon icon="solar:accessibility-linear" style="margin-right: 0.4rem;"/>
              {{ $store?.state?.userInfo?.user }}
            </a>
            <div class="msg" v-if="flag">
              <p @click="manage()">管理中心</p>
              <p class="exit" @click="exit()">退出</p>
            </div>
          </li>

        </ul>
      </el-col>
    </el-row>
    <!-- 路由区域 -->
    <!-- 占位符 -->
    <div class="main">
      <router-view></router-view>
    </div>
    <v-footer></v-footer>
  </div>
</template>
<script>
import myFooter from '@/views/student/myFooter';
import { Icon } from '@iconify/vue2';

export default {
  components: {
    "v-footer": myFooter,
    Icon
  },
  data() {
    return {
      flag: false,
      user: {}
    }
  },
  created() {
    this.userInfo()
  },
  methods: {
    exit() {  //退出登录
      this.$store.commit("logout")
      this.$router.push({ path: "/" }) //跳转到登录页面
    },
    // manage() {  //跳转到修改密码页面
    //   this.$router.push({path: '/manager'})
    // },
    userInfo() {

    },

  },
  // computed:mapState(["isPractice"])
}
</script>

<style scoped>
.right .icon {
  margin-right: 6px;
}

#student .padding-50 {
  margin: 0 auto;
  padding: 0 50px;
  box-shadow: 0 0 10px 4px rgba(1, 149, 255, 0.1);
  background-color: #fff;
}

.list a {
  text-decoration: none;
  color: #334046;
}

li {
  list-style: none;
  height: 60px;
  line-height: 60px;
}

#student .list {
  display: flex;
}

#student .list li {
  padding: 0 20px;
  cursor: pointer;
}

#student .list li:hover {
  background-color: #0195ff;
  transition: all 2s ease;
}

#student .list li:hover a {
  color: #fff;
}

#student .list .right {
  margin-left: auto;
  position: relative;
}

#student .list li.right :hover a {
  color: #000;
}

#student .list .logo {
  display: flex;
  font-weight: bold;
  color: #2f6c9f;
}

#student .list .logo i {
  font-size: 50px;
}

.right .msg {
  text-align: center;
  position: absolute;
  top: 60px;
  left: 0px;
  display: flex;
  flex-direction: column;
  border-radius: 2px;
  border-bottom: 3px solid #0195ff;
  background-color: #fff;
}

.right .msg p {
  height: 40px;
  line-height: 40px;
  width: 105px;
}

.right .msg p:hover {
  background-color: #0195ff;
}
</style>
