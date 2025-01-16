package vn.io.rento.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IntrospectResponse {
    private boolean valid;
}
