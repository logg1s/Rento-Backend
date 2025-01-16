package vn.io.rento.auth.configuration;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import vn.io.rento.auth.enums.EError;
import vn.io.rento.auth.exception.CustomException;
import vn.io.rento.auth.repository.ValidTokenRepository;
import vn.io.rento.auth.service.AuthService;

import javax.crypto.spec.SecretKeySpec;

@Component
public class CustomJwtDecoder implements JwtDecoder {

    private final AuthService authService;

    private final ValidTokenRepository validTokenRepository;
    @Value("${jwt.key}")
    private String secretKey;

    public CustomJwtDecoder(
            @Lazy AuthService authService, @Lazy ValidTokenRepository validTokenRepository) {
        this.authService = authService;
        this.validTokenRepository = validTokenRepository;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            SignedJWT signedJWT = authService.decodeToken(token);
            String accessTokenId = signedJWT.getJWTClaimsSet().getJWTID();
            validTokenRepository
                    .findById(accessTokenId)
                    .orElseThrow(() -> new CustomException(EError.TOKEN_INVALID));
        } catch (Exception e) {
            throw new CustomException(EError.UNAUTHORIZED);
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), MacAlgorithm.HS512.toString());
        return NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build()
                .decode(token);
    }
}

