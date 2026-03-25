package cn.gdsdxy.questionbank.repository;

import cn.gdsdxy.questionbank.entity.manage.Score;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ScoreRepository extends BaseMapper<Score> {

}
