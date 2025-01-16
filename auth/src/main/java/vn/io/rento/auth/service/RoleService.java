package vn.io.rento.auth.service;

import org.springframework.stereotype.Service;
import vn.io.rento.auth.dto.request.RoleRequest;
import vn.io.rento.auth.dto.response.RoleResponse;

import java.util.List;

@Service
public interface RoleService {
    RoleResponse create(RoleRequest roleRequest);

    List<RoleResponse> getAllRoles();

    void deleteRole(String roleName);
}
