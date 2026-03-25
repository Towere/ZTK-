package cn.gdsdxy.questionbank.dto.Total_usersDto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("用户页面数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Total_users {
    private Integer user;
    private String pwd;
}
