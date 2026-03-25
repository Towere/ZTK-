package cn.gdsdxy.questionbank.service;

import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.entity.manage.ExamManage;
import cn.gdsdxy.questionbank.entity.questions.JudgeQuestion;
import cn.gdsdxy.questionbank.entity.questions.Wrong;
import cn.gdsdxy.questionbank.repository.StudentRepository;
import cn.gdsdxy.questionbank.repository.WrongRepository;
import cn.gdsdxy.questionbank.util.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import java.util.concurrent.TimeUnit;

@Service
public class WrongService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    WrongRepository wrongRepository;

    public void addWrong(Wrong wrong) {
        wrongRepository.insert(wrong);
    }

    public PageInfo<Wrong> selectWrong(SearchPageDto searchPageDto) throws Exception{
        PageHelper.startPage(searchPageDto.getPage(), searchPageDto.getSize());
        QueryWrapper<Wrong> wrongQueryWrapper = new QueryWrapper<>();
        List<Wrong> wrongList = wrongRepository.selectList(wrongQueryWrapper);
        PageInfo<Wrong> PageInfo = new PageInfo(wrongList);
        PageInfo.setList(wrongList);
        return PageInfo;
    }

    public int DeleteWrongId(Integer id){
        return wrongRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)//事务回滚
    public void importQuestionsFromCSV(MultipartFile file) throws IOException {
        List<Wrong> questions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            // Assuming the first line is the header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 1) {
                    Wrong question = new Wrong();
                    question.setTopic(data[0]);
                    questions.add(question);
                }
            }
        }
        saveQuestions(questions);
    }
    @Transactional
    public void saveQuestions(List<Wrong> questions) {
        for (Wrong question : questions) {
            wrongRepository.insert(question);
        }
    }
}
