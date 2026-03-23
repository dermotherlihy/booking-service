package com.dermotherlihy.bookingservice.service;

import com.dermotherlihy.bookingservice.domain.Booking;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


public interface BookingService {

    public Optional<Booking> getBooking(UUID bookingId);

}
