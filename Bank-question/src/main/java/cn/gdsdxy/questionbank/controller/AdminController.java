package cn.gdsdxy.questionbank.controller;

import cn.gdsdxy.questionbank.constant.JwtConstant;
import cn.gdsdxy.questionbank.dto.base.Result;
import cn.gdsdxy.questionbank.entity.admin.Admin;
import cn.gdsdxy.questionbank.service.AdminService;
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

@Api(tags = "跟管理员相关的控制器")
@RestController
@RequestMapping("/api")
public class AdminController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    AdminService adminService;
    @ApiOperation("登录")
    @ApiImplicitParams(@ApiImplicitParam(name = "Admin",value = "登录对象",required = true) )
    @PostMapping("/AdminLogin")
    public Result login(@RequestBody Admin admin){
//        try {
            String token = adminService.login(admin);
//            if(!token.isEmpty()){
                adminService.login(admin);
                return new Result().ok("登录成功",token);
//            }else {
//                studentService.login(loginDto);
//                return new Result().ok("用户名密码错误",token);
//            }

//        }catch (Exception e){
//            return new Result().serverError("有异常产生，登录失败");
//        }
    }

    @PostMapping ("/AdminLogout")
    public Result logout(HttpServletRequest request){
        String token = request.getHeader(JwtConstant.HEADER_NAME);
        try{
            adminService.logout(token);
            return new Result().ok("注销成功");
        }catch (Exception e){
            return  new Result().serverError("注销成功",e.getMessage());
        }

    }
}
