package vn.io.rento.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TokenRequest {
    @NotNull
    private String accessToken;
    @NotNull
    private String refreshToken;
}
