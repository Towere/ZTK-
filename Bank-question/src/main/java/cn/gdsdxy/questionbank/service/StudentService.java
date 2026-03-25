package cn.gdsdxy.questionbank.service;

import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.entity.admin.Admin;
import cn.gdsdxy.questionbank.entity.admin.Student;
import cn.gdsdxy.questionbank.entity.manage.ExamManage;
import cn.gdsdxy.questionbank.repository.StudentRepository;
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
public class StudentService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StudentRepository studentRepository;

    public String login(Student student){
        String token ="";//把token初始化为空，当登录成功后生成token赋值给token，如果token变量为空说明登录不成功
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("studentId",student.getStudentId()).eq("pwd",student.getPwd());
        List<Student> studentList = studentRepository.selectList(wrapper);//获取查询到的记录

        if(studentList.size()>0) {
//            if (match) {
            //登录成功返回token
            int id = studentList.get(0).getStudentId();
            token = JwtUtil.createTokenByShop(id);
                redisTemplate.opsForValue().set(token, id, 20, TimeUnit.MINUTES);//将token存放在缓存中，有效时间为20分钟，特别注意缓存中的键是token，值是登录的用户名
//            }
        }
        return token;
    }

    public int register(Student student) throws Exception{
        Student studentInsert = new Student();
        studentInsert.setStudentId(student.getStudentId());
        studentInsert.setStudentName(student.getStudentName());
        studentInsert.setGrade(student.getGrade());
        studentInsert.setMajor(student.getMajor());
        studentInsert.setClazz(student.getClazz());
        studentInsert.setInstitute(student.getInstitute());
        studentInsert.setTel(student.getTel());
        studentInsert.setEmail(student.getEmail());
        studentInsert.setPwd(student.getPwd());
        studentInsert.setCardId(student.getCardId());
        studentInsert.setCardId(student.getCardId());
        studentInsert.setSex(student.getSex());
        studentInsert.setRole(student.getRole());

        return studentRepository.insert(studentInsert);

    }
    public void logout(String token) throws Exception{
        redisTemplate.delete(token);//删除缓存中键为token的元素，删除token相当于注销该用户，便后面现代该token进行请求是提示token进行token过期提示token过期或者失效
    }

    public Student validateLogin(Integer username, String password) {
        // 根据用户名从数据库中查找管理员
        Student student = studentRepository.findByUsername(username);

        if (student != null) {
            // 如果找到了管理员记录，验证密码
            if (student.getPwd().equals(password)) {
                return student; // 验证成功，返回管理员对象
            }
        }

        return null; // 登录验证失败
    }

    public int UpdateStudent(Student student){
        return studentRepository.updateById(student);
    }
    public int DeleteStudentId(Integer id){
        return studentRepository.deleteById(id);
    }
    public PageInfo<Student> getAllStudent(SearchPageDto searchPageDto){
        PageHelper.startPage(searchPageDto.getPage(), searchPageDto.getSize());
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        List<Student> studentList = studentRepository.selectList(studentQueryWrapper);
        PageInfo<Student> PageInfo = new PageInfo(studentList);
        PageInfo.setList(studentList);
        return PageInfo;
    }

}
