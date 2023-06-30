package com.brunosong.example1.repository;

import com.brunosong.example1.domain.Address;
import com.brunosong.example1.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save(){
        Member member = new Member();
        member.setName("song");
        member.setAddress(new Address("서울","광명","1111"));



    }




}