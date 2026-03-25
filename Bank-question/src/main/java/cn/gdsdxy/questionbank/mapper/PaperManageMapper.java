package cn.gdsdxy.questionbank.mapper;

import cn.gdsdxy.questionbank.dto.AllQuestionDto;
import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.entity.manage.PaperManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface PaperManageMapper {
    @Select("select question, subject, score, section,level, \"选择题\" as type from multi_question " +
            "union select  question, subject, score, section,level, \"判断题\" as type  from judge_question " +
            "union select  question, subject, score, section,level, \"填空题\" as type from fill_question")
    List<AllQuestionDto> findAll(SearchPageDto searchPageDto);

    @Select("select * from paper_manage where paperId = #{paperId}")
    PaperManage findById(Integer paperId);
}
