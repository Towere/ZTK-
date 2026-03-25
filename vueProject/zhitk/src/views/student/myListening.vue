<template>
  <div class="item1">
    <div class="item" v-for="(item, index) in pagination.audioes" :key="index" @click="toExamMsg(item)">
      <img style="width: 400px" :src="item.photo" alt="" />
      <h2>
        <p>{{ item.source }}</p>
      </h2>
      <h3>
        <p>{{ item.created }}</p>
      </h3>
    </div>
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
</template>

<script>
import request from "@/utils/request";
export default {
  name: "AudioFile",
  props: [],
  data() {
    return {
      pagination: {
        current: 1,
        total: null,
        size: 6,
        audioes: [],
      },
    };
  },
  created() {
    this.getExamInfo();
  },
  methods: {
    async getExamInfo() {
      try {
        const searchPageDto = {
          page: this.pagination.current,
          size: this.pagination.size,
        };

        const response = await request.post("/api/searchAllAudio", searchPageDto);
        
        this.pagination.audioes = [...response.data.list];
      } catch (error) {
        console.error("Error fetching exam info:", error);
        
      }
    },
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getExamInfo();
    },
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getExamInfo();
    },
    toExamMsg(item) {
      console.log(item);
      this.$store.state.examData = item;

      this.$router.push({
        path: `/myListening copy/${item.paperid}`,
        query: {
          examData: item,
        },
      });

      console.log(this.$store.state.examData);
    },
  },
};
</script>

<style scoped>
.item1 {
  width: 100%;
  display: inline-block;
}
.item {
  width: 33.33%;
  margin-top: 60px;
  float: left;
  text-align: center;
}
.addCartButton{
  background-color: aquamarine;
  padding: 10px;
  margin-top: 8px;
  color: white;
  border-radius: 30px;
}
</style>
    