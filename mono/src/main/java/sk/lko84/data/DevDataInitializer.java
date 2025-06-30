package sk.lko84.data;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sk.lko84.CredentialRepository;
import sk.lko84.OrderRepository;
import sk.lko84.UserRepository;
import sk.lko84.dto.OrderItemId;
import sk.lko84.entity.*;
import sk.lko84.id.UidProvider;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

@Component
@Profile("development")
@RequiredArgsConstructor
public class DevDataInitializer {

    private static final String SYSTEM = "system";

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final UidProvider uidProvider;

    private User admin;
    private User user;
    private User su;

    @PostConstruct
    public void init() {
        initUsers();
        initOrders();
    }

    private void initUsers() {
        if (credentialRepository.count() == 0) {
            {
                User user = new User();
                user.setUserId(uidProvider.generate());
                user.setFullName("Administrator Nobilis");
                user.setEmail("admin@test.sk");

                Credentials credentials = new Credentials();
                credentials.setUserId(user.getUserId());
                credentials.setUsername("admin");
                credentials.setPasswordHash(passwordEncoder.encode("admin"));
                credentials.setRoles(Set.of(Credentials.Role.ADMIN));
                credentials.setUser(user);
                user.setCredentials(credentials);

                setDefaultAuditInfo(user);
                setDefaultAuditInfo(credentials);

                userRepository.save(user);
                admin = user;
            }{
                User user = new User();
                user.setUserId(uidProvider.generate());
                user.setFullName("Usor Vulgaris");
                user.setEmail("user@test.sk");

                Credentials credentials = new Credentials();
                credentials.setUserId(user.getUserId());
                credentials.setUsername("user");
                credentials.setPasswordHash(passwordEncoder.encode("user"));
                credentials.setRoles(Set.of(Credentials.Role.USER));
                credentials.setUser(user);
                user.setCredentials(credentials);

                setDefaultAuditInfo(user);
                setDefaultAuditInfo(credentials);

                userRepository.save(user);
                this.user = user;
            }{
                User user = new User();
                user.setUserId(uidProvider.generate());
                user.setFullName("Usor Summus");
                user.setEmail("superuser@test.sk");

                Credentials credentials = new Credentials();
                credentials.setUserId(user.getUserId());
                credentials.setUsername("su");
                credentials.setPasswordHash(passwordEncoder.encode("su"));
                credentials.setRoles(Set.of(Credentials.Role.ADMIN, Credentials.Role.USER));
                credentials.setUser(user);
                user.setCredentials(credentials);

                setDefaultAuditInfo(user);
                setDefaultAuditInfo(credentials);

                userRepository.save(user);
                su = user;
            }
        }
    }

    private void initOrders() {
        {
            Order order = new Order();
            order.setOrderId(uidProvider.generate());
            order.setCustomer(su);
            order.setOrderDate(LocalDateTime.of(2025, 6, 29, 15, 42, 0));
            setDefaultAuditInfo(order);

            ArrayList<OrderItem> orderItems = new ArrayList<>();
            {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderItemId(new OrderItemId(order.getOrderId(), 1));
                orderItem.setName("Thing1");
                orderItem.setQuantity(5);
                orderItem.setPrice(new BigDecimal(25F));
                setDefaultAuditInfo(orderItem);

                orderItems.add(orderItem);
            }
            {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderItemId(new OrderItemId(order.getOrderId(), 2));
                orderItem.setName("Thing2");
                orderItem.setQuantity(10);
                orderItem.setPrice(new BigDecimal(10F));
                setDefaultAuditInfo(orderItem);

                orderItems.add(orderItem);
            }
            {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderItemId(new OrderItemId(order.getOrderId(), 3));
                orderItem.setName("Thing3");
                orderItem.setQuantity(1);
                orderItem.setPrice(new BigDecimal(299.99F));
                setDefaultAuditInfo(orderItem);

                orderItems.add(orderItem);
            }

            order.setItems(orderItems);

            orderRepository.save(order);
        }
    }

    private void setDefaultAuditInfo(BaseEntity entity) {
        entity.setCreatedBy(SYSTEM);
        entity.setUpdatedBy(SYSTEM);
    }

}
