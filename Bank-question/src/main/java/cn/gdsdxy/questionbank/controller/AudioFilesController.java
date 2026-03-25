package cn.gdsdxy.questionbank.controller;

import cn.gdsdxy.questionbank.dto.base.Result;
import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.dto.question.AnswerSubmitDto;
import cn.gdsdxy.questionbank.entity.AudioFiles;
import cn.gdsdxy.questionbank.entity.manage.Score;
import cn.gdsdxy.questionbank.entity.questions.Wrong;
import cn.gdsdxy.questionbank.service.AudioFilesService;
import cn.gdsdxy.questionbank.service.ScoreService;
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

@Api(tags = "跟音频相关的控制器")
@RestController
@RequestMapping("/api")
public class AudioFilesController {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    AudioFilesService audioFilesService;

    @ApiOperation("音频")
    @ApiImplicitParams(@ApiImplicitParam(name = "AudioFiles",value = "音频对象",required = true) )
    @PostMapping("/searchAudioFiles")
    public List<AudioFiles> searchAudioFiles(@RequestBody Map<String, Integer> requestBody) {
        Integer paperId = requestBody.get("paperId");
        System.out.println(paperId);
        return audioFilesService.searchAudioFiles(paperId);
    }

    @PostMapping("/searchAllAudio")
    public Result searchAllAudio(@RequestBody SearchPageDto searchPageDto){
        try {
            PageInfo<AudioFiles> audioFilesPageInfo = audioFilesService.searchAllAudio(searchPageDto);
            return new Result().ok("执行成功", audioFilesPageInfo);
        } catch (Exception e) {
            return new Result().serverError("执行失败", e.getMessage());
        }
    }


}
