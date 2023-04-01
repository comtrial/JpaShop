package JPABook.JPAShop.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable //JPA 에 내장 타입으로 존재 할 수 있음을 명시
@Getter @Setter
public class Address {

    private String city;
    private String Street;
    private String zipcode;
}
