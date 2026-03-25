package cn.gdsdxy.questionbank.constant;

public class JwtConstant {//该类的常量值要根据具体的项目进行设置
    /* 请求头相关 */
    public static final String HEADER_NAME = "Authorization"; //前端页面将token存放在请求头Header的Authorization中
    public static final String ISSUER = "gdsdxy";
    public static final String SUBJECT_SHOP = "shop";
    public static final String SECRET_KEY = "gdsdxy"; // Secret密钥
    public static final long TOKEN_EXPIRE_TIME = 10000; // token过期时间(分钟)
}
