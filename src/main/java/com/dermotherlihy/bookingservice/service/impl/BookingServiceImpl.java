package com.dermotherlihy.bookingservice.service.impl;

import com.dermotherlihy.bookingservice.domain.Booking;
import com.dermotherlihy.bookingservice.repository.BookingRepository;
import com.dermotherlihy.bookingservice.service.BookingService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
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

    @Override
    public List<Booking> listBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking createBooking(String userId, String classId, Instant startsAt) {
        return bookingRepository.save(userId, classId, startsAt);
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
