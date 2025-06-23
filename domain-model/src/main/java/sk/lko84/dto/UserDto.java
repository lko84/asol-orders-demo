package sk.lko84.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDto {
    private String userId;
    private String fullName;
    private String email;
    private String phone;
    private boolean active;
}
