package cn.gdsdxy.questionbank.repository;

import cn.gdsdxy.questionbank.entity.admin.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminRepository extends BaseMapper<Admin> {
    @Select("select adminId,adminName,sex,tel,email,cardId,role from admin where adminId = #{username} and pwd = #{password}")
    Admin findByUsername(int username);
}
