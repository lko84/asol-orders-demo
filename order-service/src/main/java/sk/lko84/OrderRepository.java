package sk.lko84;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.lko84.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

}
