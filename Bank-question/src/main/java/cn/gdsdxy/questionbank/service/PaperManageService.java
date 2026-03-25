package cn.gdsdxy.questionbank.service;

import cn.gdsdxy.questionbank.dto.AllQuestionDto;
import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.dto.manage.SearchPageQuestionDto;
import cn.gdsdxy.questionbank.entity.admin.Student;
import cn.gdsdxy.questionbank.entity.manage.PaperManage;
import cn.gdsdxy.questionbank.entity.questions.FillQuestion;
import cn.gdsdxy.questionbank.entity.questions.JudgeQuestion;
import cn.gdsdxy.questionbank.entity.questions.MultiQuestion;
import cn.gdsdxy.questionbank.mapper.PaperManageMapper;
import cn.gdsdxy.questionbank.repository.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaperManageService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    PaperManageRepository paperManageRepository;

    @Autowired
    PaperManageMapper paperManageMapper;

    @Autowired
    FillQuestionRepository fillQuestionRepository;

    @Autowired
    MultiQuestionRepository multiQuestionRepository;

    @Autowired
    JudgeQuestionRepository judgeQuestionRepository;

    public void addPaperManage(PaperManage paperManage) {
        paperManageRepository.insert(paperManage);
    }

    public List<AllQuestionDto> getAllQuestion(SearchPageDto searchPageDto) {
        return paperManageMapper.findAll(searchPageDto);
    }
    public PageInfo<AllQuestionDto> GetAllQuestionByPage(SearchPageQuestionDto searchPageQuestionDto){
        PageHelper.startPage(searchPageQuestionDto.getPage(),searchPageQuestionDto.getSize());
        QueryWrapper<PaperManage> paperManageQueryWrapper = new QueryWrapper<>();
        paperManageQueryWrapper.eq("paperId",searchPageQuestionDto.getPaperId());
        List<PaperManage> paperManageList = paperManageRepository.selectList(paperManageQueryWrapper);
        List<AllQuestionDto> allQuestionDtoList = new ArrayList<>();

        for(int i=0;i<paperManageList.size();i++){
            AllQuestionDto allQuestionDto = new AllQuestionDto();
            PaperManage paperManage = paperManageList.get(i);
            if(paperManage.getQuestionType()==1){
                QueryWrapper<MultiQuestion> multiQuestionQueryWrapper = new QueryWrapper<>();
                multiQuestionQueryWrapper.eq("questionId",paperManage.getQuestionId());
                MultiQuestion multiQuestion = multiQuestionRepository.selectList(multiQuestionQueryWrapper).get(0);
                allQuestionDto.setQuestion(multiQuestion.getQuestion());
                allQuestionDto.setLevel(multiQuestion.getDifficulty());
                allQuestionDto.setSubject(multiQuestion.getSubject());
                allQuestionDto.setScore(multiQuestion.getScore());
                allQuestionDto.setQuestionType(paperManage.getQuestionType());
                allQuestionDto.setQuestionId(multiQuestion.getQuestionId());


                allQuestionDtoList.add(allQuestionDto);

            }else if (paperManage.getQuestionType()==2){
                QueryWrapper<FillQuestion> fillQuestionQueryWrapper = new QueryWrapper<>();
                fillQuestionQueryWrapper.eq("questionId",paperManage.getQuestionId());
                FillQuestion fillQuestion = fillQuestionRepository.selectList(fillQuestionQueryWrapper).get(0);
                allQuestionDto.setQuestion(fillQuestion.getQuestion());
                allQuestionDto.setLevel(fillQuestion.getDifficulty());
                allQuestionDto.setSubject(fillQuestion.getSubject());
                allQuestionDto.setScore(fillQuestion.getScore());
                allQuestionDto.setQuestionType(paperManage.getQuestionType());
                allQuestionDto.setQuestionId(fillQuestion.getQuestionId());

                allQuestionDtoList.add(allQuestionDto);

            }else if (paperManage.getQuestionType()==3){
                QueryWrapper<JudgeQuestion> judgeQuestionQueryWrapper = new QueryWrapper<>();
                judgeQuestionQueryWrapper.eq("questionId",paperManage.getQuestionId());
                JudgeQuestion judgeQuestion = judgeQuestionRepository.selectList(judgeQuestionQueryWrapper).get(0);
                allQuestionDto.setQuestion(judgeQuestion.getQuestion());
                allQuestionDto.setLevel(judgeQuestion.getDifficulty());
                allQuestionDto.setSubject(judgeQuestion.getSubject());
                allQuestionDto.setScore(judgeQuestion.getScore());
                allQuestionDto.setQuestionType(paperManage.getQuestionType());
                allQuestionDto.setQuestionId(judgeQuestion.getQuestionId());

                allQuestionDtoList.add(allQuestionDto);
            }
        }
        PageInfo<AllQuestionDto> PageInfo = new PageInfo(allQuestionDtoList);
        PageInfo.setList(allQuestionDtoList);
        return PageInfo;

    }
    public int DeleteByPage(PaperManage paperManage){

        QueryWrapper<PaperManage> paperManageQueryWrapper = new QueryWrapper<>();
        paperManageQueryWrapper.eq("QuestionType",paperManage.getQuestionType())
                .and(paperManageQueryWrapper1 -> paperManageQueryWrapper1
                        .eq("questionId",paperManage.getQuestionId()));

        if(paperManage.getQuestionType()==1){
          paperManageRepository.delete(paperManageQueryWrapper);
          return  multiQuestionRepository.deleteById(paperManage.getQuestionId());

        }else if(paperManage.getQuestionType()==2){
            paperManageRepository.delete(paperManageQueryWrapper);
           return fillQuestionRepository.deleteById(paperManage.getQuestionId());
        }else if (paperManage.getQuestionType()==3){
            paperManageRepository.delete(paperManageQueryWrapper);
          return  judgeQuestionRepository.deleteById(paperManage.getQuestionId());
        }
        return 0;

    }
}