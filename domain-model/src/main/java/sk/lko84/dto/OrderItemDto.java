package sk.lko84.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderItemDto {
    private String orderId;
    private long line;
    private String name;
    private Integer quantity;
    private BigDecimal price;
}
