package com.beyond.basic.b2_board.post.repository;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
//    select * from post where author_id = ? and title = ?;
//    List<Post> findByAuthorIdAndTitle(Long author, String title);

//    select * from post where author_id = ? and title = ?; order by createdTime desc;
//    List<Post> findByAuthorIdAndTitleOrderByCreatedTimeDesc(Long author, String title);
//    위와 같이 작성하면 자동으로 매개변수를 찾아서 추가해준다.
//    변수명은 author지만 authorId로도 조회 가능하다.
//    List<Post> findByAuthorId(Long authorId);

    List<Post> findByAuthor(Author author);

//    jpql을 사용한 일반 join
//    jpql는 기본적으로 lazy로딩을 지원하므로, inner join으로 filtering은 하되 post객체만 조회 -> N+1문제 여전히 발생
//    raw 쿼리 : select p.* from post p inner join author a on a.id=p.author_id;


    @Query("select p from Post p inner join p.author")
    List<Post> findAllJoin();

//    jpql을 사용한 fetch inner join
//    join시 post뿐만 아니라 author객체까지 한꺼번에 조립하여 조회 -> N+1문제 해결
//    raw 쿼리 : select * from post p inner join author a on a.id=p.author_id;


    @Query("select p from Post p inner join fetch p.author")
    List<Post> findAllFetchJoin();

//    paging처리 & delyn적용
//    org.springframework.data.domain.Pageable import
//    Page객체 안에 List<Post>포함, 전체 페이지 수 등의 정보 포함.
//    Pageable객체 안에는 페이지size, 페이지번호, 정렬기준 등이 포함
    Page<Post> findAllByDelYnAndAppointment(Pageable pageable, String delYn, String appointment);

    List<Post> findByAppointment(String appointment);
}
