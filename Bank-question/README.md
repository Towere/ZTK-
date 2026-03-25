# Bank-question 在线题库与考试系统

## 项目简介

Bank-question 是一个功能完善的在线题库与考试管理系统，采用前后端分离架构，后端基于 Spring Boot 2.3.7 开发，提供 RESTful API 接口。系统支持多种题型、试卷组卷、在线考试、自动评分、成绩分析等功能，并集成了阿里云通义千问 AI 大模型，提供智能问答和学习辅助功能。

## 功能特性

### 用户系统
- 三种角色：管理员、教师、学生
- JWT Token 身份认证
- 用户信息管理

### 题库管理
- **多选题** (MultiQuestion)
- **填空题** (FillQuestion)
- **判断题** (JudgeQuestion)
- **听力题** (ListenQuestion)
- 题目增删改查
- 知识点管理

### 试卷与考试系统
- 试卷管理 (PaperManage)
- 考试安排 (ExamManage)
- 成绩管理 (Score)
- 错题本 (Wrong)

### AI 智能助手
- 集成阿里云通义千问大模型
- 智能问答功能
- 聊天记录保存

### 其他功能
- 消息系统 (Message)
- 音频文件管理 (AudioFiles)
- OCR 文字识别（Tess4J）
- 文件上传（图片、音频）

## 技术架构

### 后端技术栈

| 组件 | 技术/版本 | 说明 |
|-----|----------|------|
| 框架 | Spring Boot 2.3.7 | Web 应用框架 |
| ORM | MyBatis Plus 3.4.2 | 数据库访问 |
| 数据库 | MySQL 5.7+ | 数据存储 |
| 缓存 | Spring Data Redis | 缓存支持 |
| 安全 | Spring Security + JWT | 身份认证 |
| API 文档 | Swagger 2.9.2 | 自动生成 API 文档 |
| AI | Spring AI + 阿里云 Dashscope | 通义千问集成 |
| OCR | Tess4J 4.5.4 | 图像文字识别 |
| 工具库 | Hutool 5.8.20 | Java 工具集 |
| JSON | FastJSON 1.2.62 | JSON 处理 |
| 分页 | PageHelper 1.4.6 | 分页插件 |

### 项目结构

```
cn.gdsdxy.questionbank/
├── BankQuestion.java              # 应用启动类
├── config/                        # 配置类
│   ├── GlobalCorsConfig.java      # CORS 跨域配置
│   ├── MyWebConfig.java           # Web 配置
│   ├── SwaggerConfig.java         # Swagger 配置
│   └── DashscopeConfig.java       # 阿里云 Dashscope 配置
├── constant/                      # 常量定义
│   ├── JwtConstant.java
│   └── GenerateCSV.java
├── controller/                    # REST 控制器（19个）
│   ├── LoginController.java       # 登录认证
│   ├── AdminController.java       # 管理员管理
│   ├── StudentController.java     # 学生管理
│   ├── TeacherController.java     # 教师管理
│   ├── MultiQuestionController.java  # 多选题
│   ├── FillQuestionController.java   # 填空题
│   ├── JudgeQuestionController.java  # 判断题
│   ├── ListenQuestionController.java # 听力题
│   ├── PaperManageController.java    # 试卷管理
│   ├── ExamManageController.java     # 考试管理
│   ├── ScoreController.java          # 成绩管理
│   ├── WrongController.java          # 错题管理
│   ├── MessageController.java        # 消息管理
│   ├── ReplayController.java         # 回复管理
│   ├── KnowledgeController.java      # 知识点管理
│   ├── AudioFilesController.java     # 音频文件管理
│   └── AiController.java             # AI 助手
├── dto/                           # 数据传输对象
├── entity/                        # 实体类
│   ├── admin/                     # 用户实体
│   │   ├── Admin.java
│   │   ├── Teacher.java
│   │   └── Student.java
│   ├── manage/                    # 管理实体
│   │   ├── Score.java
│   │   ├── PaperManage.java
│   │   ├── ExamManage.java
│   │   └── ...
│   ├── questions/                 # 题目实体
│   ├── Message.java
│   ├── AudioFiles.java
│   └── ChatRecord.java
├── fliter/                        # 过滤器
│   └── JwtAuthenticationFilter.java  # JWT 认证过滤器
├── mapper/                        # MyBatis Plus Mapper
├── repository/                    # 数据仓库
├── service/                       # 业务逻辑层（18个服务）
├── util/                          # 工具类
│   ├── JwtUtil.java               # JWT 工具
│   └── ApiResultHandler.java      # API 响应处理
└── xml/                           # MyBatis XML 映射文件
```

## 环境要求

- **JDK**: 1.8+
- **Maven**: 3.6+
- **MySQL**: 5.7+
- **Redis**: 5.0+ (可选，用于缓存)
- **IDE**: IntelliJ IDEA / Eclipse

## 本地开发环境搭建

### 1. 克隆项目

```bash
git clone <repository-url>
cd Design
```

### 2. 数据库配置

创建 MySQL 数据库：

```sql
CREATE DATABASE exam DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 修改配置文件

编辑 `src/main/resources/application.properties`：

```properties
# 数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/exam?serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=your_password

# 服务端口
server.port=5555

# 阿里云 Dashscope API 配置（通义千问）
dashscope.api-key=your_api_key
dashscope.model=qwen-plus

# 图片上传路径
image.upload.path=D:\\Images
```

### 4. 编译运行

```bash
cd Bank-question
mvn clean install
mvn spring-boot:run
```

或者直接运行主类：`cn.gdsdxy.questionbank.BankQuestion`

### 5. 验证启动

- 应用地址: http://localhost:5555
- Swagger API 文档: http://localhost:5555/swagger-ui.html

## 配置说明

### 数据库配置

| 配置项 | 说明 | 默认值 |
|-------|------|-------|
| spring.datasource.url | 数据库连接 URL | localhost:3306/exam |
| spring.datasource.username | 数据库用户名 | root |
| spring.datasource.password | 数据库密码 | 123456 |

### 服务配置

| 配置项 | 说明 | 默认值 |
|-------|------|-------|
| server.port | 服务端口 | 5555 |
| spring.application.name | 应用名称 | Shopping |

### 文件上传配置

| 配置项 | 说明 | 默认值 |
|-------|------|-------|
| spring.servlet.multipart.max-file-size | 单文件最大大小 | 20MB |
| spring.servlet.multipart.max-request-size | 请求最大大小 | 200MB |
| image.upload.path | 图片上传路径 | D:\\Images |

### AI 配置

| 配置项 | 说明 | 默认值 |
|-------|------|-------|
| dashscope.api-key | 阿里云 API Key | - |
| dashscope.model | AI 模型 | qwen-plus |
| dashscope.api-url | API 地址 | 阿里云 Dashscope |

## 主要模块说明

### 认证模块 (LoginController)
- 用户登录（管理员/教师/学生）
- JWT Token 生成和验证

### 题库模块
- 支持多种题型的 CRUD 操作
- 题目分类和知识点管理

### 试卷考试模块
- 试卷组卷
- 考试安排和发布
- 在线考试
- 自动评分

### AI 助手模块 (AiController)
- 集成通义千问大模型
- 智能问答
- 聊天记录保存

## 相关文档

- [部署指南](./DEPLOY.md)
- [API 文档](./API.md)
- [HELP.md](./HELP.md) - Spring Boot 参考

## 常见问题

### 1. 数据库连接失败
检查 MySQL 服务是否启动，用户名密码是否正确。

### 2. AI 功能不可用
请确认已配置有效的阿里云 Dashscope API Key。

### 3. 文件上传失败
检查 `image.upload.path` 配置的目录是否存在且有写入权限。
