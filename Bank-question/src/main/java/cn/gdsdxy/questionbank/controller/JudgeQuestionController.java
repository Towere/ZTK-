package cn.gdsdxy.questionbank.controller;

import cn.gdsdxy.questionbank.dto.base.Result;
import cn.gdsdxy.questionbank.dto.question.JudgeQuestionDto;
import cn.gdsdxy.questionbank.entity.questions.JudgeQuestion;
import cn.gdsdxy.questionbank.entity.questions.MultiQuestion;
import cn.gdsdxy.questionbank.service.JudgeQuestionService;
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

@Api(tags = "判断题的控制器")
@RestController
@RequestMapping("/api")
public class JudgeQuestionController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    JudgeQuestionService judgeQuestionService;

    @ApiOperation("问题")
    @ApiImplicitParams(@ApiImplicitParam(name = "JudgeQuestion",value = "判断题对象",required = true) )
    @PostMapping("/JudgeQuestion")
    public Result getJudgeByPage(){
        try {
            List<JudgeQuestionDto> fillQuestionList = judgeQuestionService.getJudgeQuestionByPage();
            return new Result().ok("执行成功", fillQuestionList);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }

    @PostMapping("/selectJudgeQuestion")
    public Result getAllJudgeQuestion(){
        try {
            List<JudgeQuestion> judgeQuestionList = judgeQuestionService.getAllJudgeQuestion();
            return new Result().ok("执行成功", judgeQuestionList);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }

    @PostMapping("/insertJudgeQuestion")
    public Result insertJudgeQuestion(@RequestBody JudgeQuestion judgeQuestion) {
        try {
            judgeQuestionService.addJudgeQuestion(judgeQuestion);
            return new Result().ok("插入成功", judgeQuestion);
        } catch (Exception e) {
            return new Result().serverError("插入失败", e.getMessage());
        }
    }

    @PostMapping("/importJudgeQuestion")
    public ResponseEntity<String> importJudgeQuestions(@RequestParam("file") MultipartFile file) {
        try {
            judgeQuestionService.importQuestionsFromCSV(file);
            return ResponseEntity.ok("导入成功！");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("导入失败！");
        }
    }
    @GetMapping("/judgeQuestionId")
    public Result findOnlyQuestion() {
        try {
            JudgeQuestion judgeQuestionList = judgeQuestionService.findOnlyQuestionId();
            return new Result().ok("执行成功", judgeQuestionList);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }
}
