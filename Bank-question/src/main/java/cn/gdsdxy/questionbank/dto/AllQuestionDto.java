package cn.gdsdxy.questionbank.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("所有题目数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AllQuestionDto {

    private String question;
    private String subject;
    private Integer score;
    private String section;
    private String level;
    private String type;
    private Integer questionType;
    private Integer questionId;
}
