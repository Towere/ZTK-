package cn.gdsdxy.questionbank.service;

import cn.gdsdxy.questionbank.dto.question.AnswerSubmitDto;
import cn.gdsdxy.questionbank.entity.AudioFiles;
import cn.gdsdxy.questionbank.entity.manage.Score;
import cn.gdsdxy.questionbank.entity.questions.ListeningQuestion;
import cn.gdsdxy.questionbank.repository.ListenQuestionRepository;
import cn.gdsdxy.questionbank.repository.ScoreRepository;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ListenQuestionService {
    @Autowired
    RedisTemplate redisTemplate;
    // 注入题目相关的Repository
    @Autowired
    private ListenQuestionRepository listenQuestionRepository;

    @Autowired
    private ScoreRepository scoreRepository; // 用于保存得分
    // 根据ID查询单个听力数据
    public List<ListeningQuestion> getListenId(Integer audioId) {
        List<ListeningQuestion> listeningQuestion = listenQuestionRepository.selectList(new QueryWrapper<ListeningQuestion>().eq("audioId",audioId));
        System.out.println(listeningQuestion);
        return listeningQuestion;
    }


}
