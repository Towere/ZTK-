package cn.gdsdxy.questionbank.repository;


import cn.gdsdxy.questionbank.entity.manage.Replay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReplayRepository extends BaseMapper<Replay> {
}
