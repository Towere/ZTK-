package cn.gdsdxy.questionbank.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@ApiModel("所有评论数")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MessageDto {

    private Integer id;
    private String title;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date time;
    private String replay;
    private Date replayTime;
}
