package cn.gdsdxy.questionbank.dto.question;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("填空题页面数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FillQuestionDto {

    private String question;

    private String answer;

    private Integer score;
}
