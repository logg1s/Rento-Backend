package vn.io.rento.auth.mapper;

import vn.io.rento.auth.dto.request.PermissionRequest;
import vn.io.rento.auth.dto.response.PermissionResponse;
import vn.io.rento.auth.entity.Permission;

public class PermissionMapper {
    private PermissionMapper() {
    }

    public static Permission toPermission(PermissionRequest permissionRequest, Permission permission) {
        permission.setName(permissionRequest.getName() == null ? permission.getName() : permissionRequest.getName());
        permission.setDescription(permissionRequest.getDescription() == null ? permission.getDescription() : permissionRequest.getDescription());
        return permission;
    }

    public static PermissionResponse toPermissionResponse(Permission permission, PermissionResponse permissionResponse) {
        permissionResponse.setName(permission.getName());
        permissionResponse.setDescription(permission.getDescription());
        return permissionResponse;
    }
}
