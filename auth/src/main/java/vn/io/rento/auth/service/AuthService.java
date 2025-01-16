package vn.io.rento.auth.service;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;
import vn.io.rento.auth.dto.request.AccountRequest;
import vn.io.rento.auth.dto.request.IntrospectRequest;
import vn.io.rento.auth.dto.request.LogoutRequest;
import vn.io.rento.auth.dto.request.TokenRequest;
import vn.io.rento.auth.dto.response.IntrospectResponse;
import vn.io.rento.auth.dto.response.TokenResponse;


@Service
public interface AuthService {
    TokenResponse getToken(AccountRequest accountRequest);

    IntrospectResponse validateToken(IntrospectRequest introspectRequest);

    void logout(LogoutRequest logoutRequest);

    SignedJWT decodeToken(String token);

    TokenResponse refreshToken(TokenRequest tokenRequest);
}
