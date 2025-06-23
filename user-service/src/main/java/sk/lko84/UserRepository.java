package sk.lko84;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.lko84.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

}
