package cn.gdsdxy.questionbank.entity.questions;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("选择题")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("multi_question")
public class MultiQuestion {
    @TableId
    private Integer questionId;

    private String subject;

    private String knowledgeId;

    private String answerA;

    private String answerB;

    private String answerC;

    private String answerD;

    private String question;

    private String difficulty;

    private String rightAnswer;

    private String analysis; //题目解析

    private Integer score;

}
