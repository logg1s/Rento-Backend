package vn.io.rento.auth.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.io.rento.auth.dto.request.UserRequest;
import vn.io.rento.auth.dto.response.UserResponse;
import vn.io.rento.auth.entity.Permission;
import vn.io.rento.auth.entity.Role;
import vn.io.rento.auth.entity.User;
import vn.io.rento.auth.enums.EError;
import vn.io.rento.auth.exception.CustomException;
import vn.io.rento.auth.mapper.UserMapper;
import vn.io.rento.auth.repository.UserRepository;
import vn.io.rento.auth.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsById(userRequest.getUsername())) {
            throw new CustomException(EError.USER_ALREADY_EXISTED);
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new CustomException(EError.EMAIL_ALREADY_EXISTED);
        }
        User user = UserMapper.toUser(userRequest, new User());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(new HashSet<>(Set.of(new Role("USER", "", new HashSet<>(Set.of(new Permission("READ", "")))))));
        User userSaved = userRepository.save(user);
        return UserMapper.toUserResponse(userSaved, new UserResponse());
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        if (!userRepository.existsById(userRequest.getUsername())) {
            throw new CustomException(EError.USER_NOT_FOUND);
        }
        if (userRepository.existsByEmailAndUsernameIsNotLike(userRequest.getEmail(), userRequest.getUsername())) {
            throw new CustomException(EError.EMAIL_ALREADY_EXISTED);
        }
        User user = UserMapper.toUser(userRequest, new User());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User userSaved = userRepository.save(user);
        return UserMapper.toUserResponse(userSaved, new UserResponse());
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(user -> UserMapper.toUserResponse(user, new UserResponse())).toList();
    }

    @Override
    public UserResponse getUser(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new CustomException(EError.USER_NOT_FOUND));
        return UserMapper.toUserResponse(user, new UserResponse());
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }
}
