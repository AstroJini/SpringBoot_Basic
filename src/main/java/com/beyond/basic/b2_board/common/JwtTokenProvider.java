package com.beyond.basic.b2_board.common;

import com.beyond.basic.b2_board.author.domain.Author;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

@Component
public class JwtTokenProvider {

//    yml에서 설정한 값을 그대로 가져오는 것이다.
    @Value("${jwt.expirationAt}")
    private int experationAt;

    @Value("${jwt.secretKeyAt}")
    private String secreatKeyAt;


    private Key secret_at_key;

//    이 메서드를 따로 빼둔 이유: 이 싱글톤객체가 생성될 때 딱 한번만 생성되어야하기 때문임
    @PostConstruct
//    스프링 빈이 만들어지는 시점에 빈이 만들어진 직후에 아래 메서드가 바로 실행된다
    public void init(){
        secret_at_key = new SecretKeySpec(java.util.Base64.getDecoder().decode(secreatKeyAt), SignatureAlgorithm.HS512.getJcaName());
    }

    public String createAtToken(Author author){
        String email = author.getEmail();
        String role = author.getRole().toString();
//        claims는 페이로드(사용자 정보를 의미함) | .setSubject는 유일한 구분자 역할을 한다.
//        주된 키 값을 제외한 나머지 사용자정보는 put 사용하여 key:value 세팅
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
//                발행시간
                .setIssuedAt(now)
//                만료시간
                .setExpiration(new Date(now.getTime() + experationAt*60*1000L)) // 30분을 세팅(밀리초 단위)
//                secreat키를 통해 signature생성
                .signWith(secret_at_key)
                .compact();
        return token;
    }
}
