package com.brunosong.example1.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)  // mappedBy는 연관관계의 주인이 아닌 쪽에서 사용한다. 현재이 연관관계의 주인은 Order에 Member member 가 fk 를 가지고있다.
    private List<Order> orders = new ArrayList<>();

}
