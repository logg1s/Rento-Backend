package vn.io.rento.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LogoutRequest {
    @NotNull
    private String accessToken;
}
