package study.clone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.clone.entity.time.BaseEntity;
import study.clone.exception.NotEnoughStockException;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Item extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  private int price;
  private int stockQuantity;

  @OneToMany(mappedBy = "item")
  private List<Review> reviewList = new ArrayList<>();

  public void setReviewList(List<Review> reviewList) {
    this.reviewList = reviewList;
  }

  public void removeStock(int stock) {
    int restStock = this.stockQuantity - stock;

    if (restStock < 0) {
      throw new NotEnoughStockException("재고가 없습니다.");
    }

    this.stockQuantity = restStock;
  }


  public void addStock(int count) {
  }
}
