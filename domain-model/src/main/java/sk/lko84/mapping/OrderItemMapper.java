package sk.lko84.mapping;

import sk.lko84.dto.OrderItemDto;
import sk.lko84.dto.OrderItemId;
import sk.lko84.entity.Order;
import sk.lko84.entity.OrderItem;

import java.util.Collection;
import java.util.List;

public class OrderItemMapper {
    public static OrderItemDto toDto(OrderItem entity) {
        return new OrderItemDto(
                entity.getOrderItemId().getOrderId(),
                entity.getOrderItemId().getLine(),
                entity.getName(),
                entity.getQuantity(),
                entity.getPrice()
        );
    }

    public static OrderItem toEntity(OrderItemDto dto, Order order) {
        return new OrderItem(
                new OrderItemId(dto.getOrderId(), dto.getLine()),
                order,
                dto.getName(),
                dto.getQuantity(),
                dto.getPrice()
        );
    }

    public static List<OrderItem> toEntity(List<OrderItemDto> dtos, Order order) {
        return dtos.stream().map(dto -> toEntity(dto, order)).toList();
    }

}