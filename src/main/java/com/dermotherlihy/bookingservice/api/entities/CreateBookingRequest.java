package com.dermotherlihy.bookingservice.api.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CreateBookingRequest(
        @NotBlank String userId,
        @NotBlank String classId,
        @NotNull Instant startsAt
) {
}

