package vn.io.rento.auth.mapper;

import vn.io.rento.auth.dto.request.UserRequest;
import vn.io.rento.auth.dto.response.UserResponse;
import vn.io.rento.auth.entity.User;

public class UserMapper {
    private UserMapper() {
    }

    public static User toUser(UserRequest userRequest, User user) {
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setDateOfBirth(userRequest.getDateOfBirth());
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
        return userResponse;
    }
}
