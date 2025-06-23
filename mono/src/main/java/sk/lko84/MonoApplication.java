package sk.lko84;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Log4j2
@SpringBootApplication
public class MonoApplication {
    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void logControllers() {
        Arrays.stream(context.getBeanNamesForAnnotation(RestController.class))
                .forEach(name -> log.info("Found @RestController bean: " + name));
    }

    @PostConstruct
    public void printClasspathModules() {
        ModuleLayer.boot().modules().forEach(m ->
                log.info("Classpath module: " + m.getName()));
    }
    public static void main(String[] args) {
        SpringApplication.run(MonoApplication.class, args);
    }
}
