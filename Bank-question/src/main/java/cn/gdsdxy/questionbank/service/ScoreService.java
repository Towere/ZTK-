package cn.gdsdxy.questionbank.service;

import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.dto.manage.SearchPageScoreDto;
import cn.gdsdxy.questionbank.entity.Message;
import cn.gdsdxy.questionbank.entity.manage.ExamManage;
import cn.gdsdxy.questionbank.entity.manage.Replay;
import cn.gdsdxy.questionbank.entity.manage.Score;
import cn.gdsdxy.questionbank.repository.ReplayRepository;
import cn.gdsdxy.questionbank.repository.ScoreRepository;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ScoreRepository scoreRepository;

    public List<Score> searchScore(Integer studentId) {
        // 使用ScoreRepository中的方法来根据学生Id查询成绩
        List<Score> scoreList = scoreRepository.selectList(new QueryWrapper<Score>().eq("studentId", studentId));
        System.out.println(scoreList);
        return scoreList;
    }


    // ScoreService.java 中修改 searchPageScore 方法
    public PageInfo<Score> searchPageScore(SearchPageScoreDto searchPageScoreDto) throws Exception {
        // 1. 分页参数校验（避免无效值）
        if (searchPageScoreDto.getPage() == null || searchPageScoreDto.getPage() < 1) {
            searchPageScoreDto.setPage(1);
        }
        if (searchPageScoreDto.getSize() == null || searchPageScoreDto.getSize() < 1 || searchPageScoreDto.getSize() > 100) {
            searchPageScoreDto.setSize(10); // 限制最大每页条数
        }

        // 2. 开启分页
        PageHelper.startPage(searchPageScoreDto.getPage(), searchPageScoreDto.getSize());

        // 3. 构建查询条件
        QueryWrapper<Score> scoreQueryWrapper = new QueryWrapper<>();

        // 学生ID筛选（已存在）
        if (searchPageScoreDto.getStudentId() != null) {
            scoreQueryWrapper.eq("studentId", searchPageScoreDto.getStudentId());
        }

        // 课程名称筛选（模糊查询，支持部分匹配）
        if (searchPageScoreDto.getSubject() != null && !searchPageScoreDto.getSubject().isEmpty()) {
            scoreQueryWrapper.like("subject", searchPageScoreDto.getSubject());
        }

        // 分数范围筛选（处理及格/不及格逻辑）
        if (searchPageScoreDto.getMinScore() != null) {
            scoreQueryWrapper.ge("etScore", searchPageScoreDto.getMinScore()); // >= 最低分（如60分及格）
        }
        if (searchPageScoreDto.getMaxScore() != null) {
            scoreQueryWrapper.le("etScore", searchPageScoreDto.getMaxScore()); // <= 最高分（如59分不及格）
        }

        // 4. 执行查询并排序（按考试日期倒序，便于查看最新成绩）
        scoreQueryWrapper.orderByDesc("answerDate");
        List<Score> scoreList = scoreRepository.selectList(scoreQueryWrapper);

        // 5. 封装分页结果（PageInfo自动处理分页信息）
        return new PageInfo<>(scoreList);
    }

    public void addScore(Score score) {
        scoreRepository.insert(score);
    }

}