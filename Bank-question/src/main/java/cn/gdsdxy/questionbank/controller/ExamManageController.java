package cn.gdsdxy.questionbank.controller;

import cn.gdsdxy.questionbank.constant.JwtConstant;
import cn.gdsdxy.questionbank.dto.base.Result;
import cn.gdsdxy.questionbank.dto.manage.ExamCreateRequest;
import cn.gdsdxy.questionbank.dto.manage.ExamDto;
import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.entity.admin.Admin;
import cn.gdsdxy.questionbank.entity.admin.Student;
import cn.gdsdxy.questionbank.entity.admin.Teacher;
import cn.gdsdxy.questionbank.entity.manage.ExamManage;
import cn.gdsdxy.questionbank.repository.ExamManageRepository;
import cn.gdsdxy.questionbank.service.AdminService;
import cn.gdsdxy.questionbank.service.ExamManageService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "跟试卷相关的控制器")
@RestController
@RequestMapping("/api")
public class ExamManageController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ExamManageService examManageService;
    @ApiOperation("试卷")
    @ApiImplicitParams(@ApiImplicitParam(name = "ExamManage",value = "试卷对象",required = true) )
    @PostMapping("/ExamManage")
    public Result getExamByPage(@RequestBody SearchPageDto searchPageDto){
        try {
            PageInfo<ExamDto> examManageList = examManageService.getExamDto(searchPageDto);
            return new Result().ok("执行成功", examManageList);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }

    @PostMapping("/selectExamManage")
    public Result getAllExam(@RequestBody SearchPageDto searchPageDto){
        try {
            PageInfo<ExamManage> examManageList = examManageService.getAllExam(searchPageDto);
            return new Result().ok("执行成功", examManageList);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }

    @PostMapping("/selectExam")
    public Result getExam(){
        try {
            List<ExamDto> examDtoList = examManageService.getExam();
            return new Result().ok("执行成功", examDtoList);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }

    @PostMapping("/insertExamManage")
    public Result insertExamManage(@RequestBody ExamManage examManage) {
        try {
            examManageService.addExamManage(examManage);
            return new Result().ok("插入成功", examManage);
        } catch (Exception e) {
            return new Result().serverError("插入失败", e.getMessage());
        }
    }

    @PostMapping("/search-exams")
    public List<ExamManage> searchExams(@RequestBody String key) {
        // 根据关键字搜索试卷
        System.out.println(key);
        return examManageService.searchExams(key);
    }

    @PostMapping("/DeleteExamManage")
    public Result DeleteExamManageId(@RequestBody ExamManage examManage){
        try{
            examManageService.DeleteExamManageId(examManage.getExamCode());
            return new Result().ok("注销成功");
        }catch (Exception e){
            return new Result().serverError("注销失败");
        }
    }

    @PostMapping("/UpdateExamManage")
    public Result UpdateExamManage(@RequestBody ExamManage examManage){
        try {
            if(examManageService.UpdateExamManage(examManage)>0){
                return new Result().ok("修改成功");
            }
            else {
                return new Result().serverError("修改失败");
            }
        }catch (Exception e){
            return new Result().serverError("id冲突");
        }
    }
    @PostMapping("/createAndGenerate")
    public Result createAndGenerateExam(@Valid @RequestBody ExamCreateRequest request) {
        try {
            examManageService.createAndGenerateExam(request);
            return new Result().ok("试卷创建并组卷成功");
        } catch (Exception e) {
            return new Result().serverError("创建失败：" + e.getMessage());
        }
    }
}
