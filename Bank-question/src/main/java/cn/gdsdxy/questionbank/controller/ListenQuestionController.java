package cn.gdsdxy.questionbank.controller;

import cn.gdsdxy.questionbank.dto.base.Result;
import cn.gdsdxy.questionbank.dto.question.AnswerSubmitDto;
import cn.gdsdxy.questionbank.entity.questions.ListeningQuestion;
import cn.gdsdxy.questionbank.repository.ListenQuestionRepository;

import cn.gdsdxy.questionbank.service.ListenQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "听力题的控制器")
@RestController
@RequestMapping("/api")
public class ListenQuestionController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ListenQuestionRepository listenQuestionRepository;
    @Autowired
    ListenQuestionService listenQuestionService;

    @ApiOperation("根据ID获取单个听力数据")
    @ApiImplicitParam(name = "id", value = "听力ID", required = true, dataType = "Integer")
    @GetMapping("/listenQuestion/{audioId}")
    public Result getListenId(@PathVariable Integer audioId) {
        try {
            List<ListeningQuestion> listeningQuestion = listenQuestionService.getListenId(audioId);
            if (listeningQuestion.isEmpty()) {
                return new Result().serverError("听力数据不存在");
            }
            return new Result().ok("查询成功", listeningQuestion);
        } catch (Exception e) {
            return new Result().serverError("查询失败", e.getMessage());
        }
    }

}
