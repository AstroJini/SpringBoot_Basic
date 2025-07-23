package com.beyond.basic.b2_board.author.controller;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dto.*;
import com.beyond.basic.b2_board.author.service.AuthorService;
import com.beyond.basic.b2_board.common.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // controller + responsebody
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;
    private final JwtTokenProvider jwtTokenProvider;
    // 회원가입
    @PostMapping("/create")
    // ResponseEntity<?> 모든객체 허용가능
    // dto에 있는 validation어노테이션과 controller @Valid한쌍
    public ResponseEntity<?> save(@Valid @RequestBody AuthorCreateDto authorCreateDto){
//        try{
//            this.authorService.save(authorCreateDto);
//            return new ResponseEntity<>("OK", HttpStatus.CREATED);
//        } catch(IllegalArgumentException e){
//            e.printStackTrace();
//            // 생성자 매개변수 body 부분의 객체와 header 부에 상태코드
//            return new ResponseEntity<>(new CommonErrorDto(HttpStatus.BAD_REQUEST.value(),e.getMessage()) , HttpStatus.BAD_REQUEST);
//        }
        // controllerAdvice가 없었으면 위와 같이 개별적인 예외처리가 필요하나, 이제는 아래와 같이 가능
        this.authorService.save(authorCreateDto);
        return new ResponseEntity<>("OK", HttpStatus.CREATED);

    }
    @PostMapping("/doLogin")
    public ResponseEntity<?> doLogin(@RequestBody AuthorDoLoginDto authorDoLoginDto){
        Author author = authorService.doLogin(authorDoLoginDto);
//        토큰 생성 및 return
//        payload를 조립하기 위해서 author를 보내주는 것임.
        String token = jwtTokenProvider.createAtToken(author);
        return new ResponseEntity<>
                (new CommonDto(token, HttpStatus.OK.value(), "token is created"),
                        HttpStatus.OK);
    }
    // 회원목록조회 : /author/list
    @GetMapping("/list")
//    admin권한이 있는 지를 authentication 객체에서 쉽게 확인
//    권한이 없을 경우 filterchain에서 에러 발생
    @PreAuthorize("hasRole('ADMIN')") // or hasRole('USER') 과 같이 권한을 여러명에게 지정할 수 있다.
    public List<AuthorListDto> list(){
        return authorService.findAll();
    }
    // 회원상세조회 : /author/detail/1
    // 서버에서 별도의 try catch 하지 않으면 , 에러발생시 500 에러 + 스프링의 포멧으로 에러발생
    @GetMapping("/detail/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> detail(@PathVariable Long id){
        return new ResponseEntity<>(authorService.findById(id), HttpStatus.CREATED);
    }
    @GetMapping("/myinfo")
    public ResponseEntity<?> myInfo(){
        return new ResponseEntity<>(authorService.myInfo(), HttpStatus.OK);
    }

    // 비밀번호수정 : email,password -> json
    @PatchMapping("/updatepw")
    public void updatePw(@RequestBody AuthorUpdatePwDto authorUpdatePwDto){
        authorService.updatePassword(authorUpdatePwDto);
    }
    // 회원탈퇴(삭제) : /author/delete/1
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        authorService.delete(id);
    }
}
