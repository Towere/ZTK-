# Bank-question 前端项目

## 项目简介

这是 Bank-question 在线题库与考试系统的前端项目，基于 Vue 2 + Element UI 开发，提供教师管理端和学生考试端两个界面。

## 技术栈

| 技术 | 版本 | 说明 |
|-----|------|------|
| Vue | 2.6.14 | 前端框架 |
| Vue Router | 3.6.5 | 路由管理 |
| Vuex | 3.6.2 | 状态管理 |
| Vuex PersistedState | 4.1.0 | 状态持久化 |
| Element UI | 2.15.14 | UI 组件库 |
| Axios | 1.6.0 | HTTP 客户端 |
| Vue CLI | 5.0.0 | 脚手架工具 |

## 功能特性

### 教师端
- 学生管理：添加学生、学生列表管理
- 考试管理：创建考试、查询考试
- 题库管理：多种题型的增删改查
- 试卷管理：组卷、发布试卷

### 学生端
- 我的考试：查看可参加的考试
- 在线考试：参加考试、提交答案
- 成绩查询：查看考试成绩
- 错题本：查看错题记录
- 消息中心：接收系统消息
- 听力训练：听力题练习
- AI 助手：智能问答功能

## 项目结构

```
zhitk/
├── public/                 # 静态资源
├── src/
│   ├── assets/            # 资源文件
│   ├── components/        # 组件
│   │   ├── common/        # 公共组件
│   │   └── vuex/          # Vuex 相关
│   ├── views/             # 页面视图
│   │   ├── admin/         # 教师/管理员页面
│   │   ├── student/       # 学生页面
│   │   └── teacher/       # 教师功能页面
│   ├── router/            # 路由配置
│   │   └── index.js       # 路由定义
│   ├── utils/             # 工具函数
│   ├── vuex/              # Vuex 存储
│   ├── App.vue            # 根组件
│   └── main.js            # 入口文件
├── package.json           # 项目依赖
├── vue.config.js          # Vue CLI 配置
└── README.md              # 本文件
```

## 环境要求

- **Node.js**: 14.x 或更高版本
- **npm**: 6.x 或更高版本
- **后端服务**: Bank-question 后端（运行在 localhost:5555）

## 本地开发

### 1. 安装依赖

```bash
cd vueProject/zhitk
npm install
```

### 2. 配置后端接口地址

在 `src/utils/` 或 Axios 配置文件中配置后端 API 地址：

```javascript
// 示例：baseURL 配置
axios.defaults.baseURL = 'http://localhost:5555'
```

### 3. 启动开发服务器

```bash
npm run serve
```

开发服务器将在 `http://localhost:8080` 启动（或其他可用端口）。

### 4. 访问应用

打开浏览器访问：`http://localhost:8080`

## 角色权限说明

| 角色代码 | 角色名称 | 说明 |
|---------|---------|------|
| 0 | 管理员 | 系统管理员 |
| 1 | 教师 | 教师端用户 |
| 2 | 学生 | 学生端用户 |

## 主要页面路由

### 登录页
- 路径: `/`
- 组件: `@/components/common/login`
- 权限: 公开

### 教师端路由 (/index)
| 路径 | 说明 | 权限 |
|-----|------|------|
| `/` | 首页 | 教师 |
| `/addStudent` | 添加学生 | 教师 |
| `/studentsManage` | 学生管理 | 教师 |
| `/addExam` | 创建考试 | 教师 |
| `/selectExam` | 查询考试 | 教师 |

### 学生端路由 (/student)
| 路径 | 说明 | 权限 |
|-----|------|------|
| `/` | 我的考试 | 学生 |
| `/message` | 消息中心 | 学生 |
| `/listening` | 听力训练 | 学生 |
| `/studentScore` | 成绩查询 | 学生 |
| `/examMsg/:examCode` | 考试详情 | 学生 |
| `/startAnswer/:examCode` | 开始答题 | 学生 |

## 状态管理 (Vuex)

项目使用 Vuex 进行全局状态管理，并通过 `vuex-persistedstate` 实现状态持久化。

主要存储内容：
- 用户信息 (userInfo)
- 认证 Token
- 权限角色

## 构建部署

### 开发环境构建
```bash
npm run serve
```

### 生产环境构建
```bash
npm run build
```

构建产物将输出到 `dist/` 目录。

### 代码检查
```bash
npm run lint
```

## 部署到 Nginx

构建完成后，将 `dist/` 目录的内容部署到 Web 服务器。

Nginx 配置示例：

```nginx
server {
    listen 80;
    server_name your-domain.com;

    root /var/www/zhitk/dist;
    index index.html;

    # Vue Router history 模式支持
    location / {
        try_files $uri $uri/ /index.html;
    }

    # API 反向代理
    location /api/ {
        proxy_pass http://localhost:5555/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## 开发说明

### API 调用示例

```javascript
// 使用 axios 调用后端接口
this.$axios.get('/api/user/info')
  .then(response => {
    console.log(response.data);
  })
  .catch(error => {
    console.error(error);
  });
```

### Element UI 组件使用

项目已全局注册 Element UI，可直接使用：

```vue
<template>
  <el-button type="primary">按钮</el-button>
</template>
```

## 常见问题

### 1. 跨域问题
开发环境可通过 Vue CLI 的代理配置解决，生产环境使用 Nginx 反向代理。

### 2. 路由刷新 404
这是 Vue Router history 模式的问题，请确保 Nginx 配置了 `try_files` 规则。

### 3. 无法登录
请检查后端服务是否正常启动，API 地址配置是否正确。

## 相关文档

- [后端项目文档](../../Bank-question/README.md)
- [部署指南](../../Bank-question/DEPLOY.md)
- [API 文档](../../Bank-question/API.md)
- [Vue 2 官方文档](https://v2.vuejs.org/)
- [Element UI 文档](https://element.eleme.cn/)
