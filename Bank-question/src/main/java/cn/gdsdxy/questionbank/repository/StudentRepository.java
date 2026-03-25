package cn.gdsdxy.questionbank.repository;

import cn.gdsdxy.questionbank.entity.admin.Admin;
import cn.gdsdxy.questionbank.entity.admin.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StudentRepository extends BaseMapper<Student> {
    @Select("select studentId,studentName,grade,major,clazz,institute,tel," +
            "email,cardId,sex,role from student where studentId = #{username} and pwd = #{password}")
    Student findByUsername(int username);
}
