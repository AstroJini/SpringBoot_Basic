package com.beyond.basic.b2_board.post.dto;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dto.AuthorListDto;
import com.beyond.basic.b2_board.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostCreateDto {
    @NotEmpty
    private String title;
    private String contents;
    @Builder.Default
    private String appointment = "N";
//    LocalDateTime으로 받는 것 보다 String으로 받아서 직접 형변환 하는게 오류가 덜나고 쉬움
//    시간 정보는 직접 LocalDateTime으로 형변환 하는 경우가 많음.
    private String appointmentTime;

//    이제는 authorId값을 추가해서는 안되는 상황
//    @NotNull // 숫자는 NotEmpty 사용불가
//    private Long authorId;


    public Post toEntity(Author author, LocalDateTime appointmentTime){
        return Post.builder()
                .title(this.title)
                .contents(this.contents)
//                .authorId(this.authorId)
                .author(author)
                .delYn("N")
                .appointment(this.appointment)
                .appointmentTime(appointmentTime)
                .build();
    }

}
