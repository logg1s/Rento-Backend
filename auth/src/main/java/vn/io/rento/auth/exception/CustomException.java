package vn.io.rento.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import vn.io.rento.auth.enums.EError;

@Getter
public class CustomException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;

    public CustomException(EError error) {
        super(error.getMessage());
        this.message = error.getMessage();
        this.httpStatus = error.getHttpStatus();
    }

    public CustomException(String message) {
        super(message);
        this.message = message;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
