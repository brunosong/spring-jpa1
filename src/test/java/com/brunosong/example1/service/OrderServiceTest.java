package com.brunosong.example1.service;

import com.brunosong.example1.domain.Address;
import com.brunosong.example1.domain.Member;
import com.brunosong.example1.domain.Order;
import com.brunosong.example1.domain.OrderStatus;
import com.brunosong.example1.domain.item.Book;
import com.brunosong.example1.domain.item.Item;
import com.brunosong.example1.exception.NotEnoughStockException;
import com.brunosong.example1.repository.ItemRepository;
import com.brunosong.example1.repository.MemberRepository;
import com.brunosong.example1.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 상품주문_test() {

        //given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격, 재고

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        Order order = orderRepository.findOne(orderId);

        assertThat(OrderStatus.ORDER).isEqualTo(order.getStatus());   // 주문 상태로 들어갔는지 확인
        assertThat(order.getOrderItems().size()).isEqualTo(1);   // 주문한 상품종류 수가 맞는지
        assertThat(order.getTotalPrice()).isEqualTo(10000 * 2);   //주문한 총 가격은 10000원 짜리 2개다
        assertThat(item.getStockQuantity()).isEqualTo(8);       //10개에서 2개 주문 했으니 재고는 2개가 남는다.

    }


    @Test
    public void 상품주문_재고수량초과() {

        //given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격, 재고

        int orderCount = 20;

        //when
        assertThatThrownBy(() -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        }).isInstanceOf(NotEnoughStockException.class);


    }


    @Test
    public void 주문취소() {

        //given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격, 재고

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(item.getStockQuantity()).isEqualTo(10);

    }


    private Item createBook(String name, int price, int stockQuantity) {

        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;

    }


    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

}