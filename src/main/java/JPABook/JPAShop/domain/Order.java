package JPABook.JPAShop.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") //원래는 기본적으로 클래스 이름 따라 가능대 order 는 예약어라 바까줘야대(테이블 명 변경하는 방법)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id") //테이블명_id 스타일로 pk 컬럼명
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //매핑을 뭘로 할거냐에 대한 설정으로 pk 이름이 member_id 에 해주겠다 명시
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //==연관관계 편의 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void setOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for (OrderItem orderItem : orderItems) {
            order.orderItems.add(orderItem);
        }

        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER);
        return order;
    }

    //==비즈니스 로직==//
    public void cancel() {
        if (this.delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배달이 완료되었습니다");
        }

        for (OrderItem orderItem: this.orderItems) {
            orderItem.cancel();
        }

        this.status = OrderStatus.CANCEL;
    }

    //==조회 로직==//
    public int getTotalPrice() {
//        int totalPrice = this.orderItems.stream().mapToInt(OrderItem::getOrderPrice).sum();
        return this.orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }
}
