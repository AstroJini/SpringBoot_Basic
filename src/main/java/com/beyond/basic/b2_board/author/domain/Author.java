package com.beyond.basic.b2_board.author.domain;

import com.beyond.basic.b2_board.author.dto.AuthorDetailDto;
import com.beyond.basic.b2_board.author.dto.AuthorListDto;
import com.beyond.basic.b2_board.common.BaseTimeEntity;
import com.beyond.basic.b2_board.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@ToString
// JPA 를 사용할경우 Entity 반드시 붙여야하는 어노테이션
// JPA 의 EntityManager 에게 객체를 위임하기 위한 어노테이션
// 엔티티매니저는 영속성컨텍스트(엔티티의 현재상황)를 통해 db 데이터 관리
@Entity
// Builder어노테이션을 통해 유연하게 객체 생성 가능.
@Builder
public class Author extends BaseTimeEntity {
    @Id // pk 설정
//    indentity : auto_increment, auto는 : id 생성전략을 jpa 에게 자동설정하도록 위임하는것.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    컬럼에 별다른 설정이 없을경우 default varchar(255)
    private String name;
    @Column(length=50, unique = true, nullable = false)
    private String email;
//    @Column(name = "pw") : 되도록이면 컬럼명과 변수명을 일치시키는것이 개발의 혼선을 줄일수 있음.
    private String password;
    @Enumerated(EnumType.STRING)
    @Builder.Default // 빌더패턴에서 변수 초기화(디폴트값)시 builder.default어노테이션 필수
    private Role role = Role.User;

//    OneToMany는 선택사항, 또한 default가 lazy
//    mappedBy에는 ManyToOne쪽에 변수명을 문자열로 지정
//    mappedBy를 써야하는 실질적인 이유 : fk관리를 반대변(post)쪽에서 한다는 의미 -> 연관관계의주인설정
//    Cascade 옵션 : 부모 객체의 변화에 따라 자식 객체가 같이 변하는 옵션 | 1. persist : 저장, 2. remove : 삭제 , all : 모든 옵션이 가능함
//    orphanRemoval = true 는 예를들어 member, post, comment 테이블이 각각 1:n관계에 있을 때 멤버 탈퇴를 하면 댓글까지 모두 삭제될 수 있도록
//    만들어주는 것이다.
//    orphanRemoval : 자식의 자식까지 모두 삭제 할 경우 orphanRemoval = true 옵션 추가해야만 한다.
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
//    초기화 필수이다.
    List<Post> postList = new ArrayList<>();

    @OneToOne(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Adress adress;


//    public Author(String name, String 정mail, String password,  Role role) {
//        //this.id = AuthorMemoryRepository.id;
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.role = role;
//    }
    public void updatePw(String password){
        this.password = password;
    }

//    public AuthorDetailDto detailFromEntity(){
//        return new AuthorDetailDto(this.id, this.name, this.email);
//    }


    public AuthorListDto listFromEntity(){
        return new AuthorListDto(this.id, this.name, this.email);
    }


}
