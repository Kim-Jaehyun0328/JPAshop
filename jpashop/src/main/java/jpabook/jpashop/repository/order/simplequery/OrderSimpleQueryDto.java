package jpabook.jpashop.repository;

import java.time.LocalDateTime;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderSimpleQueryDto {
  private Long orderId;
  private String name;
  private LocalDateTime orderDate;
  private OrderStatus orderStatus;
  private Address address;

  public OrderSimpleQueryDto(Order order) {
    this.orderId = order.getId();
    this.name = order.getMember().getName();
    this.orderDate = order.getOrderDate();
    this.orderStatus = order.getStatus();
    this.address = order.getDelivery().getAddress();
  }
}
