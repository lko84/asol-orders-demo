package sk.lko84.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.lko84.OrderRepository;
import sk.lko84.dto.OrderDto;
import sk.lko84.entity.Order;
import sk.lko84.id.UidProvider;
import sk.lko84.mapping.OrderMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UidProvider uidProvider;

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    @Override
    public Optional<OrderDto> getOrderById(String id) {
        return orderRepository.findById(id).map(OrderMapper::toDto);
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(uidProvider.generate());
        Order order = OrderMapper.toEntity(orderDto);
        return OrderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public Optional<OrderDto> updateOrder(String id, OrderDto orderDto) {
        return orderRepository.findById(id)
                .map(existing -> {
                    orderDto.setOrderId(id);
                    Order updated = OrderMapper.toEntity(orderDto);
                    return OrderMapper.toDto(orderRepository.save(updated));
                });
    }

    @Override
    public boolean deleteOrder(String id) {
        return orderRepository.findById(id).map(order -> {
            orderRepository.delete(order);
            return true;
        }).orElse(false);
    }
}
