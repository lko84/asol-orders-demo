package sk.lko84.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(name = "user_id", nullable = false, updatable = false, unique = true)
    private String userId;

    @ToString.Include
    @Column(nullable = false)
    private String fullName;

    @ToString.Include
    @Column(nullable = false, unique = true)
    private String email;

    @ToString.Include
    @Column
    private String phone;

    @ToString.Include
    @Column
    private boolean active = true;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Credentials credentials;

}
