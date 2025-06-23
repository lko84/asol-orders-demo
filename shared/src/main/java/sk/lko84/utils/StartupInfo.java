package sk.lko84.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class StartupInfo {

    @Value("${server.port:8080}")
    private int serverPort;

    @Value("${springdoc.api-docs.path:/v3/api-docs}")
    private String apiDocsPath;

    @Value("${springdoc.swagger-ui.path:/swagger-ui.html}")
    private String swaggerUiPath;

    @Value("${spring.profiles.active:none}")
    private String profiles;

    @EventListener(ApplicationReadyEvent.class)
    public void doLogStartupInfo() {
        String baseUrl = "http://localhost:" + serverPort;

        log.info("\n📚 API Docs available at:");
        log.info("🔗 Swagger UI: " + baseUrl + swaggerUiPath);
        log.info("🔗 OpenAPI JSON: " + baseUrl + apiDocsPath);
        log.info("Active profiles: " + profiles + "\n");
    }
}
