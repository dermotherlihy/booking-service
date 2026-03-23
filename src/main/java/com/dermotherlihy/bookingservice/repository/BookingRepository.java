package com.dermotherlihy.bookingservice.repository;

import com.dermotherlihy.bookingservice.domain.Booking;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository {

    public Optional<Booking> findById(UUID id);
    public Booking save(String userId, String classId, Instant startsAt);
}
