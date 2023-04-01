package JPABook.JPAShop.Domain;

import JPABook.JPAShop.Domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") //원래는 기본적으로 클래스 이름 따라 가능대 order 는 예약어라 바까줘야대(테이블 명 변경하는 방법)
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id") //테이블명_id 스타일로 pk 컬럼명
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id") //매핑을 뭘로 할거냐에 대한 설정으로 pk 이름이 member_id 에 해주겠다 명시
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
