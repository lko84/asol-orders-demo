package sk.lko84.mapping;

import sk.lko84.dto.UserDto;
import sk.lko84.entity.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.isActive()
        );
    }

    public static User toEntity(UserDto dto) {
        return new User(
                dto.getUserId(),
                dto.getFullName(),
                dto.getEmail(),
                dto.getPhone(),
                dto.isActive(),
                null
        );
    }
}
