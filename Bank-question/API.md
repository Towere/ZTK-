# API 接口文档

## 概述

Bank-question 系统提供 RESTful API 接口，使用 JSON 格式进行数据交互。

- **Base URL**: `http://localhost:5555`
- **数据格式**: JSON
- **字符编码**: UTF-8
- **认证方式**: JWT Token

## 快速开始

### 1. 访问 Swagger UI

启动服务后，访问以下地址查看交互式 API 文档：

```
http://localhost:5555/swagger-ui.html
```

Swagger UI 提供了完整的 API 接口列表、参数说明、请求示例，并支持在线调试。

### 2. 认证流程

#### 登录获取 Token

```http
POST /user/login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456",
  "role": "admin"
}
```

**响应示例**：

```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "user": {
      "id": 1,
      "username": "admin",
      "role": "admin"
    }
  }
}
```

#### 使用 Token 访问受保护接口

在请求头中携带 Token：

```http
GET /admin/list
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## API 模块列表

| 模块 | 控制器 | 说明 |
|-----|-------|------|
| 认证登录 | LoginController | 用户登录、Token 管理 |
| 管理员 | AdminController | 管理员信息管理 |
| 教师 | TeacherController | 教师信息管理 |
| 学生 | StudentController | 学生信息管理 |
| 多选题 | MultiQuestionController | 多选题 CRUD |
| 填空题 | FillQuestionController | 填空题 CRUD |
| 判断题 | JudgeQuestionController | 判断题 CRUD |
| 听力题 | ListenQuestionController | 听力题 CRUD |
| 试卷管理 | PaperManageController | 试卷组卷、管理 |
| 考试管理 | ExamManageController | 考试安排、发布 |
| 成绩管理 | ScoreController | 成绩查询、统计 |
| 错题本 | WrongController | 错题收集、复习 |
| 消息 | MessageController | 消息发送、接收 |
| 回复 | ReplayController | 消息回复 |
| 知识点 | KnowledgeController | 知识点管理 |
| 音频文件 | AudioFilesController | 音频上传、管理 |
| AI 助手 | AiController | 智能问答 |

## 通用响应格式

所有 API 接口统一使用以下响应格式：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

| 字段 | 类型 | 说明 |
|-----|------|------|
| code | int | 状态码，200 表示成功 |
| message | string | 提示信息 |
| data | object/array | 返回数据 |

### 状态码说明

| Code | 说明 |
|------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未授权（Token 无效或过期） |
| 403 | 禁止访问 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 核心接口示例

### 1. 认证模块

#### 用户登录

```http
POST /user/login
Content-Type: application/json

{
  "username": "string",
  "password": "string",
  "role": "admin|teacher|student"
}
```

**Role 参数值**：
- `admin` - 管理员
- `teacher` - 教师
- `student` - 学生

### 2. 题库管理 - 多选题

#### 获取多选题列表（分页）

```http
GET /multiQuestion/page?pageNum=1&pageSize=10
Authorization: Bearer <token>
```

#### 新增多选题

```http
POST /multiQuestion/add
Authorization: Bearer <token>
Content-Type: application/json

{
  "question": "题目内容",
  "optionA": "选项A",
  "optionB": "选项B",
  "optionC": "选项C",
  "optionD": "选项D",
  "answer": "A",
  "score": 5,
  "level": "1",
  "section": "章节"
}
```

#### 修改多选题

```http
PUT /multiQuestion/update
Authorization: Bearer <token>
Content-Type: application/json

{
  "id": 1,
  "question": "修改后的题目内容",
  "answer": "B"
}
```

#### 删除多选题

```http
DELETE /multiQuestion/delete/{id}
Authorization: Bearer <token>
```

### 3. 题库管理 - 填空题

#### 新增填空题

```http
POST /fillQuestion/add
Authorization: Bearer <token>
Content-Type: application/json

{
  "question": "题目内容，使用____表示填空",
  "answer": "答案",
  "score": 5
}
```

### 4. 题库管理 - 判断题

#### 新增判断题

```http
POST /judgeQuestion/add
Authorization: Bearer <token>
Content-Type: application/json

{
  "question": "题目内容",
  "answer": "true",
  "score": 2
}
```

### 5. 试卷管理

#### 创建试卷

```http
POST /paperManage/add
Authorization: Bearer <token>
Content-Type: application/json

{
  "paperName": "期中测试",
  "questionType": "1,2,3",
  "questionIds": "1,2,3,4,5",
  "score": "5,5,5,10,10",
  "duration": 90
}
```

#### 获取试卷列表

```http
GET /paperManage/list
Authorization: Bearer <token>
```

#### 获取试卷详情

```http
GET /paperManage/detail/{id}
Authorization: Bearer <token>
```

### 6. 考试管理

#### 创建考试

```http
POST /examManage/add
Authorization: Bearer <token>
Content-Type: application/json

{
  "examName": "期中考试",
  "paperId": 1,
  "startTime": "2024-01-15 09:00:00",
  "endTime": "2024-01-15 11:00:00"
}
```

#### 学生参加考试

```http
POST /examManage/join/{examId}
Authorization: Bearer <token>
```

#### 提交答案

```http
POST /examManage/submit
Authorization: Bearer <token>
Content-Type: application/json

{
  "examId": 1,
  "paperId": 1,
  "answers": [
    {"questionId": 1, "answer": "A"},
    {"questionId": 2, "answer": "B"}
  ]
}
```

### 7. 成绩管理

#### 查询学生成绩

```http
GET /score/student/{studentId}
Authorization: Bearer <token>
```

#### 查询考试成绩列表

```http
GET /score/exam/{examId}
Authorization: Bearer <token>
```

#### 成绩统计

```http
GET /score/statistics/{examId}
Authorization: Bearer <token>
```

### 8. 错题本

#### 获取学生错题列表

```http
GET /wrong/student/{studentId}
Authorization: Bearer <token>
```

#### 添加错题

```http
POST /wrong/add
Authorization: Bearer <token>
Content-Type: application/json

{
  "studentId": 1,
  "questionId": 1,
  "questionType": "multi",
  "userAnswer": "A",
  "correctAnswer": "B"
}
```

### 9. AI 助手

#### 发送问题给 AI

```http
POST /ai/chat
Authorization: Bearer <token>
Content-Type: application/json

{
  "message": "请解释一下什么是递归算法？",
  "conversationId": "optional-conversation-id"
}
```

**响应**：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "reply": "递归算法是一种直接或间接调用自身函数的算法...",
    "conversationId": "conv-123456"
  }
}
```

#### 获取聊天记录

```http
GET /ai/history?conversationId=conv-123456
Authorization: Bearer <token>
```

### 10. 文件上传

#### 上传图片

```http
POST /file/uploadImage
Authorization: Bearer <token>
Content-Type: multipart/form-data

file: [二进制文件]
```

**响应**：

```json
{
  "code": 200,
  "message": "上传成功",
  "data": {
    "url": "/mystatic/images/xxx.jpg",
    "fileName": "xxx.jpg"
  }
}
```

#### 上传音频

```http
POST /audioFiles/upload
Authorization: Bearer <token>
Content-Type: multipart/form-data

file: [二进制音频文件]
```

## 分页查询参数

分页接口通用参数：

| 参数 | 类型 | 必填 | 说明 |
|-----|------|------|------|
| pageNum | int | 是 | 页码，从 1 开始 |
| pageSize | int | 是 | 每页记录数 |
| keyword | string | 否 | 搜索关键词 |

**分页响应示例**：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [],
    "total": 100,
    "size": 10,
    "current": 1,
    "pages": 10
  }
}
```

## 错误示例

### Token 过期或无效

```json
{
  "code": 401,
  "message": "Token 已过期或无效",
  "data": null
}
```

### 参数验证失败

```json
{
  "code": 400,
  "message": "参数验证失败：question 不能为空",
  "data": null
}
```

### 资源不存在

```json
{
  "code": 404,
  "message": "题目不存在",
  "data": null
}
```

## 开发建议

1. **使用 Swagger UI**：建议在开发阶段使用 Swagger UI 进行接口调试
2. **统一错误处理**：客户端应统一处理 code 非 200 的情况
3. **Token 刷新**：在 Token 即将过期前，使用刷新 Token 或重新登录获取新 Token
4. **文件上传**：注意文件大小限制，大文件建议分片上传
5. **分页**：列表接口都应使用分页，避免一次性加载大量数据

## 更多信息

- 完整的接口文档请启动项目后访问：`http://localhost:5555/swagger-ui.html`
- 部署相关请参考：[DEPLOY.md](./DEPLOY.md)
- 项目介绍请参考：[README.md](./README.md)
