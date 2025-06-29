package sk.lko84.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sk.lko84.CredentialRepository;
import sk.lko84.service.DefaultUserDetailsService;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final DefaultUserDetailsService userDetailsService;
    private final CredentialRepository credentialRepository;
    private final SecretKey jwtSecretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("Authorization Header: {}", request.getHeader("Authorization"));

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String jwt = header.substring(7);
            String username = jwtUtil.extractUsername(jwt); // new method you'll need

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.isValid(jwt, userDetails)) {
                    List<SimpleGrantedAuthority> authorities = getAuthoritiesFromToken(jwt);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }


    private List<SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey) // use injected key
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
