package sk.lko84.entity;

import jakarta.persistence.*;
import lombok.*;
import sk.lko84.dto.OrderItemId;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

    @EmbeddedId
    @ToString.Include
    @EqualsAndHashCode.Include
    private OrderItemId orderItemId;

    @MapsId("order_id") // maps the orderId in the embedded ID
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    @ToString.Include
    private String name;

    @Column(nullable = false)
    @ToString.Include
    private Integer quantity;

    @Column(nullable = false, scale = 2, precision = 10)
    @ToString.Include
    private BigDecimal price;

}

