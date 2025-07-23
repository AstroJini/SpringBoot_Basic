package com.beyond.basic.b3_servlet;


import com.beyond.basic.b1_hello.controller.Hello;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

//servlet은 사용자의 req를 쉽게 처리하고, 사용자에게 res를 쉽게 조립해주는 기술
//servlet에서는 url매핑을 메서드 단위가 아닌, 클래스 단위로 지정
@WebServlet("/servlet/get")
public class ServletRestGet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Hello hello = new Hello();
        hello.setName("hong");
        hello.setEmail("hong@naver.com");

//        header
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(hello);
//        body
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(body);
        printWriter.flush();
    }
}
