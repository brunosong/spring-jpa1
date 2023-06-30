package com.brunosong.example1.controller;

import com.brunosong.example1.domain.Address;
import com.brunosong.example1.domain.Member;
import com.brunosong.example1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }


    @PostMapping(value = "/members/new")
    public String create( @Valid MemberForm form, BindingResult result ) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);
        memberService.join(member);

        return "redirect:/";

    }


    @GetMapping(value = "/members")
    public String list(Model model) {

        //엔티티는 진짜 가장 순수하게 둬야 한다. 화면에 사용되는것을 엔티티로 하면 힘들어 진다.
        //화면때문에 비즈니스로직이 틀어지게 되면 큰일난다.
        //Member 객체를 바로 보내지 말고 DTO로 변환해서 보낸다. 간단한거라 이렇게 한거다.
        //API를 만들때는 절대 엔티티를 반환하면 안된다. 큰일난다 헥심 로직이 다 나가게 되어 버리는거다.
        //API 스펙이 변하게 되기도 한다.
        //화면에보여지는거는 그나마 괜찮은 이유가 어짜피 서버에서 랜더링을 하기때문에 크게 문제가 없다. 하지만 나는 그렇게 쓰지 않을꺼다
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }


}
