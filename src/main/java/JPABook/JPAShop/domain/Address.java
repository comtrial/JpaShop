package JPABook.JPAShop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

//COMMENT: 값 타입의 특징으로 생성사들 통한 생성만 가능하도록 설계 하기 위해서 Setter를 제공하지 않게 설계한다.
@Embeddable //JPA 에 내장 타입으로 존재 할 수 있음을 명시
@Getter
//@Setter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    //TODO: 아래의 기본 생성자가 없을 경우 "No default constructor for entity" 에러가 발생한다.
    // 해당 이유 분석 필요
    // * 값 타입의 불변 생성자 * JPA 기본 스펙 요구의 기본 생성자
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
