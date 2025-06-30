package sk.lko84.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderDto {
    private String orderId;
    private UserDto customer;
    private LocalDateTime orderDate;
    private List<OrderItemDto> items;
}
