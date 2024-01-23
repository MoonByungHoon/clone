package study.clone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.clone.entity.time.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "order_id")
  private Long id;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus; //[ORDER, CANCEL]

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "member_member_id")
  private Member member;

  public Order(Member member, Delivery delivery, OrderStatus orderStatus) {
    this.member = member;
    this.delivery = delivery;
    this.orderStatus = orderStatus;
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  public void setMember(Member member) {
    this.member = member;
    member.getOrders().add(this);
  }

  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
    orderItem.setOrder(this);
  }

  public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
    Order order = new Order(member, delivery, OrderStatus.ORDER);

    for (OrderItem orderItem : orderItems) {
      order.addOrderItem(orderItem);
    }

    return order;
  }

  public void cancel() {
    if (delivery.getDeliveryStatus() == DeliveryStatus.COMP) {
      throw new IllegalStateException("이미 배송이 완료된 상품입니다.");
    }

    this.changeStatus(OrderStatus.CANCEL);

    for (OrderItem orderItem : orderItems) {
      orderItem.orderCancel();
    }
  }

  private void changeStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }
}