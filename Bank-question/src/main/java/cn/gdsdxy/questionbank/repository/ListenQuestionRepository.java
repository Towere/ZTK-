package cn.gdsdxy.questionbank.repository;

import cn.gdsdxy.questionbank.entity.questions.ListeningQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ListenQuestionRepository extends BaseMapper<ListeningQuestion> {

}
