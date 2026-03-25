# 部署指南

本文档介绍如何将 Bank-question 系统部署到生产环境。

## 目录

- [环境准备](#环境准备)
- [数据库初始化](#数据库初始化)
- [配置文件](#配置文件)
- [编译打包](#编译打包)
- [部署运行](#部署运行)
- [Nginx 反向代理（可选）](#nginx-反向代理可选)
- [常见问题](#常见问题)

## 环境准备

### 服务器要求

| 组件 | 最低配置 | 推荐配置 |
|-----|---------|---------|
| CPU | 2 核 | 4 核+ |
| 内存 | 4 GB | 8 GB+ |
| 磁盘 | 20 GB | 50 GB+ |
| 操作系统 | Linux / Windows | Linux (CentOS/Ubuntu) |

### 软件环境

- **JDK**: 1.8 或更高版本
- **MySQL**: 5.7 或 8.0+
- **Redis**: 5.0+ (可选)
- **Maven**: 3.6+ (仅编译时需要)

### 安装 JDK

#### Linux (Ubuntu/Debian)
```bash
sudo apt-get update
sudo apt-get install openjdk-8-jdk
java -version
```

#### Linux (CentOS)
```bash
sudo yum install java-1.8.0-openjdk
java -version
```

#### Windows
下载并安装 JDK 8，配置 JAVA_HOME 环境变量。

### 安装 MySQL

#### Linux (Ubuntu)
```bash
sudo apt-get install mysql-server
sudo systemctl start mysql
sudo systemctl enable mysql
```

#### Linux (CentOS)
```bash
sudo yum install mysql-server
sudo systemctl start mysqld
sudo systemctl enable mysqld
```

#### 安全配置
```bash
sudo mysql_secure_installation
```

### 安装 Redis (可选)

#### Linux (Ubuntu)
```bash
sudo apt-get install redis-server
sudo systemctl start redis
sudo systemctl enable redis
```

## 数据库初始化

### 1. 创建数据库

```sql
CREATE DATABASE exam DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建数据库用户（可选）
CREATE USER 'exam_user'@'localhost' IDENTIFIED BY 'your_secure_password';
GRANT ALL PRIVILEGES ON exam.* TO 'exam_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2. 导入表结构

项目启动时，MyBatis Plus 会自动根据实体类创建表结构（如果配置了相应策略）。

如需手动初始化，请查看项目中的 SQL 脚本（如有）。

### 3. 验证数据库

```bash
mysql -u root -p
USE exam;
SHOW TABLES;
```

## 配置文件

### 创建生产环境配置

复制 `application.properties` 为 `application-prod.properties`：

```bash
cd src/main/resources
cp application.properties application-prod.properties
```

### 生产环境配置示例

编辑 `application-prod.properties`：

```properties
# 应用名称
spring.application.name=BankQuestion

# 数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/exam?serverTimezone=Asia/Shanghai&useSSL=true
spring.datasource.username=exam_user
spring.datasource.password=your_secure_password

# 连接池配置（生产环境建议添加）
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000

# MyBatis Plus 配置
mybatis.configuration.map-underscore-to-camel-case=false
mybatis-plus.configuration.map-underscore-to-camel-case=false
mybatis-plus.global-config.db-config.id-type=auto

# 服务端口
server.port=5555

# 文件上传配置
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=20MB
spring.servlet.multipart.max-request-size=200MB

# 图片上传路径（确保目录存在）
image.upload.path=/data/images

# 静态资源配置
spring.resources.static-locations=classpath:/static/imageHand/,classpath:/static/imageFly/,classpath:/META-INF/resources/, classpath:/resources/, classpath:/static/, classpath:/public/
spring.mvc.static-path-pattern=/mystatic/**

# 阿里云 Dashscope 配置
dashscope.api-key=your_production_api_key
dashscope.model=qwen-plus
dashscope.api-url=https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation
spring.codec.max-in-memory-size=10MB

# 激活生产环境配置
spring.profiles.active=prod

# 日志配置（生产环境）
logging.level.root=INFO
logging.level.cn.gdsdxy.questionbank=INFO
logging.file.name=/var/log/bankquestion/application.log
```

### 敏感信息管理

生产环境建议使用环境变量或配置中心管理敏感信息：

```bash
# 方式一：环境变量
export DB_PASSWORD=your_secure_password
export DASHSCOPE_API_KEY=your_api_key

# 方式二：启动时传入参数
java -jar app.jar --spring.datasource.password=$DB_PASSWORD
```

## 编译打包

### 使用 Maven 打包

```bash
cd Bank-question
mvn clean package -DskipTests
```

打包完成后，会在 `target/` 目录下生成 JAR 文件：
- `Bank-question-0.0.1-SNAPSHOT.jar`

### 验证 JAR 文件

```bash
ls -lh target/Bank-question-0.0.1-SNAPSHOT.jar
```

## 部署运行

### 方式一：直接运行（测试用）

```bash
java -jar target/Bank-question-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### 方式二：后台运行（推荐）

#### 使用 nohup
```bash
nohup java -jar Bank-question-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod > app.log 2>&1 &
echo $! > app.pid
```

#### 查看日志
```bash
tail -f app.log
```

#### 停止服务
```bash
kill $(cat app.pid)
```

### 方式三：使用 Systemd 服务（Linux 生产环境推荐）

#### 创建服务文件
```bash
sudo vi /etc/systemd/system/bankquestion.service
```

#### 服务文件内容
```ini
[Unit]
Description=Bank Question System
After=network.target mysql.service redis.service

[Service]
Type=simple
User=www-data
WorkingDirectory=/opt/bankquestion
ExecStart=/usr/bin/java -jar /opt/bankquestion/Bank-question-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
Restart=always
RestartSec=10

# 环境变量
Environment="JAVA_OPTS=-Xms512m -Xmx2048m"
Environment="DB_PASSWORD=your_secure_password"
Environment="DASHSCOPE_API_KEY=your_api_key"

[Install]
WantedBy=multi-user.target
```

#### 启动服务
```bash
# 复制文件到部署目录
sudo mkdir -p /opt/bankquestion
sudo cp target/Bank-question-0.0.1-SNAPSHOT.jar /opt/bankquestion/
sudo chown -R www-data:www-data /opt/bankquestion

# 创建图片上传目录
sudo mkdir -p /data/images
sudo chown -R www-data:www-data /data/images

# 创建日志目录
sudo mkdir -p /var/log/bankquestion
sudo chown -R www-data:www-data /var/log/bankquestion

# 启动服务
sudo systemctl daemon-reload
sudo systemctl enable bankquestion
sudo systemctl start bankquestion

# 查看状态
sudo systemctl status bankquestion

# 查看日志
sudo journalctl -u bankquestion -f
```

## Nginx 反向代理（可选）

### 安装 Nginx

```bash
# Ubuntu/Debian
sudo apt-get install nginx

# CentOS
sudo yum install nginx
```

### 配置 Nginx

创建配置文件 `/etc/nginx/conf.d/bankquestion.conf`：

```nginx
server {
    listen 80;
    server_name your-domain.com;

    # 客户端上传文件大小限制
    client_max_body_size 200M;

    # 访问日志
    access_log /var/log/nginx/bankquestion_access.log;
    error_log /var/log/nginx/bankquestion_error.log;

    location / {
        proxy_pass http://localhost:5555;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;

        # WebSocket 支持（如需要）
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
    }

    # 静态资源（可选，直接由 Nginx 提供）
    location /mystatic/ {
        alias /opt/bankquestion/static/;
        expires 7d;
    }
}
```

### 启用 HTTPS（推荐）

使用 Let's Encrypt 免费证书：

```bash
sudo apt-get install certbot python3-certbot-nginx
sudo certbot --nginx -d your-domain.com
```

### 重启 Nginx

```bash
sudo nginx -t
sudo systemctl restart nginx
```

## 常见问题

### 1. 服务启动失败

**检查日志**
```bash
sudo journalctl -u bankquestion -n 100
```

**常见原因**
- 端口被占用：检查 5555 端口是否被其他服务占用
- 数据库连接失败：确认 MySQL 服务运行，连接信息正确
- 权限问题：确认应用对部署目录和数据目录有读写权限

### 2. 数据库连接问题

**测试连接**
```bash
mysql -h localhost -u exam_user -p exam
```

**检查防火墙**
```bash
# Ubuntu
sudo ufw status

# CentOS
sudo firewall-cmd --list-all
```

### 3. 文件上传失败

**检查上传目录权限**
```bash
ls -ld /data/images
sudo chmod 755 /data/images
sudo chown -R www-data:www-data /data/images
```

### 4. 内存不足

**调整 JVM 内存参数**
```ini
# 在 systemd 服务文件中修改
Environment="JAVA_OPTS=-Xms256m -Xmx1024m"
```

### 5. AI 功能不工作

**检查 API Key 配置**
- 确认 API Key 有效且未过期
- 检查网络连接能否访问阿里云服务
- 查看应用日志中的错误信息

## 健康检查

部署完成后，进行以下验证：

```bash
# 检查服务端口
curl -I http://localhost:5555

# 检查 Swagger 文档
curl -I http://localhost:5555/swagger-ui.html

# 检查数据库连接（通过应用日志）
tail -f /var/log/bankquestion/application.log
```

## 备份策略

### 数据库备份

```bash
# 创建备份脚本
cat > /opt/backup/backup-db.sh << 'EOF'
#!/bin/bash
BACKUP_DIR="/opt/backup/mysql"
DATE=$(date +%Y%m%d_%H%M%S)
mkdir -p $BACKUP_DIR
mysqldump -u root -p'your_password' exam | gzip > $BACKUP_DIR/exam_$DATE.sql.gz
# 保留最近 7 天的备份
find $BACKUP_DIR -name "exam_*.sql.gz" -mtime +7 -delete
EOF

chmod +x /opt/backup/backup-db.sh

# 添加到 crontab，每天凌晨 2 点备份
(crontab -l ; echo "0 2 * * * /opt/backup/backup-db.sh") | crontab -
```

### 上传文件备份

```bash
# 同步到备份服务器
rsync -avz /data/images/ user@backup-server:/backup/images/
```
