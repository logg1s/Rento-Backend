package vn.io.rento.auth.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum EError {
    RESOURCE_NOT_FOUND("Resource not found !", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("User not found !", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTED("User already existed !", HttpStatus.CONFLICT),
    EMAIL_ALREADY_EXISTED("Email already existed !", HttpStatus.CONFLICT),
    SERVER_ERROR("Server error !", HttpStatus.INTERNAL_SERVER_ERROR),
    UNKNOWN_EXCEPTION("Unknown Error !", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED("Unauthorized Error !", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED("Unauthenticated Error !", HttpStatus.FORBIDDEN),
    VALIDATION_ERROR("Validation Error !", HttpStatus.BAD_REQUEST),
    ACCOUNT_WRONG_CREDENTIALS("Account wrong Credentials !", HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED("Token expired !", HttpStatus.UNAUTHORIZED),
    TOKEN_INVALID("Token Invalid !", HttpStatus.UNAUTHORIZED),
    TOKEN_MISMATCH("Token Mismatch !", HttpStatus.UNAUTHORIZED);


    private final String message;
    private final HttpStatus httpStatus;

    EError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
