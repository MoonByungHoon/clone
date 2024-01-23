package study.clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.clone.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
