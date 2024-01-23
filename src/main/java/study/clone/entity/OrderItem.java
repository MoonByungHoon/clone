package study.clone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item; //주문 상품

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "order_order_id")
  private Order order; //주문

  private int count; //주문 수량

  public OrderItem(Item item, int count) {
    this.item = item;
    this.count = count;

    item.removeStock(count);
  }

  //  주문에 대한 상품 관련 정보 생성
  public static OrderItem createOrderItem(Item item, int count) {
    item.removeStock(count);

    OrderItem orderItem = new OrderItem(item, count);

    return orderItem;
  }

  public void orderCancel() {
    getItem().addStock(count);
  }

  public void setOrder(Order order) {
    this.order = order;
  }
}