package sk.lko84.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "credentials")
public class Credentials extends BaseEntity {

    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(name = "user_id")
    private String userId;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @ElementCollection(fetch = FetchType.EAGER)
    @ToString.Include
    @CollectionTable(name = "credentials_roles", joinColumns = @JoinColumn(name = "credentials_id"))
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public enum Role {
        ADMIN,
        USER
    }
}