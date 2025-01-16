package vn.io.rento.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
