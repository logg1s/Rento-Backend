package vn.io.rento.auth.controller;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.io.rento.auth.dto.request.RoleRequest;
import vn.io.rento.auth.dto.response.RoleResponse;
import vn.io.rento.auth.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/v1/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleResponse> create(@Valid @RequestBody RoleRequest roleRequest) {
        return ResponseEntity.ok(roleService.create(roleRequest));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAll() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @DeleteMapping("{roleName}")
    public ResponseEntity<String> delete(@PathVariable String roleName) {
        roleService.deleteRole(roleName);
        return ResponseEntity.ok("Deleted role " + roleName);
    }

}
