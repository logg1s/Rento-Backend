package vn.io.rento.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class UserUpdateRequest {
    @NotNull
    private String username;

    private String password;

    @Email
    private String email;

    @Pattern(regexp = "\\d{10}")
    private String phoneNumber;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private Set<String> roles;

    private Boolean enabled;
}
