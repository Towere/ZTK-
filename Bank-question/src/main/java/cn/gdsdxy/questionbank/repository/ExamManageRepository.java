package cn.gdsdxy.questionbank.repository;


import cn.gdsdxy.questionbank.entity.manage.ExamManage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ExamManageRepository extends BaseMapper<ExamManage> {
}
