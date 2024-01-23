package study.clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.clone.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
