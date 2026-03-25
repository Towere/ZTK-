package cn.gdsdxy.questionbank.controller;

import cn.gdsdxy.questionbank.dto.base.Result;
import cn.gdsdxy.questionbank.entity.manage.Knowledge;
import cn.gdsdxy.questionbank.repository.KnowledgeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "知识点相关接口")
@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeController {

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @ApiOperation("获取所有知识点列表")
    @GetMapping("/list") // 与前端调用的接口路径一致
    public Result getKnowledgeList() {
        try {
            List<Knowledge> knowledgeList = knowledgeRepository.selectList(null);
            return new Result().ok("获取成功", knowledgeList);
        } catch (Exception e) {
            return new Result().serverError("获取知识点失败", e.getMessage());
        }
    }
}