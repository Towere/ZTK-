package cn.gdsdxy.questionbank.service;

import cn.gdsdxy.questionbank.dto.manage.ExamCreateRequest;
import cn.gdsdxy.questionbank.dto.manage.ExamDto;
import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;

import cn.gdsdxy.questionbank.entity.manage.ExamManage;
import cn.gdsdxy.questionbank.entity.manage.PaperManage;
import cn.gdsdxy.questionbank.entity.questions.MultiQuestion;
import cn.gdsdxy.questionbank.entity.questions.Question;
import cn.gdsdxy.questionbank.repository.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ExamManageService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ExamManageRepository examManageRepository;

    @Autowired
    MultiQuestionRepository multiQuestionRepository;
    @Autowired
    KnowledgeRepository knowledgeRepository;
    @Autowired
    PaperManageRepository paperManageRepository;

    @Autowired
    private MultiQuestionService multiQuestionService;


    public PageInfo<ExamDto> getExamDto(SearchPageDto searchPageDto) throws Exception {
        PageHelper.startPage(searchPageDto.getPage(), searchPageDto.getSize());
        List<ExamManage> examManageList = examManageRepository.selectList(null);
        List<ExamDto> examDtoList = examManageList.stream()//把列表变成流
                .map(x -> new ExamDto(x.getDescription(), x.getSource(), x.getExamDate(), x.getTotalTime(), x.getTotalScore()))
                .collect(Collectors.toList());//把流变成列表
        System.out.println(examDtoList);
        PageInfo<ExamDto> dtoPageInfo = new PageInfo(examManageList);
        dtoPageInfo.setList(examDtoList);
        return dtoPageInfo;
    }

    public PageInfo<ExamManage> getAllExam(SearchPageDto searchPageDto) throws Exception{
        PageHelper.startPage(searchPageDto.getPage(), searchPageDto.getSize());
        QueryWrapper<ExamManage> examManageQueryWrapper = new QueryWrapper<>();
        List<ExamManage> examManageList = examManageRepository.selectList(examManageQueryWrapper);
        PageInfo<ExamManage> PageInfo = new PageInfo(examManageList);
        PageInfo.setList(examManageList);
        return PageInfo;
    }

    public List<ExamDto> getExam() {
        List<ExamManage> examManageList = examManageRepository.selectList(null);
        List<ExamDto> examDtoList = examManageList.stream()//把列表变成流
                .map(x -> new ExamDto(x.getDescription(), x.getSource(), x.getExamDate(), x.getTotalTime(), x.getTotalScore()))
                .collect(Collectors.toList());//把流变成列表
        return examDtoList;
    }


    public void addExamManage(ExamManage examManage) {
        QueryWrapper<ExamManage> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().orderBy(true,false,ExamManage::getPaperId).last("limit 1");
        ExamManage examManage1=examManageRepository.selectOne(queryWrapper);
        examManage.setPaperId(examManage1.getPaperId()+1);
        examManageRepository.insert(examManage);
    }

    public List<ExamManage> searchExams(String key) {
        System.out.println();
        // 使用ExamManageRepository中的方法来根据关键字搜索试卷
      List<ExamManage> examManageList = examManageRepository.selectList(new QueryWrapper<ExamManage>().like("source", key));
      System.out.println(examManageList);
        return  examManageList;
    }

    public int DeleteExamManageId(Integer id){
        return examManageRepository.deleteById(id);
    }

    public int UpdateExamManage(ExamManage examManage){
        return examManageRepository.updateById(examManage);
    }

    /**
     * 自动组卷并创建试卷
     */
    @Transactional(rollbackFor = Exception.class)
    public void createAndGenerateExam(ExamCreateRequest request) {
        try {
            // 1. 校验组卷规则
            validateRule(request);

            // 2. 计算各难度题目数量（优化计算方式）
            int totalCount = request.getQuestionCount();
            int easyRatio = request.getDifficulty().getEasy();
            int mediumRatio = request.getDifficulty().getMedium();

            // 确保总和为totalCount，避免计算误差
            int easyCount = Math.round(totalCount * easyRatio / 100.0f);
            int mediumCount = Math.round(totalCount * mediumRatio / 100.0f);
            int hardCount = totalCount - easyCount - mediumCount;

            // 3. 转换知识点ID类型（关键修正：Long转Integer）
            List<Integer> knowledgeIntIds = request.getKnowledgePoints().stream()
                    .map(Long::intValue)
                    .collect(Collectors.toList());

            // 4. 筛选并抽取题目
            List<MultiQuestion> allQuestions = new ArrayList<>();
            allQuestions.addAll(selectAndRandomizeQuestions(knowledgeIntIds, 1, easyCount));
            allQuestions.addAll(selectAndRandomizeQuestions(knowledgeIntIds, 2, mediumCount));
            allQuestions.addAll(selectAndRandomizeQuestions(knowledgeIntIds, 3, hardCount));

            // 5. 创建试卷基本信息
            ExamManage examManage = new ExamManage();
            BeanUtils.copyProperties(request, examManage);
            examManage.setPaperId(null); // 让数据库自动生成

            // 确保examCode不为空
            if (request.getExamCode() == null) {
                throw new IllegalArgumentException("考试编号不能为空");
            }

            // 关键新增：根据examDate计算学期
            if (request.getExamDate() != null) {
                try {
                    // 将examDate转换为LocalDate
                    LocalDate examDate = LocalDate.parse(request.getExamDate());

                    // 根据月份计算学期
                    int month = examDate.getMonthValue();
                    if (month >= 9 || month <= 2) {
                        examManage.setTerm("1"); // 9月至次年2月为第1学期
                    } else {
                        examManage.setTerm("2"); // 3月至8月为第2学期
                    }
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("考试日期格式不正确，应为yyyy-MM-dd", e);
                }
            } else {
                throw new IllegalArgumentException("考试日期不能为空");
            }

            // 计算总分
            int totalScore = allQuestions.stream().mapToInt(MultiQuestion::getScore).sum();
            examManage.setTotalScore(totalScore);
            examManage.setQuestionCount(totalCount);

            examManageRepository.insert(examManage);
            Integer paperId = examManage.getPaperId();

            // 6. 保存试卷与题目的关联关系（增加题目序号）
            savePaperQuestions(paperId, allQuestions);
        } catch (Exception e) {
            throw new RuntimeException("组卷失败：" + e.getMessage(), e);
        }
    }

    /**
     * 校验组卷规则合法性
     */
    private void validateRule(ExamCreateRequest request) {
        // 校验总题数
        if (request.getQuestionCount() <= 0) {
            throw new IllegalArgumentException("总题数必须大于0");
        }

        // 校验难度比例
        int sumRatio = request.getDifficulty().getEasy() +
                request.getDifficulty().getMedium() +
                request.getDifficulty().getHard();
        if (sumRatio != 100) {
            throw new IllegalArgumentException("难度比例总和必须为100%");
        }

        // 校验知识点
        if (request.getKnowledgePoints() == null || request.getKnowledgePoints().isEmpty()) {
            throw new IllegalArgumentException("请至少选择一个知识点");
        }

        // 转换知识点ID类型用于校验
        List<Integer> knowledgeIntIds = request.getKnowledgePoints().stream()
                .map(Long::intValue)
                .collect(Collectors.toList());

        // 校验各难度题目是否充足
        checkQuestionEnough(knowledgeIntIds, 1,
                (int) Math.ceil(request.getQuestionCount() * request.getDifficulty().getEasy() / 100.0));
        checkQuestionEnough(knowledgeIntIds, 2,
                (int) Math.ceil(request.getQuestionCount() * request.getDifficulty().getMedium() / 100.0));
        checkQuestionEnough(knowledgeIntIds, 3,
                (int) Math.ceil(request.getQuestionCount() * request.getDifficulty().getHard() / 100.0));
    }

    /**
     * 检查指定难度的题目是否充足
     */
    private void checkQuestionEnough(List<Integer> knowledgeIds, int difficulty, int requiredCount) {
        int actualCount = multiQuestionService.selectByKnowledgeAndDifficulty(knowledgeIds, difficulty).size();
        if (actualCount < requiredCount) {
            String difficultyName = difficulty == 1 ? "容易" : (difficulty == 2 ? "中等" : "困难");
            throw new IllegalArgumentException(
                    String.format("%s题数量不足（需要%d题，仅找到%d题）", difficultyName, requiredCount, actualCount));
        }
    }

    /**
     * 筛选并随机抽取指定难度和数量的题目
     */
    private List<MultiQuestion> selectAndRandomizeQuestions(List<Integer> knowledgeIds, int difficulty, int targetCount) {
        List<MultiQuestion> candidates = multiQuestionService.selectByKnowledgeAndDifficulty(knowledgeIds, difficulty);
        // 双重校验，确保题目数量充足
        if (candidates.size() < targetCount) {
            String difficultyName = difficulty == 1 ? "容易" : (difficulty == 2 ? "中等" : "困难");
            throw new IllegalArgumentException(
                    String.format("%s题数量不足（需要%d题，仅找到%d题）", difficultyName, targetCount, candidates.size()));
        }
        Collections.shuffle(candidates);
        return candidates.stream().limit(targetCount).collect(Collectors.toList());
    }

    /**
     * 保存试卷与题目的关联关系，增加题目序号
     */
    private void savePaperQuestions(Integer paperId, List<MultiQuestion> questions) {
        for (int i = 0; i < questions.size(); i++) {
            PaperManage paperManage = new PaperManage();
            paperManage.setPaperId(paperId);
            paperManage.setQuestionId(questions.get(i).getQuestionId());
            paperManage.setQuestionType(1);
            paperManageRepository.insert(paperManage);
        }
    }
}