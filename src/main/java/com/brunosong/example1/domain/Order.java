package com.brunosong.example1.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter @Getter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)   //@OneToOne 은 어디에 둬도 상관이 없다. 하지만 업무가 중요한 쪽에 둔다. 딜리버리에서 오더를 찾는경우는 거의 없기 때문이다.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;  //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;   //주문상태

    protected Order() {}

    /* 연관관계 메서드 */
    public void setMember(Member member) {
        this.member = member;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /* 생성 메서드 */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);  //CASECADE 가 걸려있기 때문에 Order를 save 할때 자동으로 인썰트 된다.

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);   //CASECADE 가 걸려있기 때문에 Order를 save 할때 자동으로 인썰트 된다.
        }

        // CASECADE를 써줄수 있는곳은 Delivery 나 OrderItem 처럼 Order에서만 사용하는 것이야 한다. 왜냐면은 같이 저장이 되기 때문이다.
        // 다른데서 OrderItem 이나 Delivery 을 참조하면 문제가 생길수 있다.

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    /* ===비즈니스 로직=== */

    /*
        주문취소
     */
    public void cancel() {
        // 이미 주문이 완료 되었으면
        if(DeliveryStatus.COMP == delivery.getStatus()) {
            throw new IllegalStateException("이미 배송완료 상품은 취소가 불가능 합니다.");
        }

        // 배송이 안되었다면 오더에 상태를 취소로 바꾼다.
        this.setStatus(OrderStatus.CANCEL);

        // 오더들에 들어가져 있던 아이템들을 취소시켜줘서 재고를 다시 넣어준다.
        for (OrderItem orderItem: orderItems) {
            orderItem.cancel();
        }
    }


    /*
    * 조회 로직 ( 전체 주문 가격을 조회 )
    * */
    public int getTotalPrice() {
        int totalPrice = orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
        return totalPrice;
    }





}
