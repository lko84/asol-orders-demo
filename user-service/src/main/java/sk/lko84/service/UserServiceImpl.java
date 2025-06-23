package sk.lko84.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.lko84.dto.UserDto;
import sk.lko84.entity.User;
import sk.lko84.mapping.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public Optional<UserDto> getUserById(String id) {
        return userRepository.findById(id).map(UserMapper::toDto);
    }

    @Override
    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(UserMapper::toDto);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.toEntity(userDto);
        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    public Optional<UserDto> updateUser(String id, UserDto userDto) {
        return userRepository.findById(id)
                .map(existing -> {
                    userDto.setUserId(id);
                    User updated = UserMapper.toEntity(userDto);
                    return UserMapper.toDto(userRepository.save(updated));
                });
    }

    @Override
    public boolean deleteUser(String id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }
}