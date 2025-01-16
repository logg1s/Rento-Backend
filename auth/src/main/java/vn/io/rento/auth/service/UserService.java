package vn.io.rento.auth.service;

import org.springframework.stereotype.Service;
import vn.io.rento.auth.dto.request.UserRequest;
import vn.io.rento.auth.dto.response.UserResponse;

import java.util.List;

@Service
public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(UserRequest userRequest);

    List<UserResponse> getAllUsers();

    UserResponse getUser(String username);

    void deleteUser(String username);
}
