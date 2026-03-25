package cn.gdsdxy.questionbank.controller;

import cn.gdsdxy.questionbank.constant.JwtConstant;
import cn.gdsdxy.questionbank.dto.AllQuestionDto;
import cn.gdsdxy.questionbank.dto.base.Result;
import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.dto.manage.SearchPageQuestionDto;
import cn.gdsdxy.questionbank.entity.admin.Admin;
import cn.gdsdxy.questionbank.entity.admin.Student;
import cn.gdsdxy.questionbank.entity.manage.ExamManage;
import cn.gdsdxy.questionbank.entity.manage.PaperManage;
import cn.gdsdxy.questionbank.entity.questions.FillQuestion;
import cn.gdsdxy.questionbank.entity.questions.JudgeQuestion;
import cn.gdsdxy.questionbank.entity.questions.MultiQuestion;
import cn.gdsdxy.questionbank.service.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "跟题目相关的控制器")
@RestController
@RequestMapping("/api")
public class PaperManageController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private JudgeQuestionService judgeQuestionService;

    @Autowired
    private MultiQuestionService multiQuestionService;

    @Autowired
    private FillQuestionService fillQuestionService;

    @Autowired
    PaperManageService paperManageService;

    @ApiOperation("选题")
    @ApiImplicitParams(@ApiImplicitParam(name = "PaperManage", value = "题目对象", required = true))
    @GetMapping("/paper/{paperId}")
    public Map<Integer, List<?>> findById(@PathVariable("paperId") Integer paperId) {
        List<MultiQuestion> multiQuestionRes = multiQuestionService.findByIdAndType(paperId);   //选择题题库 1
        List<FillQuestion> fillQuestionsRes = fillQuestionService.findByIdAndType(paperId);     //填空题题库 2
        List<JudgeQuestion> judgeQuestionRes = judgeQuestionService.findByIdAndType(paperId);   //判断题题库 3
        Map<Integer, List<?>> map = new HashMap<>();
        map.put(1, multiQuestionRes);
        map.put(2, fillQuestionsRes);
        map.put(3, judgeQuestionRes);
        return map;
    }

    @PostMapping("/insertPaperManage")
    public Result insertPaperManage(@RequestBody PaperManage paperManage) {
        try {
            paperManageService.addPaperManage(paperManage);
            return new Result().ok("插入成功", paperManage);
        } catch (Exception e) {
            return new Result().serverError("插入失败", e.getMessage());
        }
    }

    @PostMapping("/selectAllQuestion")
    public Result getAllQuestion(@RequestBody SearchPageDto searchPageDto) {
        try {
            List<AllQuestionDto> allQuestionDtoList = paperManageService.getAllQuestion(searchPageDto);
            return new Result().ok("执行成功", allQuestionDtoList);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }

    @PostMapping("/GetAllQuestionByPage")
    public Result GetAllQuestionByPage(@RequestBody SearchPageQuestionDto searchPageQuestionDto) {
        PageInfo<AllQuestionDto> PageInfo = paperManageService.GetAllQuestionByPage(searchPageQuestionDto);
        return new Result().ok(PageInfo);
    }

    @PostMapping("/DeleteByQuestion")
    public Result DeleteByQuestion(@RequestBody PaperManage paperManage) {

        try {
            int i = paperManageService.DeleteByPage(paperManage);
            if (i > 0) {
                return new Result().ok("执行成功");
            } else {
                return new Result().ok("修改失败");
            }

        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }



}
