package com.beyond.basic.b2_board.author.repository;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dto.AuthorDoLoginDto;
import com.beyond.basic.b2_board.author.dto.AuthorListDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorMemoryRepository {
    private List<Author> authorList = new ArrayList<>();
    private List<AuthorListDto> authorListDto = new ArrayList<>();
    public static Long id = 1L;
    public void save(Author author) {
        authorList.add(author);
        id++;
    }

    public List<Author> findAll() {
        return this.authorList;
    }

    public Optional<Author> findById(Long id){
        Author author = null;
        for(Author a : authorList){
            if(a.getId().equals(id)){
                author = a;
            }
        }
        return Optional.ofNullable(author);
    }

    public Optional<Author> findByEmail(String email){
        Author author = null;
        for(Author a : authorList){
            if(a.getEmail().equals(email)){
                author = a;
            }
        }
        return Optional.ofNullable(author);
    }

    public void delete(Long id){
    // id 값으로 요소의 index값을 찾아 삭제
        for(int i = 0; i < this.authorList.size(); i++){
            if(this.authorList.get(i).getId().equals(id)){
                this.authorList.remove(i);
                break;
            }
        }
    }
}
