<!-- 用户登录界面 -->
<template>
    <div id="login" style="background-image: url(../img/login.jpg);">
      <div class="top-nav">
        <!-- logo -->
        <li>
          <Icon icon="solar:water-sun-broken" />
          <span>zhiTK</span>
        </li>
   


      <el-row class="main-container">
        <el-col :lg="8" :xs="16" :md="10" :span="10">
          <div class="top">
            <li>
              <span class="ititle" style="font-size: 30px;">zhiTK</span>
            </li>
          </div>

          <div class="bottom">
            <div class="container">
              <p class="title">LOGIN</p>
              <el-form :label-position="labelPosition" :model="formLabelAlign">
                <el-form-item>
                  <el-input class="input" v-model.number="formLabelAlign.user" placeholder="username"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-input v-model="formLabelAlign.pwd" placeholder="password" type='password'></el-input>
                </el-form-item>
                <div class="submit">
                  <el-button type="primary" class="row-login" @click="login()">LOGIN</el-button>
                </div>
              </el-form>

            </div>
          </div>
        </el-col>
      </el-row>

      <el-row class="footer">
        <el-col>
          <p class="msg2">Copyright©2014-2023 高等教育出版社 <a href="http://beian.miit.gov.cn/"
              target="_blank">京ICP备12020869号-19</a></p>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
  
<script>
import { mapState } from 'vuex';
import { Icon } from '@iconify/vue2';
import request from '@/utils/request'; // 引入 request.js 文件

export default {
  components: {
    Icon
  },
  name: "login",
  data() {
    return {
      role: 2,
      labelPosition: 'left',
      formLabelAlign: {
        user: '',
        pwd: ''
      }
    }
  },
  methods: {
    // 用户登录请求后台处理
    async login() { // 添加 async 关键字以便使用异步操作
      console.log("登录操作执行-------");
      try {
        const res = await request.post('/api/login', { ...this.formLabelAlign }); // 使用 request.js 发送 POST 请求
        let resData = res.data;
        if (resData != null) {
          switch (resData) {
            case 0:  // 管理员
              this.$router.push({ path: '/index' }); // 跳转到首页
              break;
            case 1: // 教师
              this.$router.push({ path: '/index' }); // 跳转到教师用户
              break;
            case 2: // 学生
              this.$router.push({ path: '/student' });//跳转学生用户
              break;
          }
          this.$store.commit("changeUserInfo",{
            user: this.formLabelAlign.user ,
            authCode:res.data
          })
        } else { // 错误提示
          this.$message({
            showClose: true,
            type: 'error',
            message: '用户名或者密码错误'
          });
        }
      } catch (error) {
        // 错误处理
        console.error(error);
        this.$message({
          showClose: true,
          type: 'error',
          message: '请求出错，请稍后再试'
        });
      }
    },
    clickTag(key) {
      this.role = key;
    }
  },
  computed: mapState(["userInfo"]),
  mounted() {

  }
}
</script>
  
<style lang="less" scoped>



#login {
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background-size: cover;
  /* 填充整个div */
  position: fixed;
  justify-content: center;
  /* 在横向上居中对齐 */
  align-items: center;
  /* 在纵向上居中对齐 */
}
.top-nav {
 background-color: rgba(15, 16, 20, 0.8);
  height: 50px;
  width: 100%;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 999;
  border-radius: 80px;
  /* 添加 border-radius 属性 */
}

.top-nav li {
  display: flex;
  /* 使用 flex 布局 */
  align-items: center;
  /* 在垂直方向上居中 */

}

.top-nav li svg {
  font-size: 45px;
  color: #f33;
  padding-left: 170px;

}

.top-nav li span {
  font-size: 30px;
  color: white;
  margin-left: 17px;
  /* 修改为 margin-left */
  line-height: 55px;
  /* 设置 line-height */
  text-shadow: 0 0 5px white;
  /* 添加 text-shadow 属性 */
}



 .main-container {
  display: flex;
  justify-content: center;
  align-items: center;

}




.el-input {
  background-image: linear-gradient(to right, #e8198b, #0eb4dd);
  height: 50px;
  width: 60%;
  margin-bottom: 20px;
  border-radius: 40px;
  justify-content: center;
  
}

.container {
  width: 450px;
  background-color: rgba(15, 16, 20, 0.8);
  border-radius: 180px;
  padding: 20px;
  justify-content: center;
}

.submit {
  display: flex;
  justify-content: center;
  display: flex;
}

.el-button {
  background-color: rgba(41, 45, 62, .8);
  width: 90px;
  height: 40px;
  border: 2px solid #0e92b3;
  text-align: center;
  line-height: 15px;
  border-radius: 30px;


}

.el-button:hover {
  background-image: linear-gradient(120deg, #84fab0 0%, #8fd3f4 100%);
}

.top {
  
  margin-right: 500px;
  margin-bottom: 20px;
  margin-top: 150px;
  font-size: 30px;
  color: #ff962a;
  display: flex;
  justify-content: center;

}

li {
  list-style: none;
  line-height: 10px;
}

.title {
  text-align: center;
  font-size: 35px;
  text-transform: uppercase;
  line-height: 100px;
  color: #f7fafc;
}
.ititle{
  text-align: center;
  background-color: rgba(15, 16, 20, 0.8);
  border-radius: 30px;
  width: 200px;
}
.bottom {
  text-align: center;
  display: flex;
  justify-content: center;
}

.footer {
  margin-top: 40px;
  text-align: center;
}

.msg2 {
  font-size: 14px;
  color: #919698;
}

.msg2 a {
  color: #ff962a;
}</style>