package com.companioncar.dal.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.util.Strings;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

public class JWTUtil {

    private static String JWT_SECERT = "MDk3ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";

    /**
     * 签发JWT
     * @param id
     * @param subject 可以是JSON数据 尽可能少
     * @param ttlMillis
     * @return  String
     *
     */
    public static String createJWT(String id, String subject, String issuer, String role, Long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        byte[] bytes = DatatypeConverter.parseBase64Binary(JWT_SECERT);
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)   // 主题
                .setIssuer(issuer)     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, bytes); // 签名算法以及密匙
        if(Strings.isNotBlank(role)){
            builder.claim("role", role);
        }
        if (ttlMillis != null) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate); // 过期时间
        }
        return builder.compact();
    }

    /**
     *
     * 解析JWT字符串
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(JWT_SECERT))
                .parseClaimsJws(jwt).getBody();
        claims.getSubject();
        return claims;
    }

    public static void main(String[] args) {
        String token = JWTUtil.createJWT(UUIDUtil.getUUID(), "id123456", "admin", "admin",1000*60*5L);
        System.out.println(token);
        Claims claims = null;
        try {
            claims = JWTUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(claims);
        String role = (String) claims.get("role");
        Date expiration = claims.getExpiration();
        System.out.println(role);
        System.out.println("过期时间："+ expiration);
    }
}
