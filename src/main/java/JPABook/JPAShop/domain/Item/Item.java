package JPABook.JPAShop.domain.Item;

import JPABook.JPAShop.domain.Category;
import JPABook.JPAShop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype") //type 별 구분하기 위해서
@Getter
@Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    //COMMENT : (Domain 주도 개발 관점에서) 아래와 같이 Setter 를 통한 데이터의 변경보다
    //아래와 같이 데이터 변경에 대한 비즈니스 로직을 통한 데이터 접근, 변경이 보다 바람직 하다.
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;

        if (restStock < 0) {
            throw new NotEnoughStockException("not enough stock");
        }

        this.stockQuantity = restStock;
    }
}

