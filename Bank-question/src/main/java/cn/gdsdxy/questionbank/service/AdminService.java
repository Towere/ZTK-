package cn.gdsdxy.questionbank.service;

import cn.gdsdxy.questionbank.entity.admin.Admin;
import cn.gdsdxy.questionbank.entity.admin.Student;
import cn.gdsdxy.questionbank.repository.AdminRepository;
import cn.gdsdxy.questionbank.repository.StudentRepository;
import cn.gdsdxy.questionbank.util.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AdminService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    AdminRepository adminRepository;

    public String login(Admin admin){
        String token ="";//把token初始化为空，当登录成功后生成token赋值给token，如果token变量为空说明登录不成功
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("adminId",admin.getAdminId()).eq("pwd",admin.getPwd());
        List<Admin> adminList = adminRepository.selectList(wrapper);//获取查询到的记录

        if(adminList.size()>0) {
//            if (match) {
            //登录成功返回token
            int id = adminList.get(0).getAdminId();
            token = JwtUtil.createTokenByShop(id);
                redisTemplate.opsForValue().set(token, id, 20, TimeUnit.MINUTES);//将token存放在缓存中，有效时间为20分钟，特别注意缓存中的键是token，值是登录的用户名
//            }
        }
        return token;
    }

    public void logout(String token) throws Exception{
        redisTemplate.delete(token);//删除缓存中键为token的元素，删除token相当于注销该用户，便后面现代该token进行请求是提示token进行token过期提示token过期或者失效
    }


    public Admin validateLogin(Integer username, String password) {
        // 根据用户名从数据库中查找管理员
        Admin admin = adminRepository.findByUsername(username);

        if (admin != null) {
            // 如果找到了管理员记录，验证密码
            if (admin.getPwd().equals(password)) {
                return admin; // 验证成功，返回管理员对象
            }
        }

        return null; // 登录验证失败
    }
}
