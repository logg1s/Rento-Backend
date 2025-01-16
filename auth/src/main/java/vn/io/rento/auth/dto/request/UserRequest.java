package vn.io.rento.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequest {
    @NotNull
    private String username;

    @NotNull
    private String password;

    @Email
    private String email;

    @Pattern(regexp = "\\d{10}")
    private String phoneNumber;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private LocalDate dateOfBirth;
}
