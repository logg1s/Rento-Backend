package vn.io.rento.auth.service.impl;

import org.springframework.stereotype.Service;
import vn.io.rento.auth.dto.request.PermissionRequest;
import vn.io.rento.auth.dto.response.PermissionResponse;
import vn.io.rento.auth.entity.Permission;
import vn.io.rento.auth.mapper.PermissionMapper;
import vn.io.rento.auth.repository.PermissionRepository;
import vn.io.rento.auth.service.PermissionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public PermissionResponse create(PermissionRequest permissionRequest) {
        Permission permission = PermissionMapper.toPermission(permissionRequest, new Permission());
        return PermissionMapper.toPermissionResponse(
                permissionRepository.save(permission), new PermissionResponse()
        );
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll().stream().map(permission -> PermissionMapper.toPermissionResponse(permission, new PermissionResponse())).collect(Collectors.toList());
    }

    @Override
    public void delete(String permissionId) {
        permissionRepository.deleteById(permissionId);
    }
}
