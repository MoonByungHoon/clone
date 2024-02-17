package study.clone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import study.clone.entity.time.BaseEntity;

@Getter
@Entity
public class Review extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String writer;
  private String comment;

  @ManyToOne
  @JoinColumn(name = "item_id")
  private Item item;

}