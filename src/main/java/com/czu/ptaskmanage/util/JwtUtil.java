package com.czu.ptaskmanage.util;

import io.jsonwebtoken.*;
import java.util.Date;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private long expire; // 单位：毫秒

    private byte[] secretKeyBytes;

    @PostConstruct
    public void init() {
        // 这里可以做些初始化操作，比如把secret转成byte数组
        secretKeyBytes = secret.getBytes();
    }

    // 生成token，payload放openid
    public String generateToken(String openid) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + expire);

        return Jwts.builder()
                .setSubject(openid)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secretKeyBytes)
                .compact();
    }

    // 解析token，返回openid，验证签名和过期
    public String parseToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKeyBytes)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token过期");
        } catch (JwtException e) {
            throw new RuntimeException("Token无效");
        }
    }
}

