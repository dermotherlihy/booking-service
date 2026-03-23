package com.dermotherlihy.bookingservice.service;

import com.dermotherlihy.bookingservice.domain.Booking;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface BookingService {

    public Optional<Booking> getBooking(UUID bookingId);

    public List<Booking> listBookings();

    public Booking createBooking(String userId, String classId, Instant startsAt);

    public Optional<Booking> updateBooking(UUID bookingId, String userId, String classId, Instant startsAt);

    public boolean deleteBooking(UUID bookingId);

}
