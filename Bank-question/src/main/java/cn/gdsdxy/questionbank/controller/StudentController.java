package cn.gdsdxy.questionbank.controller;

import cn.gdsdxy.questionbank.constant.JwtConstant;
import cn.gdsdxy.questionbank.dto.base.Result;
import cn.gdsdxy.questionbank.dto.manage.ExamDto;
import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.entity.admin.Student;
import cn.gdsdxy.questionbank.entity.admin.Teacher;
import cn.gdsdxy.questionbank.entity.manage.ExamManage;
import cn.gdsdxy.questionbank.service.StudentService;
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
import java.util.List;

@Api(tags = "跟学生相关的控制器")
@RestController
@RequestMapping("/api")
public class StudentController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StudentService studentService;
    @ApiOperation("学生")
    @ApiImplicitParams(@ApiImplicitParam(name = "Student",value = "学生对象",required = true) )
    @PostMapping("/StudentLogin")
    public Result login(@RequestBody Student student){
//        try {
            String token = studentService.login(student);
//            if(!token.isEmpty()){
                studentService.login(student);
                return new Result().ok("登录成功",token);
//            }else {
//                studentService.login(loginDto);
//                return new Result().ok("用户名密码错误",token);
//            }

//        }catch (Exception e){
//            return new Result().serverError("有异常产生，登录失败");
//        }
    }
    @PostMapping("/StudentRegister")
    public Result register(@RequestBody Student student) throws Exception {
        try{
            if(studentService.register(student)>0){
                return new Result().ok("添加成功");
            }else {
                return new Result().ok("添加失败");
            }
        }catch (Exception e){
            return new Result().serverError("添加失败",e.getMessage());
        }
    }

    @PostMapping ("/StudentLogout")
    public Result logout(HttpServletRequest request){
        String token = request.getHeader(JwtConstant.HEADER_NAME);
        try{
            studentService.logout(token);
            return new Result().ok("注销成功");
        }catch (Exception e){
            return  new Result().serverError("注销成功",e.getMessage());
        }
    }

    @PostMapping("/UpdateStudent")
    public Result UpdateStudent(@RequestBody Student student){
        try {
            if(studentService.UpdateStudent(student)>0){
                return new Result().ok("修改成功");
            }
            else {
                return new Result().serverError("修改失败");
            }
        }catch (Exception e){
            return new Result().serverError("id冲突");
        }
    }

    @PostMapping("/selectStudent")
    public Result selectStudent(@RequestBody SearchPageDto searchPageDto){
        try {
            PageInfo<Student> studentPageInfo = studentService.getAllStudent(searchPageDto);
            return new Result().ok("执行成功", studentPageInfo);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }

    @PostMapping("/DeleteStudentId")
    public Result DeleteStudentId(@RequestBody Student student){
        try{
            studentService.DeleteStudentId(student.getStudentId());
            return new Result().ok("注销成功");
        }catch (Exception e){
            return new Result().serverError("注销失败");
        }
    }
}
