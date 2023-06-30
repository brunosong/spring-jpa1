package com.brunosong.example1.repository;

import com.brunosong.example1.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    EntityManager em;

    @Test
    void update_test() {

        Book book = em.find(Book.class, 1L);

        //TX
        book.setName("asdfadf");   //이렇게 하면 변경감지를 일으켜 값이 자동으로 업데이트 된다. 이걸 더티체킹이라고 한다.

        // 변경감지 -> dirty checking 기본적으로 이런 매커니즘으로 jpa는 값을 변경할수 있다.
        // 준영속 엔티티 : 영속성 컨텍스트가 더는 관리하지 않는 엔티티를 말한다.
    }

}