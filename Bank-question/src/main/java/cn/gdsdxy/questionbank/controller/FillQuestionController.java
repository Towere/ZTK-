package cn.gdsdxy.questionbank.controller;

import cn.gdsdxy.questionbank.dto.base.Result;
import cn.gdsdxy.questionbank.dto.manage.ExamDto;
import cn.gdsdxy.questionbank.dto.question.FillQuestionDto;
import cn.gdsdxy.questionbank.entity.manage.ExamManage;
import cn.gdsdxy.questionbank.entity.questions.FillQuestion;
import cn.gdsdxy.questionbank.entity.questions.JudgeQuestion;
import cn.gdsdxy.questionbank.service.ExamManageService;
import cn.gdsdxy.questionbank.service.FillQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(tags = "填空题的控制器")
@RestController
@RequestMapping("/api")
public class FillQuestionController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    FillQuestionService fillQuestionService;
    @ApiOperation("问题")
    @ApiImplicitParams(@ApiImplicitParam(name = "FillQuestion",value = "填空题对象",required = true) )
    @PostMapping("/FillQuestion")
    public Result getFillByPage(){
        try {
            List<FillQuestionDto> fillQuestionList = fillQuestionService.getFillQuestionByPage();
            return new Result().ok("执行成功", fillQuestionList);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }

    @PostMapping("/selectFillQuestion")
    public Result getAllFillQuestion(){
        try {
            List<FillQuestion> fillQuestionList = fillQuestionService.getAllFillQuestion();
            return new Result().ok("执行成功", fillQuestionList);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }

    @PostMapping("/insertFillQuestion")
    public Result insertFillQuestion(@RequestBody FillQuestion fillQuestion) {
        try {
            fillQuestionService.addFillQuestion(fillQuestion);
            return new Result().ok("插入成功", fillQuestion);
        } catch (Exception e) {
            return new Result().serverError("插入失败", e.getMessage());
        }
    }

    @PostMapping("/importFillQuestion")
    public ResponseEntity<String> importFillQuestions(@RequestParam("file") MultipartFile file) {
        try {
            fillQuestionService.importQuestionsFromCSV(file);
            return ResponseEntity.ok("导入成功！");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("导入失败！");
        }
    }
    @GetMapping("/fillQuestionId")
    public Result findOnlyQuestion() {
        try {
            FillQuestion fillQuestionList = fillQuestionService.findOnlyQuestionId();
            return new Result().ok("执行成功", fillQuestionList);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }
}
