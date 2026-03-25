package cn.gdsdxy.questionbank.dto.manage;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("页面数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ExamDto {

    private String description;

    private String source;

    private String examDate;

    private Integer totalTime;

    private Integer totalScore;
}
