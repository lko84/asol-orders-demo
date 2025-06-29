package sk.lko84.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.lko84.CredentialRepository;
import sk.lko84.dto.AuthRequest;
import sk.lko84.dto.AuthResponse;
import sk.lko84.entity.Credentials;
import sk.lko84.security.JwtUtil;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CredentialRepository credentialRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        Credentials credentials = credentialRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<String> roles = credentials.getRoles().stream().map(Enum::name).toList();

        String token = jwtUtil.generateToken(authRequest.getUsername(), roles);
        return ResponseEntity.ok(new AuthResponse(token, credentials.getUserId(), credentials.getUsername(), credentials.getRoles().stream().map(Enum::name).toList()));
    }
}
