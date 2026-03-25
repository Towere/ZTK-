package cn.gdsdxy.questionbank.controller;

import cn.gdsdxy.questionbank.constant.JwtConstant;
import cn.gdsdxy.questionbank.dto.ApiResult;
import cn.gdsdxy.questionbank.dto.Login;
import cn.gdsdxy.questionbank.dto.LoginResultDto;
import cn.gdsdxy.questionbank.dto.Total_usersDto.Total_users;
import cn.gdsdxy.questionbank.dto.base.Result;
import cn.gdsdxy.questionbank.entity.admin.Admin;
import cn.gdsdxy.questionbank.entity.admin.Student;
import cn.gdsdxy.questionbank.entity.admin.Teacher;
import cn.gdsdxy.questionbank.service.AdminService;
import cn.gdsdxy.questionbank.service.LoginService;
import cn.gdsdxy.questionbank.util.ApiResultHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "跟登录相关的控制器")
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    LoginService loginService;
    @Autowired
    AdminService adminService;

    @ApiOperation("登录")
    @ApiImplicitParams(@ApiImplicitParam(name = "Admin",value = "登录对象",required = true) )
    @PostMapping("/login")
    public Result login(@RequestBody Total_users total_users){
        try{
            LoginResultDto loginResult = loginService.getAllTotal_users(total_users); // 接收DTO
            if(loginResult!= null){
              return  new Result().ok("登录成功", loginResult );
            }else {
              return  new Result().ok("登录失败", null);
            }

        }catch (Exception e){
            return new Result().serverError("登录失败",e.getMessage());
        }
    }
}
