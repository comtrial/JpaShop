package JPABook.JPAShop.api;

import JPABook.JPAShop.domain.Address;
import JPABook.JPAShop.domain.Order;
import JPABook.JPAShop.domain.OrderStatus;
import JPABook.JPAShop.repository.OrderRepository;
import JPABook.JPAShop.repository.OrderSearch;
import JPABook.JPAShop.repository.order.simpleQuery.OrderSimpleQueryDto;
import JPABook.JPAShop.repository.order.simpleQuery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
//        return orderRepository.findAllByString(new OrderSearch()).stream()
//                .map(o -> new SimpleOrderDto(o)) //.map(SimpleOrderDto::new) 람다 레퍼런스로도 가능
//                .collect(Collectors.toList());
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }


    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        return orderRepository.finaAllWithMeberDelivery().stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
    }


    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {

        return orderSimpleQueryRepository.findOrderDtos();

    }
    // client 팀이랑 협의 된 api 스펙의 데이터
    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;


        public SimpleOrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName(); // Lazy 초기화
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress(); // Lazy 초기화
        }
    }
}

