package cn.gdsdxy.questionbank.controller;

import cn.gdsdxy.questionbank.dto.base.Result;
import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.dto.manage.SearchPageScoreDto;
import cn.gdsdxy.questionbank.entity.Message;
import cn.gdsdxy.questionbank.entity.manage.ExamManage;
import cn.gdsdxy.questionbank.entity.manage.Score;
import cn.gdsdxy.questionbank.service.ScoreService;
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

import java.util.List;
import java.util.Map;

@Api(tags = "跟成绩相关的控制器")
@RestController
@RequestMapping("/api")
public class ScoreController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ScoreService scoreService;
    @ApiOperation("成绩")
    @ApiImplicitParams(@ApiImplicitParam(name = "Score",value = "成绩对象",required = true) )
    @PostMapping("/searchScore")
    public List<Score> searchScore(@RequestBody Map<String, Integer> requestBody) {
        Integer studentId = requestBody.get("studentId");
        // 根据关键字搜索成绩
        System.out.println(studentId);
        return scoreService.searchScore(studentId);
    }

    @ApiOperation(value = "分页查询成绩（带筛选条件）", notes = "支持按学生学号、课程名称、是否及格筛选，返回分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchPageScoreDto", value = "分页查询参数（包含页码、每页条数、studentId、subject、minScore、maxScore）",
                    required = true, dataType = "SearchPageScoreDto")
    })
    @PostMapping("/searchPageScore")
    public Result searchPageScore(@RequestBody SearchPageScoreDto searchPageScoreDto) {
        try {
            PageInfo<Score> scorePageInfo = scoreService.searchPageScore(searchPageScoreDto);
            return new Result().ok("执行成功", scorePageInfo);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }


    @PostMapping("/insertScore")
    public Result insertScore(@RequestBody Score score) {
        try {
            scoreService.addScore(score);
            return new Result().ok("插入成功", score);
        } catch (Exception e) {
            return new Result().serverError("插入失败", e.getMessage());
        }
    }
}
