package cn.gdsdxy.questionbank.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@ApiModel("学生表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("student")
public class Student {
    @ApiModelProperty("学生id")
    @TableId
    private Integer studentId;

    private String studentName;

    private String grade;

    private String major;

    private String clazz;

    private String institute;

    private String tel;

    private String email;

    private String pwd;

    private String cardId;

    private String sex;

    private String role;


}
