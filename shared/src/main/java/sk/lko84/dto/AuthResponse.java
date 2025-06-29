package sk.lko84.dto;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AuthResponse{
    private String token;
    private String userid;
    private String username;
    private List<String> roles;
}