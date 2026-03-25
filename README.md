# Bank-question 在线题库与考试系统

## 项目简介

Bank-question 是一个基于 Spring Boot + Vue 2 开发的综合性在线题库与考试系统，采用前后端分离架构。支持多种题型、试卷管理、考试安排、成绩统计等功能，并集成了阿里云通义千问 AI 提供智能问答辅助。

## 目录结构

```
Design/
├── Bank-question/              # 后端项目（Spring Boot）
│   ├── src/                    # 源代码
│   │   └── main/
│   │       ├── java/           # Java 源代码
│   │       └── resources/      # 配置文件
│   ├── pom.xml                 # Maven 配置
│   ├── README.md               # 后端详细文档
│   ├── DEPLOY.md               # 部署指南
│   └── API.md                  # API 文档
├── vueProject/                 # 前端项目（Vue 2）
│   └── zhitk/                  # 前端主项目
│       ├── src/                # 源代码
│       ├── package.json        # npm 依赖
│       └── README.md           # 前端文档
├── pom.xml                     # 父项目 Maven 配置
└── README.md                   # 本文件
```

## 技术栈总览

### 后端技术栈

| 技术 | 版本 | 说明 |
|-----|------|------|
| Java | 1.8 | 开发语言 |
| Spring Boot | 2.3.7 | Web 框架 |
| MyBatis Plus | 3.4.2 | ORM 框架 |
| MySQL | 5.7+ | 数据库 |
| Redis | - | 缓存 |
| JWT | 3.8.3 | 身份认证 |
| Swagger | 2.9.2 | API 文档 |
| Spring AI | 1.0.0-M1 | AI 集成 |
| 阿里云通义千问 | - | AI 大模型 |

### 前端技术栈

| 技术 | 版本 | 说明 |
|-----|------|------|
| Vue | 2.6.14 | 前端框架 |
| Vue Router | 3.6.5 | 路由管理 |
| Vuex | 3.6.2 | 状态管理 |
| Element UI | 2.15.14 | UI 组件库 |
| Axios | 1.6.0 | HTTP 客户端 |

## 快速开始

### 后端启动

详细文档请查看 [Bank-question/README.md](./Bank-question/README.md)

- [后端开发环境搭建](./Bank-question/README.md#本地开发环境搭建)
- [后端部署指南](./Bank-question/DEPLOY.md)
- [API 文档](./Bank-question/API.md)

### 前端启动

详细文档请查看 [vueProject/zhitk/README.md](./vueProject/zhitk/README.md)

- [前端开发环境搭建](./vueProject/zhitk/README.md#本地开发)

## 功能模块

- 用户系统（管理员、教师、学生三种角色）
- 题库管理（多选、填空、判断、听力等题型）
- 试卷系统
- 考试管理
- 成绩管理
- 错题本
- AI 智能助手
- 消息系统

## License

本项目仅供学习和研究使用。
