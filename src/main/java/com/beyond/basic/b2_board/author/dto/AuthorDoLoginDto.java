package com.beyond.basic.b2_board.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorDoLoginDto {
    private String email;
    private String password;
}
