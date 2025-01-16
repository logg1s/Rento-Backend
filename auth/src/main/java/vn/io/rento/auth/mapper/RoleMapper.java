package vn.io.rento.auth.mapper;

import vn.io.rento.auth.dto.request.RoleRequest;
import vn.io.rento.auth.dto.response.RoleResponse;
import vn.io.rento.auth.entity.Role;

public class RoleMapper {
    private RoleMapper() {
    }

    public static Role toRole(RoleRequest roleRequest, Role role) {
        role.setName(roleRequest.getName());
        role.setDescription(roleRequest.getDescription());
        return role;
    }

    public static RoleResponse toRoleResponse(Role role, RoleResponse roleResponse) {
        roleResponse.setName(role.getName());
        roleResponse.setDescription(roleResponse.getDescription());
        return roleResponse;
    }
}
