package sk.lko84.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderItemId implements Serializable {

    @Column(name = "order_id", nullable = false, updatable = false)
    private String orderId;

    @Column(name = "order_item_id", nullable = false, updatable = false)
    private long line;
}
