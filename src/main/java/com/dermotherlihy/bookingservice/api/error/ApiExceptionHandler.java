package com.dermotherlihy.bookingservice.api.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String message = "Request validation failed";
        if (ex.getBindingResult() != null && ex.getBindingResult().getFieldErrorCount() > 0) {
            String field = ex.getBindingResult().getFieldError().getField();
            String reason = ex.getBindingResult().getFieldError().getDefaultMessage();
            message = field + ": " + reason;
        }

        return ResponseEntity
                .badRequest()
                .body(new ApiError(
                        400,
                        "Validation Failed",
                        message,
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(
                        400,
                        "Bad Request",
                        "Invalid request parameter",
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(
            Exception ex,
            HttpServletRequest request
    ) {
        String message = ex.getMessage();
        if (message == null || message.isBlank()) {
            message = ex.getClass().getName();
        }

        return ResponseEntity
                .internalServerError()
                .body(new ApiError(
                        500,
                        ex.getClass().getSimpleName(),
                        message,
                        request.getRequestURI()
                ));
    }
}

