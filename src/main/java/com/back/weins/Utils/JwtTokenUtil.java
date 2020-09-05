package com.back.weins.Utils;

import com.alibaba.fastjson.JSON;

import com.back.weins.Constant.CheckResult;
import com.back.weins.Constant.Constant;
import com.back.weins.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.ibatis.annotations.Mapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.security.Key;
import java.util.Date;

// implements Serializable

@Component
@Repository
public class JwtTokenUtil {
//    private static final long serialVersionUID = -2550185165626007488L;
//    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
//    @Value("${jwt.secret}")
//    private String secret;
//    //retrieve username from jwt token
//    public String getUsernameFromToken(String token) {
//        return getClaimFromToken(token, Claims::getSubject);
//    }
//    //retrieve expiration date from jwt token
//    public Date getExpirationDateFromToken(String token) {
//        return getClaimFromToken(token, Claims::getExpiration);
//    }
//    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getAllClaimsFromToken(token);
//        return claimsResolver.apply(claims);
//    }
//    //for retrieveing any information from token we will need the secret key
//    private Claims getAllClaimsFromToken(String token) {
//        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//    }
//    //check if the token has expired
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//    //generate token for user
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        return doGenerateToken(claims, userDetails.getUsername());
//    }
//    //while creating the token -
////1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
////2. Sign the JWT using the HS512 algorithm and secret key.
////3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
////   compaction of the JWT to a URL-safe string
//    private String doGenerateToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//                .signWith(SignatureAlgorithm.HS512, secret).compact();
//    }
//    //validate token
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }








    //    fjaodjflajfja

    static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static SecretKey generalKey(){
        String stringKey = Constant.JWT_SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        System.out.println(encodedKey);
        System.out.println(Base64.encodeBase64URLSafeString(encodedKey));
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static String createJWT(String id, String subject, long ttlMillis){
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(key);
        if(ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static Claims parseJWT(String jwt) throws Exception {
        jwt = jwt.replace("Bearer", "");
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();

    }

    public static CheckResult validateJWT(String jwtStr) throws Exception {
        CheckResult checkResult = new CheckResult();

        Claims claims = null;
        try {
            claims = parseJWT(jwtStr);
            checkResult.setSuccess(true);
        }
        catch (ExpiredJwtException e) {
            checkResult.setErrCode(1);
            checkResult.setSuccess(false);
        }
        catch (Exception e) {
            checkResult.setErrCode(3);
            checkResult.setSuccess(false);
        }
        return checkResult;

    }

    public static String generealSubject(User user) {
        return JSON.toJSONString(user);
    }

}
