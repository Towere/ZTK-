<!-- 管理系统顶部信息栏 -->
<!-- 教师与管理员-->
<template>
  <header id="topbar">
    <el-row>
      <el-col :span="4" class="topbar-left">
          <!-- logo -->
          <li class="logo">
            <Icon icon="solar:water-sun-broken" />
            <span>zhiTK</span>
          </li>

      </el-col>
      <el-col :span="20" class="topbar-right">
        <i class="el-icon-menu" @click="toggle()"></i>
        <div class="user">
          <span>{{ $store.state?.userInfo.user }}</span>
          <img src="" class="user-img" ref="img" @click="showSetting()" />
          <transition name="fade">
            <div class="out" ref="out" v-show="login_flag">
              <ul>
                <li class="exit" @click="exit()"><a href="javascript:;">退出登录</a></li>
              </ul>
            </div>
          </transition>
        </div>
      </el-col>
    </el-row>
  </header>
</template>
  
<script>
import { Icon } from '@iconify/vue2';
import { mapState, mapMutations } from 'vuex'
export default {
  components: {
    Icon
  },
  data() {
    return {
      login_flag: false,
      user: { //用户信息
        userName: null,
        userId: null
      }
    }
  },
  created() {
    this.getUserInfo()
  },
  computed: mapState(["flag", "menu"]),


  methods: {
    //显示、隐藏退出按钮
    showSetting() {    
      this.$store.commit("logout")
      this.$router.push({ path: "/" }) //跳转到登录页面
      this.login_flag = !this.login_flag
    },
    //左侧栏放大缩小
    ...mapMutations(["toggle"]),
    getUserInfo() { //获取用户信息



    },

    exit() {
      this.$router.push({ path: "/" }) //跳转到登录页面
    }
  },
}
</script>
  
<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity .5s;
}

.fade-enter,
.fade-leave-to

/* .fade-leave-active below version 2.1.8 */
  {
  opacity: 0;
}

#topbar {
  position: relative;
  z-index: 10;
  background-color: rgba(15, 16, 20, 0.8);
  height: 80px;
  line-height: 80px;
  color: #fff;
  box-shadow: 0 4px 8px 0  rgba(15, 16, 20, 0.8), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
.logo{
  list-style: none;
  line-height: 80px;
  font-size: 40px;
  
}
#topbar .topbar-left {
  height: 80px;
  display: flex;
  justify-content: center;
  overflow: hidden;
}

.topbar-left .icon-kaoshi {
  font-size: 60px;
}

.topbar-left .title {
  font-size: 20px;
  cursor: pointer;
}

.topbar-right {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.topbar-right .user-img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}

.topbar-right .el-icon-menu {
  font-size: 30px;
  margin-left: 20px;
}

.topbar-right .user {
  position: relative;
  margin-right: 40px;
  display: flex;
}

.topbar-right .user .user-img {
  margin-top: 15px;
  margin-left: 10px;
  cursor: pointer;
}

.user .out {
  font-size: 14px;
  position: absolute;
  top: 80px;
  right: -20px;
  background-color:  rgba(15, 16, 20, 0.8);
  box-shadow: 0 4px 8px 0  rgba(15, 16, 20, 0.8), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  padding: 12px;
}

.user .out ul {
  list-style: none;
}



.out a {

  color: #000;
}

.out .exit {
  margin-top: 20px;
  padding-top: 9px;
  border-top: 5px solid #ccc;
}</style>
  