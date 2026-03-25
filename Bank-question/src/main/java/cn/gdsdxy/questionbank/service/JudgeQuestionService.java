package cn.gdsdxy.questionbank.service;

import cn.gdsdxy.questionbank.dto.manage.SearchPageQuestionDto;
import cn.gdsdxy.questionbank.dto.question.JudgeQuestionDto;
import cn.gdsdxy.questionbank.entity.questions.JudgeQuestion;
import cn.gdsdxy.questionbank.mapper.JudgeQuestionMapper;
import cn.gdsdxy.questionbank.repository.JudgeQuestionRepository;
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
public class JudgeQuestionService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    JudgeQuestionRepository judgeQuestionRepository;

    @Autowired
    JudgeQuestionMapper judgeQuestionMapper;

    public List<JudgeQuestionDto> getJudgeQuestionByPage(){
        List<JudgeQuestion> judgeQuestionList =judgeQuestionRepository.selectList(null);
        System.out.println(judgeQuestionList);
        List<JudgeQuestionDto> judgeQuestionDtoList = judgeQuestionList.stream()//把列表变成流
                .map(x->new JudgeQuestionDto(x.getQuestion(),x.getAnswer(),x.getScore()))
                .collect(Collectors.toList());//把流变成列表
        System.out.println(judgeQuestionDtoList);
        return judgeQuestionDtoList;
    }
    public List<JudgeQuestion> getAllJudgeQuestion(){
        QueryWrapper<JudgeQuestion> judgeQuestionQueryWrapper = new QueryWrapper<>();
        List<JudgeQuestion> judgeQuestionList =judgeQuestionRepository.selectList(judgeQuestionQueryWrapper);
        return judgeQuestionList;
    }

    public void addJudgeQuestion(JudgeQuestion judgeQuestion) {
        judgeQuestionRepository.insert(judgeQuestion);
    }

    public List<JudgeQuestion> findByIdAndType(Integer PaperId) {
        return judgeQuestionMapper.findByIdAndType(PaperId);
    }
    public List<JudgeQuestion> findByQuestion(SearchPageQuestionDto searchPageQuestionDto) {
        return judgeQuestionMapper.findByQuestion(searchPageQuestionDto);
    }
    public JudgeQuestion findOnlyQuestionId() {
        return judgeQuestionMapper.findOnlyQuestionId();
    }

    @Transactional(propagation = Propagation.REQUIRED)//事务回滚
    public void importQuestionsFromCSV(MultipartFile file) throws IOException {
        List<JudgeQuestion> questions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            // Assuming the first line is the header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) {
                    JudgeQuestion question = new JudgeQuestion();
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
    public void saveQuestions(List<JudgeQuestion> questions) {
        for (JudgeQuestion question : questions) {
            // 在这里可以添加一些逻辑，比如验证题目是否有效等

            // 调用持久化层将题目保存到数据库
            judgeQuestionRepository.insert(question);
        }
    }

}
