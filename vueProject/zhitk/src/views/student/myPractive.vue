<template>
    <div>
      <el-upload
        action="http://localhost:5555/api/importMultiQuestion"
        :on-success="handleSuccess"
        :on-error="handleError"
        :before-upload="beforeUpload"
        multiple
        :limit="3"
        :on-exceed="handleExceed"
        
      >
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip">只能上传csv文件</div>
      </el-upload>
    </div>
  </template>
  
  <script>
  export default {
    methods: {
      handleSuccess(response, file, fileList) {
        this.$message.success('文件上传成功');
        // 处理上传成功的逻辑
      },
      handleError(err, file, fileList) {
        this.$message.error('文件上传失败');
        // 处理上传失败的逻辑
      },
      beforeUpload(file) {
        const isCSV = file.type === 'text/csv';
        if (!isCSV) {
          this.$message.error('只能上传csv文件');
        }
        return isCSV;
      },
      handleExceed(files, fileList) {
        this.$message.warning('只能上传三个文件');
      }
    }
  }
  </script>
  
  <style>
  /* 样式可以根据需要进行自定义 */
  </style>
  