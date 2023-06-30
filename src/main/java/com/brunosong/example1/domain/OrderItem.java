package com.brunosong.example1.domain;

import com.brunosong.example1.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    
    private int orderPrice; //주문 당시에 가격
    
    private int count; //주문 당시에 수량

    protected OrderItem() {}  // new OrderItem 을 생성하지 못하도록 설정 

    /* === 생성 메서드 === */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        //재고의 수량을 줄여준다.
        item.removeStock(count);

        return orderItem;
    }


    /* 비즈니스 로직*/
    public void cancel(){
        //상품의 재고를 원복 시켜 준다.
        getItem().addStock(count);

    }

    /* 아이템의 총 가격을 구해준다. */
    public int getTotalPrice() {
        return item.getPrice() * count;
    }
}
