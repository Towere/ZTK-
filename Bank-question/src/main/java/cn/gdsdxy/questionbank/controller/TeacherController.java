package cn.gdsdxy.questionbank.controller;

import cn.gdsdxy.questionbank.constant.JwtConstant;
import cn.gdsdxy.questionbank.dto.base.Result;
import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.entity.admin.Student;
import cn.gdsdxy.questionbank.entity.admin.Teacher;
import cn.gdsdxy.questionbank.service.TeacherService;
import com.github.pagehelper.PageInfo;
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

@Api(tags = "跟教师相关的控制器")
@RestController
@RequestMapping("/api")
public class TeacherController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    TeacherService teacherService;
    @ApiOperation("教师")
    @ApiImplicitParams(@ApiImplicitParam(name = "Teacher",value = "教师对象",required = true) )
    @PostMapping("/TeacherLogin")
    public Result login(@RequestBody Teacher teacher){
//        try {
            String token = teacherService.login(teacher);
//            if(!token.isEmpty()){
                teacherService.login(teacher);
                return new Result().ok("登录成功",token);
//            }else {
//                studentService.login(loginDto);
//                return new Result().ok("用户名密码错误",token);
//            }

//        }catch (Exception e){
//            return new Result().serverError("有异常产生，登录失败");
//        }
    }
    @PostMapping("/TeacherRegister")
    public Result register(@RequestBody Teacher teacher) throws Exception {
        try{
            if(teacherService.register(teacher)>0){
                return new Result().ok("添加成功");
            }else {
                return new Result().ok("添加失败");
            }
        }catch (Exception e){
            return new Result().serverError("添加失败",e.getMessage());
        }
    }

    @PostMapping ("/TeacherLogout")
    public Result logout(HttpServletRequest request){
        String token = request.getHeader(JwtConstant.HEADER_NAME);
        try{
            teacherService.logout(token);
            return new Result().ok("注销成功");
        }catch (Exception e){
            return  new Result().serverError("注销成功",e.getMessage());
        }

    }

    @PostMapping("/UpdateTeacher")
    public Result UpdateTeacher(@RequestBody Teacher teacher){
        try {
            if(teacherService.UpdateTeacher(teacher)>0){
                return new Result().ok("修改成功");
            }
            else {
                return new Result().serverError("修改失败");
            }
        }catch (Exception e){
            return new Result().serverError("id冲突");
        }
    }

    @PostMapping("/DeleteTeacherId")
    public Result DeleteTeacherId(@RequestBody Teacher teacher){
        try{
            teacherService.DeleteTeacherId(teacher.getTeacherId());
            return new Result().ok("注销成功");
        }catch (Exception e){
            return new Result().serverError("注销失败");
        }
    }

    @PostMapping("/selectTeacher")
    public Result selectTeacher(@RequestBody SearchPageDto searchPageDto){
        try {
            PageInfo<Teacher> teacherPageInfo = teacherService.getAllTeacher(searchPageDto);
            return new Result().ok("执行成功", teacherPageInfo);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }
}
