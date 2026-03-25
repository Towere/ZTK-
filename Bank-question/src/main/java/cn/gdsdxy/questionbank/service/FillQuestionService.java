package cn.gdsdxy.questionbank.service;

import cn.gdsdxy.questionbank.dto.manage.ExamDto;
import cn.gdsdxy.questionbank.dto.manage.SearchPageQuestionDto;
import cn.gdsdxy.questionbank.dto.question.FillQuestionDto;
import cn.gdsdxy.questionbank.entity.manage.ExamManage;
import cn.gdsdxy.questionbank.entity.questions.FillQuestion;
import cn.gdsdxy.questionbank.entity.questions.JudgeQuestion;
import cn.gdsdxy.questionbank.mapper.FillQuestionMapper;
import cn.gdsdxy.questionbank.repository.ExamManageRepository;
import cn.gdsdxy.questionbank.repository.FillQuestionRepository;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FillQuestionService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    FillQuestionRepository fillQuestionRepository;
    @Autowired
    FillQuestionMapper fillQuestionMapper;

    public List<FillQuestionDto> getFillQuestionByPage(){
        List<FillQuestion> fillQuestionList =fillQuestionRepository.selectList(null);
        System.out.println(fillQuestionList);
        List<FillQuestionDto> fillQuestionDtoList = fillQuestionList.stream()//把列表变成流
                .map(x->new FillQuestionDto(x.getQuestion(),x.getAnswer(),x.getScore()))
                .collect(Collectors.toList());//把流变成列表
        System.out.println(fillQuestionDtoList);
        return fillQuestionDtoList;
    }
    public List<FillQuestion> getAllFillQuestion(){
        QueryWrapper<FillQuestion> fillQuestionQueryWrapper = new QueryWrapper<>();
        List<FillQuestion> fillQuestionList =fillQuestionRepository.selectList(fillQuestionQueryWrapper);
        return fillQuestionList;
    }

    public void addFillQuestion(FillQuestion fillQuestion) {
        fillQuestionRepository.insert(fillQuestion);
    }

    public List<FillQuestion> findByIdAndType(Integer PaperId) {
        return fillQuestionMapper.findByIdAndType(PaperId);
    }

    public List<FillQuestion> findByQuestion(SearchPageQuestionDto searchPageQuestionDto) {
        return fillQuestionMapper.findByQuestion(searchPageQuestionDto);
    }
    public FillQuestion findOnlyQuestionId() {
        return fillQuestionMapper.findOnlyQuestionId();
    }
    @Transactional(propagation = Propagation.REQUIRED)//事务回滚
    public void importQuestionsFromCSV(MultipartFile file) throws IOException {
        List<FillQuestion> questions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            // Assuming the first line is the header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) {
                    FillQuestion question = new FillQuestion();
                    question.setSubject(data[0]);
                    question.setQuestion(data[1]);
                    question.setAnswer(data[2]);
                    question.setDifficulty(data[3]);
                    question.setKnowledgeId(data[4]);
                    try {
                        int score = Integer.parseInt(data[5]);
                        question.setScore(score);
                    } catch (NumberFormatException e) {
                        // 处理无效的分数，可以跳过该行或进行其他操作
                        // 例如，记录错误日志
                    }
                    question.setAnalysis(data[6]);
                    questions.add(question);
                }
            }
        }
        saveQuestions(questions);
    }
    @Transactional
    public void saveQuestions(List<FillQuestion> questions) {
        for (FillQuestion question : questions) {
            // 在这里可以添加一些逻辑，比如验证题目是否有效等

            // 调用持久化层将题目保存到数据库
            fillQuestionRepository.insert(question);
        }
    }

}
