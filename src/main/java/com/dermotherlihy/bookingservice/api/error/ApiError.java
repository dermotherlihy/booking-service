package com.dermotherlihy.bookingservice.api.error;

public record ApiError(
        int status,
        String error,
        String message,
        String path
) {
}

