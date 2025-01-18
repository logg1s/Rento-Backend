package vn.io.rento.auth.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import vn.io.rento.auth.dto.response.ErrorResponse;
import vn.io.rento.auth.enums.EError;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, Object> errMap = new HashMap<>();
        List<FieldError> errorList = ex.getFieldErrors();
        errorList.forEach(error -> errMap.put(error.getField(), error.getDefaultMessage()));
        ErrorResponse<Object> errorResponse = ErrorResponse.builder()
                .message(EError.VALIDATION_ERROR.getMessage())
                .path(request.getDescription(false))
                .time(LocalDateTime.now().toString())
                .errors(errMap)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse<Object>> handle(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        ErrorResponse<Object> errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .path(request.getDescription(false))
                .time(LocalDateTime.now().toString())
                .build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
    }

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorResponse<Object>> handle(CustomException e, WebRequest request) {
        ErrorResponse<Object> errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .path(request.getDescription(false))
                .time(LocalDateTime.now().toString())
                .build();
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }
}
