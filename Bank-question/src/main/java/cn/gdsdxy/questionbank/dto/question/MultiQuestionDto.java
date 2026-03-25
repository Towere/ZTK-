package cn.gdsdxy.questionbank.dto.question;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("选择题页面数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MultiQuestionDto {

    private String question;

    private String rightAnswer;

    private Integer score;
}
