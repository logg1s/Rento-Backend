package vn.io.rento.auth.dto.response;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class PermissionResponse {
    @Id
    private String name;
    private String description;
}
