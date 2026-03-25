package cn.gdsdxy.questionbank.controller;

import cn.gdsdxy.questionbank.dto.base.Result;
import cn.gdsdxy.questionbank.dto.question.FillQuestionDto;
import cn.gdsdxy.questionbank.dto.question.MultiQuestionDto;
import cn.gdsdxy.questionbank.entity.questions.FillQuestion;
import cn.gdsdxy.questionbank.entity.questions.JudgeQuestion;
import cn.gdsdxy.questionbank.entity.questions.MultiQuestion;
import cn.gdsdxy.questionbank.service.FillQuestionService;
import cn.gdsdxy.questionbank.service.MultiQuestionService;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Api(tags = "跟选择题相关的控制器")
@RestController
@RequestMapping("/api")
public class MultiQuestionController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    MultiQuestionService multiQuestionService;
    @ApiOperation("问题")
    @ApiImplicitParams(@ApiImplicitParam(name = "MultiQuestion",value = "选择题对象",required = true) )
    @PostMapping("/MultiQuestion")
    public Result getMultiByPage(){
        try {
            List<MultiQuestionDto> multiQuestionList = multiQuestionService.getMultiQuestionByPage();
            return new Result().ok("执行成功", multiQuestionList);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }
    @GetMapping("/multiQuestionId")
    public Result findOnlyQuestion() {
        try {
            MultiQuestion multiQuestionList = multiQuestionService.findOnlyQuestionId();
            return new Result().ok("执行成功", multiQuestionList);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }

    @PostMapping("/selectMultiQuestion")
    public Result getAllMultiQuestion(){
        try {
            List<MultiQuestion> multiQuestionList = multiQuestionService.getAllMultiQuestion();
            return new Result().ok("执行成功", multiQuestionList);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }

    @GetMapping("/generate")
    public ResponseEntity<List<MultiQuestion>> generateRandomExam(@RequestParam int numberOfQuestions) {
        if (numberOfQuestions <= 0) {
            return ResponseEntity.badRequest().body(Collections.emptyList()); // 返回空题目列表和HTTP 400错误，因为请求的题目数量无效
        }

        List<MultiQuestion> randomQuestions = multiQuestionService.generateRandomExam(numberOfQuestions);
        if (randomQuestions.isEmpty()) {
            return ResponseEntity.notFound().build(); // 返回HTTP 404错误，因为没有可用的题目
        }

        return ResponseEntity.ok(randomQuestions);
    }

    @PostMapping("/insertMultiQuestion")
    public Result insertMultiQuestion(@RequestBody MultiQuestion multiQuestion) {
        try {
            multiQuestionService.addMultiQuestion(multiQuestion);
            return new Result().ok("插入成功", multiQuestion);
        } catch (Exception e) {
            return new Result().serverError("插入失败", e.getMessage());
        }
    }

    @PostMapping("/importMultiQuestion")
    public ResponseEntity<String> importQuestions(@RequestParam("file") MultipartFile file) {
        try {
            multiQuestionService.importQuestionsFromCSV(file);
            return ResponseEntity.ok("导入成功！");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("导入失败！");
        }
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please select a file to upload.";
        }
        try {
            // 保存文件到临时目录（或其他目录）
            byte[] bytes = file.getBytes();
            Path path = Paths.get("E:\\Design\\Bank-question\\src\\main\\resources\\static\\wrongPhoto/" + file.getOriginalFilename());
            Files.write(path, bytes);

            // 执行OCR扫描
            String ocrResult = multiQuestionService.performOCR(path.toString());

            // 返回OCR结果
            return ocrResult;
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file.";
        }
    }
//    @PostMapping("/processOCRResult")
//    public String processOCRResult(@RequestBody String ocrResult) {
//            MultiQuestion quiz = multiQuestionService.parseOCRResult(ocrResult);
//
//            // 假设 multiQuestionService 是你的服务类对象，并且它有一个 addMultiQuestion 方法用于将问题添加到数据库
//            multiQuestionService.addMultiQuestion(quiz);
//            return "OCR结果已处理并存入数据库";
//
//    }
}
