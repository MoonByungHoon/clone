package study.clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.clone.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
