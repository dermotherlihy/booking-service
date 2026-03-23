package com.dermotherlihy.bookingservice.api;

import com.dermotherlihy.bookingservice.api.entities.BookingResponse;
import com.dermotherlihy.bookingservice.api.entities.CreateBookingRequest;
import com.dermotherlihy.bookingservice.domain.Booking;
import com.dermotherlihy.bookingservice.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = {BookingServiceAPI.PATH}, produces = MediaType.APPLICATION_JSON_VALUE)
public class BookingServiceAPI {

    public static final String PATH = "/v1/bookings";

    private final BookingService bookingService;

    public BookingServiceAPI(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    private BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getId().toString(),
                booking.getUserId(),
                booking.getClassId(),
                booking.getCreatedAt(),
                booking.getStartsAt()
        );
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> listBookings() {
        List<BookingResponse> responses = bookingService.listBookings()
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody CreateBookingRequest request) {
        Booking created = bookingService.createBooking(
                request.userId(),
                request.classId(),
                request.startsAt()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable UUID bookingId) {
        Optional<Booking> booking = bookingService.getBooking(bookingId);
        if (booking.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponse(booking.get()));
    }

    @PutMapping(value = "/{bookingId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingResponse> updateBooking(
            @PathVariable UUID bookingId,
            @Valid @RequestBody CreateBookingRequest request
    ) {
        Optional<Booking> updated = bookingService.updateBooking(
                bookingId,
                request.userId(),
                request.classId(),
                request.startsAt()
        );
        if (updated.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponse(updated.get()));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable UUID bookingId) {
        boolean deleted = bookingService.deleteBooking(bookingId);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
