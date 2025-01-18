package vn.io.rento.auth.service;

import org.springframework.stereotype.Service;
import vn.io.rento.auth.dto.request.UserCreateRequest;
import vn.io.rento.auth.dto.request.UserUpdateRequest;
import vn.io.rento.auth.dto.response.UserResponse;

import java.util.List;

@Service
public interface UserService {
    UserResponse createUser(UserCreateRequest userCreateRequest);

    UserResponse updateUser(UserUpdateRequest userUpdateRequest);

    List<UserResponse> getAllUsers();

    UserResponse getUser(String username);

    void deleteUser(String username);
}
