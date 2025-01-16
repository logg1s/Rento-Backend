package vn.io.rento.auth.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class RoleResponse {
    private String name;

    private String description;

    private Set<PermissionResponse> permission;
}
