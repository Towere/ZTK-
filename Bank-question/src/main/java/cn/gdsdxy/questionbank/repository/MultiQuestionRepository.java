package cn.gdsdxy.questionbank.repository;

import cn.gdsdxy.questionbank.entity.admin.Admin;
import cn.gdsdxy.questionbank.entity.questions.MultiQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Mapper
@Repository
public interface MultiQuestionRepository extends BaseMapper<MultiQuestion> {

}
