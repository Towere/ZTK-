package cn.gdsdxy.questionbank.util;


import cn.gdsdxy.questionbank.constant.JwtConstant;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class JwtUtil {
    /**
     * 加密算法
     */
    private static final Algorithm algorithm = Algorithm.HMAC512(JwtConstant.SECRET_KEY);

    /**
     * 功能描述：创建前台JWT     *
     * @param customerId
     * @return 返回token，即jwt
     */
    public static String createTokenByShop(int customerId) {
//        Date now = Date.from(Instant.now());
        Date now=new Date();
        String token = JWT.create()
                //下面前7项是JWT PayLoad负载中的官方设置
                .withIssuer(JwtConstant.ISSUER) // 用于说明该JWT是由谁签发的
                .withSubject(JwtConstant.SUBJECT_SHOP) // 用于说明该JWT面向的对象
                .withAudience(JwtConstant.SUBJECT_SHOP) // 用于说明该JWT发送给的用户
                .withExpiresAt(Date.from(ZonedDateTime.now().plusMinutes(JwtConstant.TOKEN_EXPIRE_TIME).toInstant())) // 数字类型，说明该JWT过期的时间  //ZonedDateTime.now().plusMinutes()此方法基于此日期时间返回添加了分钟数的ZonedDateTime
                .withNotBefore(now) // 生效时间
                .withIssuedAt(now) // 签发时间
                .withClaim("customerId",customerId)
                .withJWTId(UUID.randomUUID().toString()) // 说明标明JWT的唯一ID
                //下面这项是PayLoad负载中的自定义设置，通过withClaim()方法进行设置，可以设置多个withClaim
                .withClaim(JwtConstant.SUBJECT_SHOP, customerId)//该代码要改为实际项目中需要存放的对象
                .sign(algorithm);//algorithm=Algorithm.HMAC512(JwtConstant.SECRET_KEY)。使用HMAC512算法，用JwtConstant.SECRET_KEY密钥进行加密

        return token;
    }
    /**
     * 功能描述：解析验证token
     *
     * @param token 加密后的token字符串
     * @return 返回TRUE或FALSE表示token验证成功或失败
     */
    public static Boolean verifyToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return Boolean.FALSE;
        }
        try {
            JWTVerifier verifier = JWT.require(algorithm)//验证时一定要跟生成时的 算法 和 签名 一致
                    .withIssuer(JwtConstant.ISSUER)
                    .withAudience(JwtConstant.SUBJECT_SHOP)
                    .withSubject(JwtConstant.SUBJECT_SHOP)
                    //单位秒: 可以接受过期的时间长度,比如过期时间为15:30:00,可以往后延45秒，那么过期时间为15:30:45
                    //.acceptExpiresAt(45)
                    //单位秒：可以接受提前使用的时间长度，比如NotBefore定以为15:30:00，那么在到时间之前正常token都不可用
                    //设置为60，代表提前一分钟可以用  那么token在15:29:00就可以用了
                    //.acceptNotBefore(60)
//                    .acceptExpiresAt(System.currentTimeMillis() + JwtConstant.TOKEN_EXPIRE_TIME * 1000) //JWT 正确的配置续期姿势
                    .build();
            verifier.verify(token);
            return Boolean.TRUE;
            //以上代码就已经完成验证了，如果没有出异常，就验证成功！出现了就是验证失败
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Boolean.FALSE;
        }
    }

    /**
     * 功能描述：解析token，获取token中包含的用户信息
     *
     * @param token
     * @return 返回customerId
     * @throws JWTDecodeException
     */
    public static Integer getShopByToken(String token) throws JWTDecodeException {
        DecodedJWT decodedJWT = JWT.decode(token);
        if (!JwtConstant.SUBJECT_SHOP.equals(decodedJWT.getSubject())) {
            throw new RuntimeException("签名验证失败");
        }
        return decodedJWT.getClaim(JwtConstant.SUBJECT_SHOP).asInt();//获取customerId，与上面红色字体存放的内容对应。如果customerId参数类型是String，就把asInt()改为asString()
    }
}
