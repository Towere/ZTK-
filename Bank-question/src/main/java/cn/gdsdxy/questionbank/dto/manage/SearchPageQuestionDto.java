package cn.gdsdxy.questionbank.dto.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@ApiModel("统一查询问题分页Dto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SearchPageQuestionDto implements Serializable {

    @ApiModelProperty("页码")
    private Integer page;

    @ApiModelProperty("页大小")
    private Integer size;

    @ApiModelProperty("paperId")
    private Integer paperId;


    /**
     * 功能描述：前端是从第1页开始算起。后端的分页查询是第0页算期。
     *
     * @return
     */
//    public Integer getPage() {
//        return page >= 1 ? page - 1 : page;
//    }

}
