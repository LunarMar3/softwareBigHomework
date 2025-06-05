package com.scut.softwareBigHomework.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class JwtUtils {
    private static final String SECRET_KEY = "Minecraft";

    private final static SecureDigestAlgorithm<SecretKey,SecretKey> SHA256 = Jwts.SIG.HS256;

    public static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());



    // 生成 Token
    public static String generateToken(String username,Integer id) {
        // 令牌id
        String uuid = UUID.randomUUID().toString();
        Date exprireDate = Date.from(Instant.now().plusSeconds(3600));

        return Jwts.builder()
                // 设置头部信息header
                .header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                // 设置自定义负载信息payload
                .claim("username", username)
                .claim("id",id)
                // 令牌ID
                .id(uuid)
                // 过期日期
                .expiration(exprireDate)
                // 签发时间
                .issuedAt(new Date())
                // 签名
                .signWith(KEY, SHA256)
                .compact();
    }


    // 解析 Token
    public static Jws<Claims> parseToken(String token) {
        return Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token);
    }

    public static JwsHeader parseHeader(String token) {
        return parseToken(token).getHeader();
    }

    public static Claims parsePayload(String token) {
        return parseToken(token).getPayload();
    }

    // 检验是否过期
    public static boolean isExpired(String token) {
        return parsePayload(token).getExpiration().before(new Date());
    }

}
