package cn.gdsdxy.questionbank.service;

import cn.gdsdxy.questionbank.dto.LoginResultDto;
import cn.gdsdxy.questionbank.dto.Total_usersDto.Total_users;
import cn.gdsdxy.questionbank.entity.admin.Admin;
import cn.gdsdxy.questionbank.entity.admin.Student;
import cn.gdsdxy.questionbank.entity.admin.Teacher;
import cn.gdsdxy.questionbank.repository.AdminRepository;
import cn.gdsdxy.questionbank.repository.StudentRepository;
import cn.gdsdxy.questionbank.repository.TeacherRepository;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AdminService adminService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;


    public Admin adminLogin(Integer username, String password) {
        // 在这里实现 Admin 用户的登录验证逻辑
        // 检查用户名和密码是否匹配
        // 如果匹配，返回 Admin 对象；否则返回 null
        return adminService.validateLogin(username, password);
    }

    public Teacher teacherLogin(Integer username, String password) {
        // 在这里实现 Teacher 用户的登录验证逻辑
        // 检查用户名和密码是否匹配
        // 如果匹配，返回 Teacher 对象；否则返回 null
        return teacherService.validateLogin(username, password);
    }

    public Student studentLogin(Integer username, String password) {
        // 在这里实现 Student 用户的登录验证逻辑
        // 检查用户名和密码是否匹配
        // 如果匹配，返回 Student 对象；否则返回 null
        return studentService.validateLogin(username, password);
    }

    public LoginResultDto getAllTotal_users(Total_users total_users) {
        // 1. 验证管理员
        QueryWrapper<Admin> adminWrapper = new QueryWrapper<>();
        adminWrapper.eq("adminId", total_users.getUser()).eq("pwd", total_users.getPwd());
        List<Admin> adminList = adminRepository.selectList(adminWrapper);
        if (adminList != null && !adminList.isEmpty()) {
            LoginResultDto result = new LoginResultDto();
            result.setUserType(0);
            result.setUserId(adminList.get(0).getAdminId()); // 提取管理员ID
            System.out.println(result);
            return result;
        }

        // 2. 验证教师
        QueryWrapper<Teacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.eq("teacherId", total_users.getUser()).eq("pwd", total_users.getPwd());
        List<Teacher> teacherList = teacherRepository.selectList(teacherWrapper);
        if (teacherList != null && !teacherList.isEmpty()) {
            LoginResultDto result = new LoginResultDto();
            result.setUserType(1);
            result.setUserId(teacherList.get(0).getTeacherId()); // 提取教师ID
            System.out.println(result);
            return result;
        }

        // 3. 验证学生
        QueryWrapper<Student> studentWrapper = new QueryWrapper<>();
        studentWrapper.eq("studentId", total_users.getUser()).eq("pwd", total_users.getPwd());
        List<Student> studentList = studentRepository.selectList(studentWrapper);
        if (studentList != null && !studentList.isEmpty()) {
            LoginResultDto result = new LoginResultDto();
            result.setUserType(2);
            result.setUserId(studentList.get(0).getStudentId()); // 提取学生ID
            System.out.println(result);
            return result;
        }

        return null; // 登录失败
    }

}


