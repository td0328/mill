package com.myth.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author xuqiang
 * @since 2022/11/8
 */
public class JwtUtils {

    public static final long EXPIRE = 1000 * 60 * 60 * 24;//token过期时间   24小时
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";//密钥


    public static String getJwtToken(String id, String account){

        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("jacob-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id", id)
                .claim("account", account)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 根据token，判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据request判断token是否存在与有效（也就是把token取出来罢了）
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("UserToken");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取用户id
     * @param request
     * @return
     */
    public static String getUserIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("UserToken");
        if(StringUtils.isEmpty(jwtToken)) return "";
        try {
            // 这里解析可能会抛异常，所以try catch来捕捉
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).                            parseClaimsJws(jwtToken);
            Claims claims = claimsJws.getBody();
            return (String)claims.get("id");
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 根据token获取用户的account
     * @param request
     * @return
     */
    public static String getUserAccountByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("UserToken");
        if(StringUtils.isEmpty(jwtToken)) return "";
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
            Claims claims = claimsJws.getBody();
            return (String)claims.get("account");
        }catch (Exception e){
            //e.printStackTrace();
            return "";
        }
    }
    public static boolean isTokenExpired(HttpServletRequest request){
        String jwtToken = request.getHeader("UserToken");
        if(StringUtils.isEmpty(jwtToken)) return false;
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
            Claims claims = claimsJws.getBody();
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        }catch (Exception e){
            //e.printStackTrace();
            return false;
        }
    }
}

