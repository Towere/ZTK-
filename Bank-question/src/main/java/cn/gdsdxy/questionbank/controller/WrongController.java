package cn.gdsdxy.questionbank.controller;

import cn.gdsdxy.questionbank.dto.base.Result;
import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.entity.questions.Wrong;
import cn.gdsdxy.questionbank.service.WrongService;
import com.github.pagehelper.PageInfo;
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
import java.util.Map;

@Api(tags = "跟错题相关的控制器")
@RestController
@RequestMapping("/api")
public class WrongController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    WrongService wrongService;
    @ApiOperation("题目")
    @ApiImplicitParams(@ApiImplicitParam(name = "Wrong",value = "错题对象",required = true) )
    @PostMapping("/insertWrong")
    public Result insertWrong(@RequestBody Wrong wrong) {
        try {
            wrongService.addWrong(wrong);
            return new Result().ok("添加成功", wrong);
        } catch (Exception e) {
            return new Result().serverError("添加失败", e.getMessage());
        }
    }

    @PostMapping("/selectWrong")
    public Result selectWrong(@RequestBody SearchPageDto searchPageDto){
        try {
            PageInfo<Wrong> wrongPageInfo = wrongService.selectWrong(searchPageDto);
            return new Result().ok("执行成功", wrongPageInfo);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }

    @PostMapping("/DeleteWrong")
    public Result DeleteWrong(@RequestBody Wrong wrong){
        try{
            wrongService.DeleteWrongId(wrong.getWrongId());
            return new Result().ok("删除成功");
        }catch (Exception e){
            return new Result().serverError("删除失败");
        }
    }

    @PostMapping("/importWrong")
    public ResponseEntity<String> importWrong(@RequestParam("file") MultipartFile file) {
        try {
            wrongService.importQuestionsFromCSV(file);
            return ResponseEntity.ok("导入成功！");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("导入失败！");
        }
    }
}
