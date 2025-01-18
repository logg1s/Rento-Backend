package vn.io.rento.auth.service.impl;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.io.rento.auth.dto.request.UserCreateRequest;
import vn.io.rento.auth.dto.request.UserUpdateRequest;
import vn.io.rento.auth.dto.response.UserResponse;
import vn.io.rento.auth.entity.Role;
import vn.io.rento.auth.entity.User;
import vn.io.rento.auth.enums.EError;
import vn.io.rento.auth.enums.ERole;
import vn.io.rento.auth.exception.CustomException;
import vn.io.rento.auth.mapper.UserMapper;
import vn.io.rento.auth.repository.RoleRepository;
import vn.io.rento.auth.repository.UserRepository;
import vn.io.rento.auth.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        if (userRepository.existsById(userCreateRequest.getUsername())) {
            throw new CustomException(EError.USER_ALREADY_EXISTED);
        }
        if (userRepository.existsByEmail(userCreateRequest.getEmail())) {
            throw new CustomException(EError.EMAIL_ALREADY_EXISTED);
        }
        if (roleRepository.existsAllByNameIn(userCreateRequest.getRoles())) {
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(userCreateRequest.getRoles()));
            User user = UserMapper.toUser(userCreateRequest, new User());
            user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
            user.setRoles(roles);
            User userSaved = userRepository.save(user);
            return UserMapper.toUserResponse(userSaved, new UserResponse());
        }
        throw new CustomException(EError.ROLE_NOT_FOUND);
    }
    private boolean hasRole(String roleName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + roleName));
    }
    @Override
    @PreAuthorize("hasRole(T(vn.io.rento.auth.enums.ERole).ADMIN.name()) || #userUpdateRequest.username == authentication.name")
    public UserResponse updateUser(UserUpdateRequest userUpdateRequest) {
        String requestedEmail = userUpdateRequest.getEmail();
        String requestedUsername = userUpdateRequest.getUsername();

        User user = userRepository.findById(requestedUsername)
                .orElseThrow(() -> new CustomException(EError.USER_NOT_FOUND));

        if (userRepository.existsByEmailAndUsernameIsNotLike(requestedEmail, requestedUsername)) {
            throw new CustomException(EError.EMAIL_ALREADY_EXISTED);
        }

        Set<String> requestedRoles = userUpdateRequest.getRoles();

        if (hasRole(ERole.ADMIN.name()) && requestedRoles != null && !requestedRoles.isEmpty() && !roleRepository.existsAllByNameIn(requestedRoles)) {
            throw new CustomException(EError.ROLE_NOT_FOUND);
        }

        if (hasRole(ERole.ADMIN.name()) && requestedRoles != null) {
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(requestedRoles));
            user.setRoles(roles);
        }

        UserMapper.toUser(userUpdateRequest, user);

        String requestedPassword = userUpdateRequest.getPassword();
        if (requestedPassword != null) user.setPassword(passwordEncoder.encode(requestedPassword));

        User userSaved = userRepository.save(user);
        return UserMapper.toUserResponse(userSaved, new UserResponse());
    }

    @Override
    @PreAuthorize("hasRole(T(vn.io.rento.auth.enums.ERole).ADMIN.name())")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(user -> UserMapper.toUserResponse(user, new UserResponse())).toList();
    }

    @Override
    @PostAuthorize("hasRole(T(vn.io.rento.auth.enums.ERole).ADMIN.name()) || returnObject.username == authentication.name && hasAuthority('READ')")
    public UserResponse getUser(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new CustomException(EError.USER_NOT_FOUND));
        return UserMapper.toUserResponse(user, new UserResponse());
    }

    @Override
    @PreAuthorize("hasRole(T(vn.io.rento.auth.enums.ERole).ADMIN.name())")
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }
}
