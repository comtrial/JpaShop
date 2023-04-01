package JPABook.JPAShop.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id") //primary key name 을 통해 연관관계가 설정 되기 때문에
    private Long id;

    private String name;

    @Embedded //내장 타입을 포함한다
    private Address address;

    @OneToMany(mappedBy = "member") //난 그냥 Orders table 의 member 필드에 매핑 된거얌
    private List<Order> orders = new ArrayList<>();
}
