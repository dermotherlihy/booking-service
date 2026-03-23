package com.dermotherlihy.bookingservice.service.impl;

import com.dermotherlihy.bookingservice.domain.Booking;
import com.dermotherlihy.bookingservice.repository.BookingRepository;
import com.dermotherlihy.bookingservice.service.BookingService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Optional<Booking> getBooking(UUID bookingId) {
        return bookingRepository.findById(bookingId);
    }
}
