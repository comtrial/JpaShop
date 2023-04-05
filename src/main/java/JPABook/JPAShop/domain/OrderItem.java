package JPABook.JPAShop.domain;

import JPABook.JPAShop.domain.Item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;

    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {

    }

    public void cancel() {
        this.item.addStock(this.count);
    }

    // 왜 getter 를 사용하지 않고 변수 값으로
    public int getTotalPrice() {
        return orderPrice * count;
    }
}
