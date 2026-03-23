package com.dermotherlihy.bookingservice.api.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

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
        return ResponseEntity
                .internalServerError()
                .body(new ApiError(
                        500,
                        "Internal Server Error",
                        "An unexpected error occurred",
                        request.getRequestURI()
                ));
    }
}

