package vn.io.rento.auth.service.impl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import vn.io.rento.auth.dto.request.RoleRequest;
import vn.io.rento.auth.dto.response.RoleResponse;
import vn.io.rento.auth.entity.Permission;
import vn.io.rento.auth.entity.Role;
import vn.io.rento.auth.enums.EError;
import vn.io.rento.auth.exception.CustomException;
import vn.io.rento.auth.mapper.RoleMapper;
import vn.io.rento.auth.repository.PermissionRepository;
import vn.io.rento.auth.repository.RoleRepository;
import vn.io.rento.auth.service.RoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    @PreAuthorize("hasRole(T(vn.io.rento.auth.enums.ERole).ADMIN.name())")
    public RoleResponse create(RoleRequest roleRequest) {
        Set<String> requestedPermissions = roleRequest.getPermissions();

        if (permissionRepository.existsAllByNameIn(requestedPermissions)) {
            Role role = RoleMapper.toRole(roleRequest, new Role());
            Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(requestedPermissions));
            role.setPermissions(permissions);
            return RoleMapper.toRoleResponse(roleRepository.save(role), new RoleResponse());
        }

        throw new CustomException(EError.PERMISSION_NOT_FOUND);
    }

    @Override
    @PreAuthorize("hasRole(T(vn.io.rento.auth.enums.ERole).ADMIN.name())")
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream().map(role -> RoleMapper.toRoleResponse(role, new RoleResponse())).toList();
    }

    @Override
    @PreAuthorize("hasRole(T(vn.io.rento.auth.enums.ERole).ADMIN.name())")
    public void deleteRole(String roleName) {
        roleRepository.deleteById(roleName);
    }
}
