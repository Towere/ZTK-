package cn.gdsdxy.questionbank.fliter;

import cn.gdsdxy.questionbank.constant.JwtConstant;
import cn.gdsdxy.questionbank.dto.base.Result;
import cn.gdsdxy.questionbank.util.JwtUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /* 根据项目的实际需求设置白名单URL，即白名单中的URL不需要判断token是否正确，其它都要判断token是否正确*/
        List<String> WHITE_LIST = new ArrayList<String>() {{
            add("/");
            add("/swagger-ui.html");
            add("/api-docs");
            add("/webjars");
            add("/swagger");
            add("/static/"); // 静态路径
            add("/home"); // 前台首页
            add("/admin/login");
            add("login");// 前台账号（登录、注册）
            add("/images");
            add("/shop/register");
            add("/wrongPhoto");
            add("/api-docs");
            add("/mystatic/");
            add("/api/register");
            add("/shop/item");
            add("/shop/");
            add("/api/shop/deleteItem");
            add("/api/order/addOrder");
            add("/api/api/upload");
            add("/api/shop/selectItems");
            add("/api/uploadFile");
            add("/api/mystatic/");
        }};
        for (String url : WHITE_LIST) {
            if (request.getRequestURI().indexOf(url) >= 0) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        /* 获取header中的token信息 */
        String token = request.getHeader(JwtConstant.HEADER_NAME);//获取header中的token信息，该值要跟请求为header的属性名一致才能正常获取，如果方法的参数为/"Authorization"，则postmane请求为headers中必须包含
        //("Authorization","值")的建值对，vue发送请求时必须携带headers，格式如下this
        //this.axios.get("/books",{
        //        headers: {
        //            "Authorization": sessionStorage.getItem('Authorization')
        //          }
        //      })

        response.setCharacterEncoding("UTF-8");
        //如果不包含token，则显示“token格式出错”，直接返回
        if (StringUtils.isEmpty(token)) {
            Result result = new Result();
            result =  result.serverError("token格式出错","");
            String resultString = JSON.toJSONString(result);
            response.setContentType("application/json;charset=utf-8;");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(resultString);
            return;
        }

        //如果验证token失败则显示“token验证失败”，直接返回
        if (!JwtUtil.verifyToken(token)) {
            Result result = new Result();
            result =  result.serverError("token验证出错","");
            String resultString = JSON.toJSONString(result);
            response.setContentType("application/json;charset=utf-8;");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(resultString);
            return;
        }

        //token是合法的，则判断token是否在有效期之内
        Object tokenObject = redisTemplate.opsForValue().get(token);

        if (tokenObject==null) {
            Result result = new Result();
            result =  result.serverError("登录过期，请重新登录","");
            String resultString = JSON.toJSONString(result);
            response.setContentType("application/json;charset=utf-8;");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(resultString);
            return;
        } else {

            //更新缓存的有效期
            redisTemplate.expire(token, 20, TimeUnit.MINUTES);//将键为token的缓存对象的有效期设置为20分钟
            filterChain.doFilter(request, response);
        }
    }
}
