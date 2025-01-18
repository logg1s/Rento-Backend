package vn.io.rento.auth.service.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.io.rento.auth.dto.request.AccountRequest;
import vn.io.rento.auth.dto.request.IntrospectRequest;
import vn.io.rento.auth.dto.request.LogoutRequest;
import vn.io.rento.auth.dto.request.TokenRequest;
import vn.io.rento.auth.dto.response.IntrospectResponse;
import vn.io.rento.auth.dto.response.TokenResponse;
import vn.io.rento.auth.entity.Permission;
import vn.io.rento.auth.entity.Role;
import vn.io.rento.auth.entity.User;
import vn.io.rento.auth.entity.ValidToken;
import vn.io.rento.auth.enums.EError;
import vn.io.rento.auth.exception.CustomException;
import vn.io.rento.auth.repository.UserRepository;
import vn.io.rento.auth.repository.ValidTokenRepository;
import vn.io.rento.auth.service.AuthService;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ValidTokenRepository validTokenRepository;

    @NonFinal
    @Value("${jwt.key}")
    private String secretKey;

    @NonFinal
    @Value("${jwt.atTime}")
    private int atTime;

    @NonFinal
    @Value("${jwt.rtTime}")
    private int rtTime;

    public TokenResponse getToken(AccountRequest request) {
        User user = userRepository
                .findById(request.getUsername())
                .orElseThrow(() -> new CustomException(EError.ACCOUNT_WRONG_CREDENTIALS));
        boolean isAuth = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isAuth) {
            throw new CustomException(EError.ACCOUNT_WRONG_CREDENTIALS);
        }
        WrappedTokens wrappedTokens = null;
        try {
            wrappedTokens = saveValidToken(user, null);
        } catch (ParseException e) {
            throw new CustomException(e.getMessage());
        }
        return new TokenResponse(
                wrappedTokens.accessToken.serialize(), wrappedTokens.refreshToken.serialize());
    }

    private WrappedTokens saveValidToken(User user, String prevToken) throws ParseException {
        SignedJWT accessToken = createToken(user, atTime, ChronoUnit.MINUTES);
        SignedJWT refreshToken = createToken(user, rtTime, ChronoUnit.DAYS);
        ValidToken validToken = ValidToken.builder()
                .accessTokenId(accessToken.getJWTClaimsSet().getJWTID())
                .refreshTokenId(refreshToken.getJWTClaimsSet().getJWTID())
                .prevTokenId(prevToken)
                .refreshTime(
                        new Date(Instant.now().plus(rtTime, ChronoUnit.DAYS).toEpochMilli()))
                .build();
        validTokenRepository.save(validToken);
        return new WrappedTokens(accessToken, refreshToken);
    }

    public IntrospectResponse validateToken(IntrospectRequest request) {
        SignedJWT accessToken = decodeToken(request.getAccessToken());
        ValidToken validToken = null;
        try {
            validToken = validTokenRepository
                    .findById(accessToken.getJWTClaimsSet().getJWTID())
                    .orElse(null);
        } catch (ParseException e) {
            throw new CustomException(e.getMessage());
        }

        return new IntrospectResponse(validToken != null);
    }

    public TokenResponse refreshToken(TokenRequest request) {
        SignedJWT signRefreshToken = decodeToken(request.getRefreshToken());
        ValidToken currentValidToken = null;
        try {
            currentValidToken = validTokenRepository
                    .findByRefreshTokenId(signRefreshToken.getJWTClaimsSet().getJWTID())
                    .orElse(null);
        } catch (ParseException e) {
            throw new CustomException(e.getMessage());
        }
        if (currentValidToken == null) {
            SignedJWT signAccessToken = decodeToken(request.getAccessToken());
            try {
                currentValidToken = validTokenRepository
                        .findByPrevTokenId(signAccessToken.getJWTClaimsSet().getJWTID())
                        .orElseThrow(() -> new CustomException(EError.TOKEN_MISMATCH));
            } catch (ParseException e) {
                throw new CustomException(e.getMessage());
            }
            validTokenRepository.delete(currentValidToken);
            throw new CustomException(EError.TOKEN_INVALID);
        }
        User user = null;
        try {
            user = userRepository
                    .findById(signRefreshToken.getJWTClaimsSet().getSubject())
                    .orElseThrow(() -> new CustomException(EError.USER_NOT_FOUND));
        } catch (ParseException e) {
            throw new CustomException(e.getMessage());
        }
        WrappedTokens wrappedTokens = null;
        try {
            wrappedTokens = saveValidToken(user, currentValidToken.getAccessTokenId());
        } catch (ParseException e) {
            throw new CustomException(e.getMessage());
        }
        validTokenRepository.delete(currentValidToken);
        return new TokenResponse(wrappedTokens.accessToken.serialize(), wrappedTokens.refreshToken.serialize());
    }

    public void logout(LogoutRequest request) {
        SignedJWT accessToken = decodeToken(request.getAccessToken());
        ValidToken validToken = null;
        try {
            validToken = validTokenRepository
                    .findById(accessToken.getJWTClaimsSet().getJWTID())
                    .orElseThrow(() -> new CustomException(EError.TOKEN_INVALID));
        } catch (ParseException e) {
            throw new CustomException(e.getMessage());
        }
        if (validToken != null) {
            validTokenRepository.delete(validToken);
        }
    }

    public SignedJWT decodeToken(String token) {
        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(token);
        } catch (ParseException e) {
            throw new CustomException(e.getMessage());
        }
        JWSVerifier jwsVerifier;
        try {
            jwsVerifier = new MACVerifier(secretKey);
        } catch (JOSEException e) {
            throw new CustomException(e.getMessage());
        }

        try {
            if (!signedJWT.verify(jwsVerifier)) {
                throw new CustomException(EError.TOKEN_INVALID);
            }
        } catch (JOSEException e) {
            throw new CustomException(e.getMessage());
        }

        JWTClaimsSet jwtClaimsSet = null;
        try {
            jwtClaimsSet = signedJWT.getJWTClaimsSet();
        } catch (ParseException e) {
            throw new CustomException(e.getMessage());
        }
        Date expireTime = jwtClaimsSet.getExpirationTime();

        if (expireTime == null || expireTime.before(new Date())) {
            throw new CustomException(EError.TOKEN_EXPIRED);
        }
        return signedJWT;
    }

    private SignedJWT createToken(User user, long amountToAdd, TemporalUnit temporalUnit) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .jwtID(UUID.randomUUID().toString())
                .issuer("long")
                .issueTime(new Date())
                .expirationTime(
                        new Date(Instant.now().plus(amountToAdd, temporalUnit).toEpochMilli()))
                .claim("scope", buildRoles(user))
                .build();

        SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);
        try {
            signedJWT.sign(new MACSigner(secretKey));
            return signedJWT;
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new CustomException("Cannot create token");
        }
    }

    private String buildRoles(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!user.getRoles().isEmpty()) {
            Set<String> set = new HashSet<>();
            for (Role role : user.getRoles()) {
                set.add("ROLE_" + role.getName());
                for (Permission permission : role.getPermissions()) {
                    set.add(permission.getName());
                }
            }
            for (String str : set) {
                stringJoiner.add(str);
            }
        }
        return stringJoiner.toString();
    }

    private record WrappedTokens(SignedJWT accessToken, SignedJWT refreshToken) {
    }
}
