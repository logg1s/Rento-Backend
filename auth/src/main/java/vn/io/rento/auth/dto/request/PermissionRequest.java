package vn.io.rento.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PermissionRequest {
    @NotNull
    private String name;

    @NotNull
    private String description;
}
