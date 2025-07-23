package com.beyond.basic.b2_board.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class JwtTokenFilter extends GenericFilter {

    @Value("${jwt.secretKeyAt}")
    private String secretKey;

//    여기선 ServletRequest를 통해 토큰을 꺼내 와야함
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest)request;
            String bearerToken = req.getHeader("Authorization");
            if (bearerToken==null){
//            token이 없는 경우 다시 filterChain으로 되돌아가는 로직
                chain.doFilter(request, response);
                return;
            }

//        token이 있는 경우 토큰 검증 후 Authentication객체 생성
            String token = bearerToken.substring(7);
//        token 검증 및 claims 추출
//            try catch구문을 사용하지 않으면 아래의 코드에서 500 에러 발생할 수 있음
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            List<GrantedAuthority> authorities = new ArrayList<>();
//        authentication객체를 만들 때 권한은 ROLE_ 라는 키워드를 붙여서 만들어 주는 것이 추후 문제 발생 X
            authorities.add(new SimpleGrantedAuthority("ROLE_"+claims.get("role")));
            Authentication authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        //            아래의 코드를 try에서 catch단으로 내린 이유 :
        chain.doFilter(request, response);
    }
}
