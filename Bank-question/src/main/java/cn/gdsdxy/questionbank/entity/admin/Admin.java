package cn.gdsdxy.questionbank.entity.admin;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("管理员表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("admin")
public class Admin {
    @ApiModelProperty("管理员id")
    @TableId
    private Integer adminId;

    private String adminName;

    private String sex;

    private String tel;

    private String email;

    private String pwd;

    private String cardId;

    private String role;


}
