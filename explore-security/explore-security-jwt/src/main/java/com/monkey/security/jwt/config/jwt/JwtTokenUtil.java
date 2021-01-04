package com.monkey.security.jwt.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtTokenUtil {
    private String secret;
    private Long expiration;
    private String header;


    /**
     * 通过用户名生成令牌
     * @param userDetails 用户信息
     * @return 令牌
     */
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<String, Object>(){{
            put("sub", userDetails.getUsername());
            put("created", new Date());
        }};
        return generateToken(claims);
    }

    /**
     * 通过令牌获取用户名
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameByFromToken(String token){
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    /**
     * 判断token是否过期
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token){
        Claims claims = getClaimsFromToken(token);
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    /**
     * 刷新令牌
     * @param token 旧令牌
     * @return 新令牌
     */
    public String refreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        claims.put("created", new Date());
        return generateToken(claims);
    }

    /**
     * 验证令牌，用户名正确且令牌未过期才算有效
     * @param token 令牌
     * @param userDetails 用户信息
     * @return 是否有效
     */
    public Boolean validateToken(String token, UserDetails userDetails){
        String username = getUsernameByFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 通过给定的claims生成令牌
     * @param claims 数据声明
     * @return 令牌
     */
    private String generateToken(Map<String, Object> claims){
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        return Jwts.builder().setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }

    /**
     * 从令牌中获取数据声明
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token){
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            e.printStackTrace();
            claims = null;
        }
        return claims;
    }
}
