package com.dermotherlihy.bookingservice.service.impl;

import com.dermotherlihy.bookingservice.domain.Booking;
import com.dermotherlihy.bookingservice.repository.BookingRepository;
import com.dermotherlihy.bookingservice.service.BookingService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final Counter bookingsCreatedCounter;

    public BookingServiceImpl(BookingRepository bookingRepository, MeterRegistry meterRegistry) {
        this.bookingRepository = bookingRepository;
        this.bookingsCreatedCounter = Counter.builder("bookings.created")
                .description("Total number of bookings created")
                .register(meterRegistry);
    }

    @Override
    public Optional<Booking> getBooking(UUID bookingId) {
        return bookingRepository.findById(bookingId);
    }

    @Override
    public List<Booking> listBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking createBooking(String userId, String classId, Instant startsAt) {
        Booking booking = bookingRepository.save(userId, classId, startsAt);
        bookingsCreatedCounter.increment();
        return booking;
    }

    @Override
    public Optional<Booking> updateBooking(UUID bookingId, String userId, String classId, Instant startsAt) {
        return bookingRepository.update(bookingId, userId, classId, startsAt);
    }

    @Override
    public boolean deleteBooking(UUID bookingId) {
        return bookingRepository.deleteById(bookingId);
    }
}
