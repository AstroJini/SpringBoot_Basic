package com.beyond.basic.b2_board.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.*;

//로그인 관련한 설정정보에 관한 클래스이다.
@Configuration
public class SecurityConfig {

//    내가 만든 객체는 Component, 외부 라이브러리를 활용한 객체는 Bean+Configuration
//    Bean은 메서드 위에 붙여 Return되는 객체를 싱글톤 객체로 생성한다.
//    Component는 클래스 위에 붙여 클래스 자체를 싱글톤 객체로 생성한다.
//    filter 계층에서 filter 로직을 커스텀한다.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
//                cors: 특정 도메인에 대한 허용정책, postman은 웹 브라우저가 아니기 때문에 cors정책에 적용 x
                .cors(c->c.configurationSource(corsConfiguration()))
                .build();
    }

    private CorsConfigurationSource corsConfiguration(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("*")); // 모든 HTTP(get, post 등) 메서드 허용
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더요소(Authorization 등) 허용
        configuration.setAllowCredentials(true); // 자격 증명 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        **의 의미는 계층구조를 허용한다는 의미이다. (/author/list/...과 같이)
        source.registerCorsConfiguration("/**", configuration); //모든 url패턴에 대해 cors설정 적용
        return source;
    }
}
