package sk.lko84.service;


import sk.lko84.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getAllUsers();
    Optional<UserDto> getUserById(String id);
    Optional<UserDto> getUserByEmail(String email);
    UserDto createUser(UserDto userDto);
    Optional<UserDto> updateUser(String id, UserDto userDto);
    boolean deleteUser(String id);
}