package cn.gdsdxy.questionbank.service;

import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.entity.admin.Student;
import cn.gdsdxy.questionbank.entity.admin.Teacher;
import cn.gdsdxy.questionbank.repository.TeacherRepository;
import cn.gdsdxy.questionbank.util.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TeacherService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    TeacherRepository teacherRepository;

    public String login(Teacher teacher){
        String token ="";//把token初始化为空，当登录成功后生成token赋值给token，如果token变量为空说明登录不成功
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.eq("teacherId",teacher.getTeacherId()).eq("pwd",teacher.getPwd());
        List<Teacher> teacherList = teacherRepository.selectList(wrapper);//获取查询到的记录

        if(teacherList.size()>0) {
//            if (match) {
            //登录成功返回token
            int id = teacherList.get(0).getTeacherId();
            token = JwtUtil.createTokenByShop(id);
                redisTemplate.opsForValue().set(token, id, 20, TimeUnit.MINUTES);//将token存放在缓存中，有效时间为20分钟，特别注意缓存中的键是token，值是登录的用户名
//            }
        }
        return token;
    }

    public int register(Teacher teacher) throws Exception{
        Teacher teacherInsert = new Teacher();
        teacherInsert.setTeacherId(teacher.getTeacherId());
        teacherInsert.setTeacherName(teacher.getTeacherName());
        teacherInsert.setInstitute(teacher.getInstitute());
        teacherInsert.setSex(teacher.getSex());
        teacherInsert.setTel(teacher.getTel());
        teacherInsert.setEmail(teacher.getEmail());
        teacherInsert.setPwd(teacher.getPwd());
        teacherInsert.setCardId(teacher.getCardId());
        teacherInsert.setType(teacher.getType());
        teacherInsert.setRole(teacher.getRole());

        return teacherRepository.insert(teacherInsert);

    }
    public void logout(String token) throws Exception{
        redisTemplate.delete(token);//删除缓存中键为token的元素，删除token相当于注销该用户，便后面现代该token进行请求是提示token进行token过期提示token过期或者失效
    }

    public Teacher validateLogin(Integer username, String password) {
        // 根据用户名从数据库中查找管理员
        Teacher teacher = teacherRepository.findByUsername(username);

        if (teacher != null) {
            // 如果找到了管理员记录，验证密码
            if (teacher.getPwd().equals(password)) {
                return teacher; // 验证成功，返回管理员对象
            }
        }

        return null; // 登录验证失败
    }
    public int UpdateTeacher(Teacher teacher){
        return teacherRepository.updateById(teacher);
    }
    public int DeleteTeacherId(Integer id){
        return teacherRepository.deleteById(id);
    }

    public PageInfo<Teacher> getAllTeacher(SearchPageDto searchPageDto){
        PageHelper.startPage(searchPageDto.getPage(), searchPageDto.getSize());
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        List<Teacher> teacherList = teacherRepository.selectList(teacherQueryWrapper);
        PageInfo<Teacher> PageInfo = new PageInfo(teacherList);
        PageInfo.setList(teacherList);
        return PageInfo;
    }
}
