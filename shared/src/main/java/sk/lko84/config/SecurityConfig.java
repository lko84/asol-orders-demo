package sk.lko84.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import sk.lko84.security.JwtAuthFilter;
import sk.lko84.service.DefaultUserDetailsService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.stream.Collectors;

@Configuration
@Log4j2
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final DefaultUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // no sessions for JWT
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/login",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/auth/login"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                // .formLogin(Customizer.withDefaults()) // Testing
                // .httpBasic(Customizer.withDefaults()) // BasicAuth fallback
                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public Filter logHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request);

                StringBuilder sb = new StringBuilder();

                sb.append("Request ").append(request.getMethod()).append(" ").append(request.getRequestURI()).append("\n");

                // Log headers
                Enumeration<String> headerNames = request.getHeaderNames();
                sb.append("Headers:\n");
                while (headerNames.hasMoreElements()) {
                    String header = headerNames.nextElement();
                    sb.append("  ").append(header).append(": ").append(request.getHeader(header)).append("\n");
                }

                // Log query string
                if (request.getQueryString() != null) {
                    sb.append("Query string: ").append(request.getQueryString()).append("\n");
                }

                // Log body
                String body = new BufferedReader(cachedRequest.getReader()).lines().collect(Collectors.joining("\n"));
                sb.append("Body:\n").append(body);

                logger.info(sb.toString());

                // Continue filter chain
                filterChain.doFilter(cachedRequest, response);

            }
        };
    }

}
