package study.clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.clone.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
