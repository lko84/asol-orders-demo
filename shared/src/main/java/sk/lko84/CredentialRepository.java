package sk.lko84;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.lko84.entity.Credentials;
import sk.lko84.entity.User;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credentials, String> {

    public Optional<Credentials> findByUsername(String username);

}
