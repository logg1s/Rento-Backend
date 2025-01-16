package vn.io.rento.auth.service;

import org.springframework.stereotype.Service;
import vn.io.rento.auth.dto.request.PermissionRequest;
import vn.io.rento.auth.dto.response.PermissionResponse;

import java.util.List;

@Service
public interface PermissionService {
    PermissionResponse create(PermissionRequest permissionRequest);

    List<PermissionResponse> getAllPermissions();

    void delete(String permissionId);
}
