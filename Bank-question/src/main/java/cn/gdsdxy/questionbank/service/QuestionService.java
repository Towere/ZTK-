package cn.gdsdxy.questionbank.service;

import cn.gdsdxy.questionbank.entity.questions.Question;
import cn.gdsdxy.questionbank.mapper.QuestionMapper;
import cn.gdsdxy.questionbank.repository.QuestionRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public List<Question> selectByKnowledgeAndDifficulty(List<Integer> knowledgeIds, Integer difficulty) {
        LambdaQueryWrapper<Question> wrapper = Wrappers.lambdaQuery();
        wrapper.in(Question::getKnowledgeId, knowledgeIds)
                .eq(Question::getDifficulty, difficulty)
                .eq(Question::getIsValid, 1);
        return questionRepository.selectList(wrapper);
    }
}