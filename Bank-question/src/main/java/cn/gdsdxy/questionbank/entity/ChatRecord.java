package cn.gdsdxy.questionbank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 聊天记录实体类
 * 对应数据库表 chat_record
 */
@Data
public class ChatRecord {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID（关联用户表）
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 用户提问内容
     */
    @TableField("question")
    private String question;

    /**
     * AI回答内容
     */
    @TableField("answer")
    private String answer;

    /**
     * 记录创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 记录更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
}