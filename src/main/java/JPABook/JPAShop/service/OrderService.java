package JPABook.JPAShop.service;

import JPABook.JPAShop.domain.*;
import JPABook.JPAShop.domain.Item.Item;
import JPABook.JPAShop.repository.ItemRepository;
import JPABook.JPAShop.repository.MemberRepository;
import JPABook.JPAShop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        // 주문상품 생성
        //comment: 아래 처럼 createOrder 와 같이 커스텀한 생성자를 생성해서 관리하는 것이 setter 를 사용하는 것 보다 안정적이며
        // 엔티티에서 @NoArg...(ACCESSLEVEL.protected) 로 기본 생성자를 사용하지 말고 커스텀 생성자를 사용하도록 유도하는 것이 유지보수 측면에서도 좋은 설계이다.
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        // orderRepository 에는 cancel 이 없어 why!
        // 도메인에서 비즈니스 로직을 가지고 있기 때문에 repository 에서 해줄 필요 없이
        // 변경 감지로 update 쿼리 생성되서 자동 수정 된다.
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }
}
