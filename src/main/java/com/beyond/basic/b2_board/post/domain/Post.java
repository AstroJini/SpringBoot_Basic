package com.beyond.basic.b2_board.post.domain;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.common.BaseTimeEntity;
import com.beyond.basic.b2_board.post.dto.PostListDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(length = 3000)
    private String contents;
    private String delYn;

// FK 설정시 ManyToOne 필수
// ManyToOne에서는 default fetch EAGER(즉시로딩)
// 그래서, 일반적으로 fetch LAZY(지연로딩)설정 : author객체를 사용하지 않는한, author 객체로 쿼리발생x
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id") // fk 관계성
    private Author author;
}
