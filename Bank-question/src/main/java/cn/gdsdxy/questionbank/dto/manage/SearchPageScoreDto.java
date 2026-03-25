package cn.gdsdxy.questionbank.dto.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@ApiModel("统一查询成绩分页Dto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SearchPageScoreDto implements Serializable {

    @ApiModelProperty("学生id")
    private Integer studentId;

    @ApiModelProperty("页码")
    private Integer page;

    @ApiModelProperty("页大小")
    private Integer size;

    private String subject; // 新增：课程名称
    private Integer minScore; // 新增：最低分数（用于及格筛选）
    private Integer maxScore; // 新增：最高分数（用于不及格筛选）


}
