package cn.gdsdxy.questionbank.repository;

import cn.gdsdxy.questionbank.entity.admin.Student;
import cn.gdsdxy.questionbank.entity.admin.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TeacherRepository extends BaseMapper<Teacher> {
    @Select("select teacherId,teacherName,institute,sex,tel,email,cardId," +
            "type,role from teacher where teacherId = #{username} and pwd = #{password}")
    Teacher findByUsername(int username);

}
