package cn.gdsdxy.questionbank.entity.questions;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("question")
public class Question {
    @TableId(type = IdType.AUTO)
    private Long id;  // 题目ID

    private String content;  // 题目内容

    private Integer difficulty;  // 难度(1:容易,2:中等,3:困难)

    private Long knowledgeId;  // 知识点ID

    private Integer score;  // 分值

    private String optionA;  // 选项A

    private String optionB;  // 选项B

    private String optionC;  // 选项C

    private String optionD;  // 选项D

    private String correctAnswer;  // 正确答案

    private Integer isValid;  // 是否有效(1:有效)

}