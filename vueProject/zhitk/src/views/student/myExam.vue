<template>
  <div id="myExam">
    <div class="title">我的试卷</div>
    <div class="wrapper">
      <ul class="top">
        <li class="order">试卷列表</li>
        <li class="search-li">
          <div class="icon">
            <input
              type="text"
              placeholder="试卷名称"
              class="search"
              v-model="key"
            /><i class="el-icon-search"></i>
          </div>
        </li>
        <li>
          <el-button type="primary" @click="search()">搜索试卷</el-button>
          <el-button type="primary" @click="goBack()">返回</el-button>
        </li>
      </ul>
      <ul class="paper" v-loading="loading">
        <li
          class="item"
          v-for="(item, index) in pagination.records"
          :key="index"
          @click="toExamMsg(item)"
        >
          <h4>{{ item.source }}</h4>
          <p class="name">{{ item.source }}-{{ item.description }}</p>
          <div class="info">
            <i class="el-icon-loading"></i
            ><span>{{ item.examDate.substr(0, 10) }}</span>
            <i class="iconfont icon-icon-time"></i
            ><span v-if="item.totalTime != null"
              >限时{{ item.totalTime }}分钟</span
            >
            <i class="iconfont icon-fenshu"></i
            ><span>满分{{ item.totalScore }}分</span>
          </div>
        </li>
      </ul>
      <div class="pagination">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pagination.current"
          :page-sizes="[6, 10, 20, 40]"
          :page-size="pagination.size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pagination.total"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request"; // 引入 request.js 文件


export default {
  data() {
    return {
      loading: false,
      key: null, // 搜索关键字
      pagination: {
        current: 1, // 当前页
        total: null, // 记录条数
        size: 6, // 每页条数
        records: [], // 考试信息数组
      },
    };
  },
  created() {
    this.getExamInfo();
    this.loading = true;
  },
  methods: {

    getExamInfo() {
      const searchPageDto = {
        page: this.pagination.current,
        size: this.pagination.size,
      };

      // 发送请求并更新数据
      request
        .post("/api/selectExamManage",searchPageDto)
        .then((res) => {
          console.log(res.data.list);
          this.pagination.records.splice(0)
          this.loading = false;
          res.data.list.forEach(element => {
            this.pagination.records.push(element)
          });
        })
        .catch((error) => {
          console.error("获取考试信息失败:", error);
          this.loading = false;
        });
    },
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getExamInfo();
    },
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getExamInfo();
    },
    // 搜索试卷
    search() {
      this.loading = true; // 设置加载状态
      request
        .post("/api/selectExam")
        .then((res) => {
          // 请求成功后处理获取的考试信息
          this.pagination.records = res.data;
          console.log(res); // 在获取数据后进行搜索操作
          if (this.pagination.records && this.pagination.records.length > 0) {
            let newPage = this.pagination.records.filter((item) => {
              return item.source.includes(this.key);
            });
            this.pagination.records = newPage;
          }
          this.loading = false; // 取消加载状态
        })
        .catch((error) => {
          console.error("请求失败", error);
          this.loading = false; // 处理请求失败的情况
        });
    },
    goBack() {
    this.key = null; // 清空搜索关键字
    this.getExamInfo(); // 重新获取所有考试信息
  },
  toExamMsg(item) {
    console.log(item);
    this.$store.state.examData=item
  this.$router.push({ path: `/examMsg/${item.examCode}`,query:{
    itemData:item
  }});
}

}};

</script>

<style lang="less" scoped>
.pagination {
  padding: 20px 0px 30px 0px;
  .el-pagination {
    display: flex;
    justify-content: center;
  }
}
.paper {
  h4 {
    cursor: pointer;
  }
}
.paper .item a {
  color: #000;
}
.wrapper .top .order {
  cursor: pointer;
}
.wrapper .top .order:hover {
  color: #0195ff;
  border-bottom: 2px solid #0195ff;
}
.wrapper .top .order:visited {
  color: #0195ff;
  border-bottom: 2px solid #0195ff;
}
.item .info i {
  margin-right: 5px;
  color: #0195ff;
}
.item .info span {
  margin-right: 14px;
}
.paper .item {
  width: 380px;
  border-radius: 4px;
  padding: 20px 30px;
  border: 1px solid #eee;
  box-shadow: 0 0 4px 2px rgba(217, 222, 234, 0.3);
  transition: all 0.6s ease;
}
.paper .item:hover {
  box-shadow: 0 0 4px 2px rgba(140, 193, 248, 0.45);
  transform: scale(1.03);
}
.paper .item .info {
  font-size: 14px;
  color: #88949b;
}
.paper .item .name {
  font-size: 14px;
  color: #88949b;
}
.paper * {
  margin: 20px 0;
}
.wrapper .paper {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}
.top .el-icon-search {
  position: absolute;
  right: 10px;
  top: 10px;
}
.top .icon {
  position: relative;
}
.wrapper .top {
  border-bottom: 1px solid #eee;
  margin-bottom: 20px;
}
#myExam .search-li {
  margin-left: auto;
}
.top .search-li {
  margin-left: auto;
}
.top li {
  display: flex;
  align-items: center;
}
.top .search {
  margin-left: auto;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #eee;
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
}
.top .search:hover {
  color: #0195ff;
  border-color: #0195ff;
}
.wrapper .top {
  display: flex;
}
.wrapper .top li {
  margin: 20px;
}
#myExam {
  width: 980px;
  margin: 0 auto;
}
#myExam .title {
  margin: 20px;
}
#myExam .wrapper {
  background-color: #fff;
}
</style>
