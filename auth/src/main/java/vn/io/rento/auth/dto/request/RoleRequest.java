package vn.io.rento.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class RoleRequest {
    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Set<String> permission;
}
