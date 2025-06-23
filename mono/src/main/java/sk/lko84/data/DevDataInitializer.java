package sk.lko84.data;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sk.lko84.CredentialRepository;
import sk.lko84.UserRepository;
import sk.lko84.entity.Credentials;
import sk.lko84.entity.Order;
import sk.lko84.entity.User;
import sk.lko84.id.UidProvider;

import java.util.Set;

@Component
@Profile("development")
@RequiredArgsConstructor
public class DevDataInitializer {

    private static final String SYSTEM = "system";

    private final UserRepository userRepository;
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

                user.setCreatedBy(SYSTEM);
                user.setUpdatedBy(SYSTEM);
                credentials.setCreatedBy(SYSTEM);
                credentials.setUpdatedBy(SYSTEM);

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

                user.setCreatedBy(SYSTEM);
                user.setUpdatedBy(SYSTEM);
                credentials.setCreatedBy(SYSTEM);
                credentials.setUpdatedBy(SYSTEM);

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

                user.setCreatedBy(SYSTEM);
                user.setUpdatedBy(SYSTEM);
                credentials.setCreatedBy(SYSTEM);
                credentials.setUpdatedBy(SYSTEM);

                userRepository.save(user);
                su = user;
            }
        }
    }

    private void initOrders() {
        {
            Order order = new Order();
            order.setOrderId(uidProvider.generate());
        }
    }

}
