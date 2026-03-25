package cn.gdsdxy.questionbank.repository;


import cn.gdsdxy.questionbank.entity.manage.Knowledge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface KnowledgeRepository extends BaseMapper<Knowledge> {
    List<Knowledge> selectBySubject(String subject);
}
