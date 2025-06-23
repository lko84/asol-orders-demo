package sk.lko84.mapping;

import sk.lko84.dto.OrderDto;
import sk.lko84.dto.OrderItemDto;
import sk.lko84.dto.UserDto;
import sk.lko84.entity.Order;

import java.util.List;

public class OrderMapper {

    public static OrderDto toDto(Order entity) {
        List<OrderItemDto> items = entity.getItems().stream()
                .map(OrderItemMapper::toDto)
                .toList();

        return new OrderDto(
                entity.getOrderId(),
                entity.getCustomer().getUserId(),
                entity.getOrderDate(),
                items
        );
    }

    public static Order toEntity(OrderDto dto) {
        Order order = new Order();
        order.setOrderId(dto.getOrderId());
        // order.setCustomer(UserMapper.toEntity(customer));
        order.setOrderDate(dto.getOrderDate());
        order.setItems(OrderItemMapper.toEntity(dto.getItems(), order));
        return order;
    }
}
