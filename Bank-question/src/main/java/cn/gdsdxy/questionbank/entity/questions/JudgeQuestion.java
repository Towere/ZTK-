package cn.gdsdxy.questionbank.entity.questions;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("判断题")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("judge_question")
public class JudgeQuestion {
    @TableId
    private Integer questionId;

    private String subject;

    private String question;

    private String answer;

    private String difficulty;

    private String knowledgeId;

    private Integer score;

    private String analysis; //题目解析
}
