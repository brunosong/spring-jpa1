package com.brunosong.example1.service;

import com.brunosong.example1.domain.Address;
import com.brunosong.example1.domain.Member;
import com.brunosong.example1.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;

    @Autowired MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    //@Rollback(value = false)
    void 회원가입_test() {

        //given
        Member member = new Member();
        member.setName("song");
        member.setAddress(new Address("서울","광명","1111"));

        //when
        Long id = memberService.join(member);
        Member findMember = memberService.findOne(id);

        em.flush();

        //then
        assertThat(id).isEqualTo(findMember.getId());
    }


    @Test
    void 중복회원예외_test() {

        //given
        Member member = new Member();
        member.setName("song");
        member.setAddress(new Address("서울","광명","1111"));

        Member member2 = new Member();
        member2.setName("song");
        member2.setAddress(new Address("서울","광명","1111"));

        //when
        memberService.join(member);

        //then
        assertThatThrownBy(() -> {
            memberService.join(member2);
        }).isInstanceOf(IllegalStateException.class);

    }





}