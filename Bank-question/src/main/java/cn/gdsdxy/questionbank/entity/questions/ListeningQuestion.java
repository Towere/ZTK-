package cn.gdsdxy.questionbank.entity.questions;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ListeningQuestion {
    @TableId
    private Integer id;
    private Integer audioId; // 关联的音频ID
    private Integer questionNo; // 题目序号（1,2,3...）
    private String questionContent; // 题目内容
    private String optionA; // 选项A内容
    private String optionB; // 选项B内容
    private String optionC; // 选项C内容
    private Integer correctAnswer; // 正确答案（1:A,2:B,3:C）
    private Integer score; // 每题分值
}