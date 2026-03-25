package cn.gdsdxy.questionbank.controller;

import cn.gdsdxy.questionbank.dto.base.Result;
import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.entity.Message;
import cn.gdsdxy.questionbank.entity.manage.ExamManage;
import cn.gdsdxy.questionbank.entity.manage.Replay;
import cn.gdsdxy.questionbank.service.MessageService;
import cn.gdsdxy.questionbank.service.ReplayService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "跟回复评论相关的控制器")
@RestController
@RequestMapping("/api")
public class ReplayController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ReplayService replayService;
    @ApiOperation("回复")
    @ApiImplicitParams(@ApiImplicitParam(name = "Replay",value = "回复对象",required = true) )
    @PostMapping("/insertReplay")
    public Result insertMessage(@RequestBody Replay replay) {
        try {
            replayService.addReplay(replay);
            return new Result().ok("插入成功", replay);
        } catch (Exception e) {
            return new Result().serverError("插入失败", e.getMessage());
        }
    }

    @PostMapping("/searchMessage")
    public List<Replay> searchReplay(@RequestBody Map<String, Integer> requestBody) {
        Integer messageId = requestBody.get("messageId");
        // 根据关键字搜索评论回应
        System.out.println(messageId);
        return replayService.searchReplay(messageId);
    }

}
