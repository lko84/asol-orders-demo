package sk.lko84.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderDto {
    private String orderId;
    private String customerId;
    private LocalDateTime orderDate;
    private List<OrderItemDto> items;
}
