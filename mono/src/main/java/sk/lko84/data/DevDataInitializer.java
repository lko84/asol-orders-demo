package sk.lko84.data;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sk.lko84.CredentialRepository;
import sk.lko84.UserRepository;
import sk.lko84.entity.Credentials;
import sk.lko84.entity.User;
import sk.lko84.id.UidProvider;

@Component
@Profile("development")
@RequiredArgsConstructor
public class DevDataInitializer {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final UidProvider uidProvider;

    @PostConstruct
    public void init() {
        if (credentialRepository.count() == 0) {
            User user = new User();
            user.setUserId(uidProvider.generate());
            user.setFullName("Administrator Nobilis");
            user.setEmail("admin@test.sk");

            Credentials credentials = new Credentials();
            credentials.setUserId(user.getUserId());
            credentials.setUsername("admin");
            credentials.setPasswordHash(passwordEncoder.encode("admin"));
            credentials.setUser(user); // set bidirectional if needed
            user.setCredentials(credentials);

            userRepository.save(user); // cascade saves credentials
        }
    }
}
