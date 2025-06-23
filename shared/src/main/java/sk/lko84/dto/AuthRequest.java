package sk.lko84.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AuthRequest{
    private String username;
    private String password;
}
