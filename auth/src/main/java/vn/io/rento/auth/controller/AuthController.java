package vn.io.rento.auth.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.io.rento.auth.dto.request.AccountRequest;
import vn.io.rento.auth.dto.request.IntrospectRequest;
import vn.io.rento.auth.dto.request.LogoutRequest;
import vn.io.rento.auth.dto.request.TokenRequest;
import vn.io.rento.auth.dto.response.IntrospectResponse;
import vn.io.rento.auth.dto.response.TokenResponse;
import vn.io.rento.auth.service.AuthService;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> getToken(@Valid @RequestBody AccountRequest accountRequest) {
        return ResponseEntity.ok(authService.getToken(accountRequest));
    }

    @PostMapping("/validate")
    public ResponseEntity<IntrospectResponse> validateToken(@Valid @RequestBody IntrospectRequest introspectRequest) {
        return ResponseEntity.ok(authService.validateToken(introspectRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(tokenRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);
        return ResponseEntity.ok("Logout successfully");
    }
}
