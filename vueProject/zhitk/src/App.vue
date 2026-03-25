<template>
  <div id="app">
<router-view></router-view>
  </div>
</template>

<script>
import index from './views/admin/index.vue';

export default {
  components: {
    index
  },
  name: 'App',
}
</script>

<style>
  ul {
    list-style: none;
  }
  
  a {
    text-decoration: none;
  }
  
  * {
    margin: 0;
    padding: 0;
  }
  
  #app {
    font-family: "Microsoft YaHei", "Helvetica", "Tahoma", "Geneva", "Arial", sans-serif;
    background-color: #f7f7f7;
    color: #333;
    line-height: 1.5;
    padding: 20px;

  }
</style>
