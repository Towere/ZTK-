package cn.gdsdxy.questionbank.controller;

import cn.gdsdxy.questionbank.dto.MessageDto;
import cn.gdsdxy.questionbank.dto.base.Result;
import cn.gdsdxy.questionbank.dto.manage.ExamDto;
import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.entity.Message;
import cn.gdsdxy.questionbank.entity.manage.ExamManage;
import cn.gdsdxy.questionbank.service.ExamManageService;
import cn.gdsdxy.questionbank.service.MessageService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "跟评论相关的控制器")
@RestController
@RequestMapping("/api")
public class MessageController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    MessageService messageService;
    @ApiOperation("评论")
    @ApiImplicitParams(@ApiImplicitParam(name = "Message",value = "评论对象",required = true) )
    @PostMapping("/selectMessage")
    public Result getAllMessage(@RequestBody SearchPageDto searchPageDto){
        try {
            PageInfo<Message> messagePageInfo = messageService.getAllMessage(searchPageDto);
            return new Result().ok("查询成功", messagePageInfo);
        } catch (Exception e) {
            return new Result().serverError("查询失败", e.getMessage());
        }
    }

    @PostMapping("/insertMessage")
    public Result insertMessage(@RequestBody Message message) {
        try {
            messageService.addMessage(message);
            return new Result().ok("插入成功", message);
        } catch (Exception e) {
            return new Result().serverError("插入失败", e.getMessage());
        }
    }

    @PostMapping("/selectAllMessage")
    public Result getAllMessageDto(@RequestBody SearchPageDto searchPageDto){
        try {
            PageInfo<MessageDto> messageDtoPageInfo = messageService.getAllMessageDto(searchPageDto);
            return new Result().ok("查询成功", messageDtoPageInfo);
        } catch (Exception e) {
            return new Result().serverError("查询失败", e.getMessage());
        }
    }

}
