package sk.lko84.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(name = "order_id", nullable = false, updatable = false, unique = true)
    private String orderId;

    @ManyToOne(optional = false)
    @ToString.Include
    @JoinColumn(name = "user_id")
    private User customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @ToString.Include
    @Column(nullable = false)
    private LocalDateTime orderDate;

}
