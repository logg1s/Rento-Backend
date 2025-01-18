package vn.io.rento.auth.mapper;

import org.springframework.stereotype.Component;
import vn.io.rento.auth.dto.request.UserCreateRequest;
import vn.io.rento.auth.dto.request.UserUpdateRequest;
import vn.io.rento.auth.dto.response.RoleResponse;
import vn.io.rento.auth.dto.response.UserResponse;
import vn.io.rento.auth.entity.Role;
import vn.io.rento.auth.entity.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class UserMapper {
    private UserMapper() {
    }

    public static User toUser(UserCreateRequest userCreateRequest, User user) {
        user.setUsername(userCreateRequest.getUsername() == null ? user.getUsername() : userCreateRequest.getUsername());
        user.setPassword(userCreateRequest.getPassword() == null ? user.getPassword() : userCreateRequest.getPassword());
        user.setEmail(userCreateRequest.getEmail() == null ? user.getEmail() : userCreateRequest.getEmail());
        user.setFirstName(userCreateRequest.getFirstName() == null ? user.getFirstName() : userCreateRequest.getFirstName());
        user.setLastName(userCreateRequest.getLastName() == null ? user.getLastName() : userCreateRequest.getLastName());
        user.setPhoneNumber(userCreateRequest.getPhoneNumber() == null ? user.getPhoneNumber() : userCreateRequest.getPhoneNumber());
        user.setDateOfBirth(userCreateRequest.getDateOfBirth() == null ? user.getDateOfBirth() : userCreateRequest.getDateOfBirth());
        user.setEnabled(userCreateRequest.getEnabled() == null ? user.getEnabled() : userCreateRequest.getEnabled());
        // need to manual map role
        return user;
    }

    public static User toUser(UserUpdateRequest userUpdateRequest, User user) {
        user.setPassword(userUpdateRequest.getPassword() == null ? user.getPassword() : userUpdateRequest.getPassword());
        user.setEmail(userUpdateRequest.getEmail() == null ? user.getEmail() : userUpdateRequest.getEmail());
        user.setFirstName(userUpdateRequest.getFirstName() == null ? user.getFirstName() : userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName() == null ? user.getLastName() : userUpdateRequest.getLastName());
        user.setPhoneNumber(userUpdateRequest.getPhoneNumber() == null ? user.getPhoneNumber() : userUpdateRequest.getPhoneNumber());
        user.setDateOfBirth(userUpdateRequest.getDateOfBirth() == null ? user.getDateOfBirth() : userUpdateRequest.getDateOfBirth());
        user.setEnabled(userUpdateRequest.getEnabled() == null ? user.getEnabled() : userUpdateRequest.getEnabled());
        // need to manual map role
        return user;
    }

    public static UserResponse toUserResponse(User user, UserResponse userResponse) {
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        userResponse.setCreatedBy(user.getCreatedBy());
        userResponse.setUpdatedBy(user.getUpdatedBy());
        userResponse.setDateOfBirth(user.getDateOfBirth());
        userResponse.setEnabled(user.getEnabled());
        userResponse.setRoles(user.getRoles().stream().map(r -> RoleMapper.toRoleResponse(r, new RoleResponse())).collect(Collectors.toSet()));
        return userResponse;
    }
}
