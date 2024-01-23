package study.clone.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Food")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends Item {

  private String originFood;
  private int weight;
}
