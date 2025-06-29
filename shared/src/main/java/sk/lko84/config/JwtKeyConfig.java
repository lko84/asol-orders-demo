package sk.lko84.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
@Primary
@Log4j2
public class JwtKeyConfig {

    private static final int MIN_HS512_KEY_LENGTH = 64; // 512 bits / 8 = 64 bytes

    @Value("${jwt.secret:}")
    private String configuredSecret;

    @Bean
    public SecretKey jwtSecretKey() {
        if (configuredSecret != null && !configuredSecret.isEmpty()) {
            byte[] keyBytes = configuredSecret.getBytes(StandardCharsets.UTF_8);

            if (keyBytes.length >= MIN_HS512_KEY_LENGTH) {
                log.info("Loaded JWT secret key from configuration ({} bytes)", keyBytes.length);
                return Keys.hmacShaKeyFor(keyBytes);
            } else {
                log.warn("Provided JWT secret key is too short ({} bytes). Generating a secure random key instead.", keyBytes.length);
            }
        } else {
            log.warn("No JWT secret provided in configuration. Generating a secure random key.");
        }

        SecretKey generatedKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        log.info("Generated secure random JWT secret key ({} bytes)", generatedKey.getEncoded().length);
        return generatedKey;
    }
}