<!--左侧下拉式导航栏-->
<!-- 2023.11.13 -->
<template>
    <div id="left">
      <el-menu
        active-text-color="#FFF" 
        text-color="#FFF" 
        :default-active="$route.path"
        class="el-menu-vertical-demo" 
        @open="handleOpen" 
        @close="handleClose" 
        :collapse="flag"
        background-color=" rgba(15, 16, 20, 0.8)"  
        menu-trigger="click" router>


        <el-submenu v-for="(item,index) in menu" :index='item.index' :key="index"> 
          <div slot="title">
            <div class="left-width">
              <i class="iconfont" :class="item.icon"></i>
              <span slot="title" class="title">{{item.title}}</span>
            </div>
          </div>


          <el-menu-item-group v-for="(list,index1) in item.content" :key="index1" >
            <el-menu-item @click="handleTitle(item.index)" :index="list.path" v-if="list.item1 != null">{{list.item1}}</el-menu-item>
            <el-menu-item @click="handleTitle(item.index)" :index="list.path" v-if="list.item2 != null">{{list.item2}}</el-menu-item>
            <el-menu-item @click="handleTitle(item.index)" :index="list.path" v-if="list.item3 != null">{{list.item3}}</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </el-menu>
    </div>
  </template>
  
  <script>
  import {mapState} from 'vuex'
import store from '../vuex/store'
  export default {
    name: "mainLeft",
    data() {
      return {
        
      }
    },
    computed: mapState(["flag","menu"]),
    created() {
      this.addData()
    },
    methods: {
      handleOpen(key, keyPath) {
        // console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        // console.log(key, keyPath);
      },
      //点击标题传递参数给navigator组件
      handleTitle(index) {
        this.bus.$emit('sendIndex',index)
      },
      addData() {
        store.commit("setMenu")
        // if(this.$store.state.userInfo?.authCode == 0) {
        //   this.menu.push({
        //     index: '5',
        //     title: '教师管理',
        //     icon: 'icon-Userselect',
        //     content:[{item1:'教师管理',path:'/teacherManage'},{item2: '添加教师',path: '/addTeacher'}],
        //   })
        // }
      }
    },
  }
  </script>
  
  <style>
  .el-menu-vertical-demo .el-submenu__title {
    overflow: hidden;
  }
  .left-width .iconfont {
    font-size: 18px;
    color: #fff;
  }
  .left-width {
    width: 180px;
  }
  .el-menu-vertical-demo:not(.el-menu--collapse) {
    min-height: 900px;
   
  }
  #left {
    height: 900px;
    background-color: rgba(15, 16, 20, 0.8);
    z-index: 0;
  }
  #left .el-menu-vertical-demo .title {
    color: #fff;
    font-size: 16px;
    font-weight: bold;
    margin-left: 14px;
  }
  
  .el-submenu {
    color: #fff;
    border-bottom: 1px solid #eeeeee0f !important;
  }
  .el-submenu__title:hover {
    background-color: #fff;
  }
  .el-submenu__title i {
      color: #fbfbfc !important;
  }
  </style>
  