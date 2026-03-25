package cn.gdsdxy.questionbank.controller;

import cn.gdsdxy.questionbank.entity.ChatRecord;
import cn.gdsdxy.questionbank.mapper.ChatRecordMapper;
import cn.gdsdxy.questionbank.service.DashscopeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final DashscopeService dashscopeService;
    private final ChatRecordMapper chatRecordMapper;

    /**
     * 处理AI聊天请求
     */
    // 修改 AiController.java 的 chat 方法
    @PostMapping("/chat")
    public ResponseEntity<String> chat(
            @RequestBody Map<String, String> requestBody,
            // 关键修改：允许请求头为空并设置默认值，方便调试
            @RequestHeader(value = "X-User-Id", required = false) String userIdStr
    ) {
        // 1. 验证并转换用户ID
        Long userId;
        try {
            // 检查请求头是否存在
            if (userIdStr == null || userIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("请求头缺少X-User-Id");
            }
            // 转换为Long类型（前端可能传递字符串，需处理类型转换）
            userId = Long.valueOf(userIdStr.trim());
        } catch (NumberFormatException e) {
            log.error("用户ID格式错误: {}", userIdStr, e);
            return ResponseEntity.badRequest().body("用户ID格式错误");
        } catch (IllegalArgumentException e) {
            log.error("用户ID缺失", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        // 2. 处理业务逻辑
        String question = requestBody.get("newMessage");
        try {
            String answer = dashscopeService.getAnswer(question);
            // 保存聊天记录（使用转换后的userId）
            saveChatRecord(userId, question, answer);
            System.out.println("AI回答: " + answer);
            return ResponseEntity.ok(answer);
        } catch (Exception e) {
            log.error("处理AI聊天请求失败", e);
            return ResponseEntity.badRequest().body("请求失败: " + e.getMessage());
        }
    }


    /**
     * 保存聊天记录
     */
    private void saveChatRecord(Long userId, String question, String answer) {
        ChatRecord record = new ChatRecord();
        record.setUserId(userId);
        record.setQuestion(question);
        record.setAnswer(answer);
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        chatRecordMapper.insert(record);
    }

    /**
     * 获取聊天历史记录
     */
    @GetMapping("/history")
    public ResponseEntity<?> getChatHistory(
            // 从请求头获取当前登录用户ID（与chat方法保持一致的参数名和校验逻辑）
            @RequestHeader(value = "X-User-Id", required = false) String userIdStr
    ) {
        // 1. 验证并转换用户ID（复用chat方法中的校验逻辑）
        Long userId;
        try {
            if (userIdStr == null || userIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("请求头缺少X-User-Id");
            }
            userId = Long.valueOf(userIdStr.trim());
        } catch (NumberFormatException e) {
            log.error("用户ID格式错误: {}", userIdStr, e);
            return ResponseEntity.badRequest().body("用户ID格式错误");
        } catch (IllegalArgumentException e) {
            log.error("用户ID缺失", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        // 2. 根据当前登录用户ID查询聊天记录（不分页，按时间倒序）
        return ResponseEntity.ok(chatRecordMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<ChatRecord>()
                        .eq("user_id", userId)
                        .orderByAsc("create_time")
        ));
    }

}