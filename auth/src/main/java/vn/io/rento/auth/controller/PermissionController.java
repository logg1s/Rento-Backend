package vn.io.rento.auth.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.io.rento.auth.dto.request.PermissionRequest;
import vn.io.rento.auth.dto.response.PermissionResponse;
import vn.io.rento.auth.service.PermissionService;

import java.util.List;

@RestController
@RequestMapping("/v1/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public ResponseEntity<List<PermissionResponse>> getAll() {
        return ResponseEntity.ok(permissionService.getAllPermissions());
    }

    @PostMapping
    public ResponseEntity<PermissionResponse> create(@Valid @RequestBody PermissionRequest request) {
        return ResponseEntity.ok(permissionService.create(request));
    }

    @DeleteMapping("{permissionName}")
    public ResponseEntity<String> delete(@PathVariable String permissionName) {
        permissionService.delete(permissionName);
        return ResponseEntity.ok("Deleted permission: " + permissionName);
    }
}
