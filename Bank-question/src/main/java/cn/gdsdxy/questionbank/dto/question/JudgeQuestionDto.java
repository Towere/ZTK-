package cn.gdsdxy.questionbank.dto.question;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("判断题页面数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class JudgeQuestionDto {

    private String question;

    private String answer;

    private Integer score;
}
