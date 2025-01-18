package vn.io.rento.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import vn.io.rento.auth.entity.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    @NotNull
    private String username;

    @Email
    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private boolean enabled;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private LocalDate dateOfBirth;
    private Set<RoleResponse> roles;
}
