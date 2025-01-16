package vn.io.rento.auth.dto.request;

import lombok.Data;

@Data
public class IntrospectRequest {
    private String accessToken;
}
