package vn.io.rento.auth.mapper;

import vn.io.rento.auth.dto.request.RoleRequest;
import vn.io.rento.auth.dto.response.PermissionResponse;
import vn.io.rento.auth.dto.response.RoleResponse;
import vn.io.rento.auth.entity.Permission;
import vn.io.rento.auth.entity.Role;

import java.util.stream.Collectors;

public class RoleMapper {
    private RoleMapper() {
    }

    public static Role toRole(RoleRequest roleRequest, Role role) {
        role.setName(roleRequest.getName() == null ? role.getName() : roleRequest.getName());
        role.setDescription(roleRequest.getDescription() == null ? role.getDescription() : roleRequest.getDescription());
        return role;
    }

    public static RoleResponse toRoleResponse(Role role, RoleResponse roleResponse) {
        roleResponse.setName(role.getName());
        roleResponse.setDescription(roleResponse.getDescription());
        roleResponse.setPermission(role.getPermissions().stream().map(s -> PermissionMapper.toPermissionResponse(s, new PermissionResponse())).collect(Collectors.toSet()));
        return roleResponse;
    }
}
