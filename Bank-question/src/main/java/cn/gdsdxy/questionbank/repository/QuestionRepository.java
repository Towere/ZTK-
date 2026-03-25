package cn.gdsdxy.questionbank.repository;

import cn.gdsdxy.questionbank.entity.questions.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionRepository extends BaseMapper<Question> {
    List<Question> selectByKnowledgeAndDifficulty(
            @Param("knowledgeIds") List<Long> knowledgeIds,
            @Param("difficulty") Integer difficulty
    );
}
