package com.brunosong.example1.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    String city;
    String street;
    String zipcode;

    protected Address() {}

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    // 값타입이라는건 변경되면 안된다. 그래서 생성자로 만들고 setter 를 지운다. 변경불가능한 클래스를 만드는 것이다.
    // setter 를 막기위해서는 생성자로 생성을 했지만 jpa (스펙상)에서 기본생성자를 사용하여 뭔가를 한다. 그래서 기본생성자를
    // 만들어 줘야 하는데 외부에서 만들 가능성이 있기 때문에 jpa 에서는 protected 까지 허용을 해준다.


}
