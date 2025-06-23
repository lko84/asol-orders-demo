package sk.lko84.service;

import sk.lko84.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderDto> getAllOrders();
    Optional<OrderDto> getOrderById(String id);
    OrderDto createOrder(OrderDto orderDto);
    Optional<OrderDto> updateOrder(String id, OrderDto orderDto);
    boolean deleteOrder(String id);
}
