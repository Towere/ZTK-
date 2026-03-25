// 展示组件页面
<template>

  <div id="index">
    <header1 class="topbar"></header1>
    <section class="container">
      <div class="left_side">
        <mainLeft></mainLeft>
      </div>
      <div class="main_wrapper">
        <navigator class="nav"></navigator>
        <router-view></router-view>
      </div>
    </section>
    <v-footer class="footer"></v-footer>
  </div>

</template>

<script>
import header from '@/components/common/header'
import mainLeft from '@/components/common/mainLeft'
import navigator from '@/components/common/navigator'
import myFooter from '@/views/student/myFooter';

export default {
  components:{
    header1: header,
    mainLeft: mainLeft,
    navigator:navigator,
    "v-footer": myFooter,
  },
  data() {
    return {
      username: '许如梦'
    }
  },
  methods: {
    
  }
}
</script>

<style lang="less" scoped>

#index .nav {
  box-shadow: 1px 0 5px rgba(242, 6, 6, 0.1);
  margin-bottom: 40px;
}
.container {
  display: flex;
  background-color: #fff;
}
.main_wrapper {
  overflow: hidden;
  flex: 1;
  
}
.footer{
  margin-top: -40px;
}
</style>

