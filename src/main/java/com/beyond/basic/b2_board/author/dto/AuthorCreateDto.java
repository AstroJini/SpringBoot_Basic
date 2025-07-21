package com.beyond.basic.b2_board.author.dto;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.domain.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data // dto 계층은 데이터의 안정성이 엔티티만큼 중요하지느 않으므로, setter 도 추가
public class AuthorCreateDto {
    @NotEmpty(message = "이름은 필수 입력 항목입니다")
    private String name;
    @NotEmpty(message = "이메일은 필수 입력 항목입니다")
    private String email;
    @NotEmpty(message = "비밀번호는 필수 입력 항목입니다")
    @Size(min=8,max=20)
    private String password;
    // 문자열로 값이 넘어오면 Role에 값으로 매핑
    private Role role = Role.User;

//    public Author authorToEntity(){
//        return new Author(this.name, this.email, this.password, this.role);
//    }
    public Author authorToEntity() {
        // 빌더패턴은 매개변수의 개수와 매개변수의 순서에 상관없이 객체생성가능
        return Author.builder()
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .role(this.role)
                .build();
    }
}
