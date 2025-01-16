package vn.io.rento.auth.service.impl;

import org.springframework.stereotype.Service;
import vn.io.rento.auth.dto.request.RoleRequest;
import vn.io.rento.auth.dto.response.RoleResponse;
import vn.io.rento.auth.entity.Permission;
import vn.io.rento.auth.entity.Role;
import vn.io.rento.auth.mapper.RoleMapper;
import vn.io.rento.auth.repository.PermissionRepository;
import vn.io.rento.auth.repository.RoleRepository;
import vn.io.rento.auth.service.RoleService;

import java.util.HashSet;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public RoleResponse create(RoleRequest roleRequest) {
        List<Permission> permissions = permissionRepository.findAllById(roleRequest.getPermission());
        Role role = RoleMapper.toRole(roleRequest, new Role());
        role.setPermission(new HashSet<>(permissions));
        return RoleMapper.toRoleResponse(roleRepository.save(role), new RoleResponse());
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream().map(role -> RoleMapper.toRoleResponse(role, new RoleResponse())).toList();
    }

    @Override
    public void deleteRole(String roleName) {
        roleRepository.deleteById(roleName);
    }
}
