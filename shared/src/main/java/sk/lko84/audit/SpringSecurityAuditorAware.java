package sk.lko84;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.data.domain.AuditorAware;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return Optional.of(authentication.getName());
        }

        // TODO: Job/service name from thread context
        //if () {
        //  return Optional.ofNullable(ThreadContext....);
        //}

        return Optional.of("system");
    }

}
