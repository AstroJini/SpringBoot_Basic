package com.beyond.basic.b2_board.common;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//logback 객체 만드는 방법 2.

@Slf4j
@RestController
public class LogController {
//    logback 객체 만드는 방법 1
//    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @GetMapping("/log/test")
    public String logTest(){
//        기존의 system print는 spring에서는 잘 사용되지 않음.
//        이유 : 1) 성능이 떨어짐 2)로그 분류작업 불가
        System.out.println("hello world");

////        가장 많이 사용되는 로그 라이브러리 : logback
////        logger가 성능이 더 좋고 분류작업 모두 가능
////        log lever(프로젝트 차원에 설정) : trace < debug < info < error
//        logger.trace("trace 로그입니다.");//출력 안될 것임
//        logger.debug("debug 로그입니다.");//출력 안될 것임
//        logger.error("error 로그입니다.");
//        logger.info("info 로그입니다.");
//        return "ok";

//        Slf4j 어노테이션을 선언시 log라는 변수로 logback객체 사용가
        log.info("Slf4j 테스트");

        try {
            log.info("에러테스트 시작");
            throw new RuntimeException("에러테스트");
        }catch (RuntimeException e){
//            +보다 , 로 두 메세지를 이어서 출력하는 것이 성능에 더 좋음
            log.error("에러메시지 : ", e);
//            e.printStackTrace(); // 성능 문제로 logback쓰는게 더 좋음
        }
        return "ok";
    }
}
