package cn.gdsdxy.questionbank.entity.admin;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("教师表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("teacher")
public class Teacher {
    @ApiModelProperty("教师id")
    @TableId
    private Integer teacherId;

    private String teacherName;

    private String institute;

    private String sex;

    private String tel;

    private String email;

    private String pwd;

    private String cardId;

    private String type;

    private String role;

}
