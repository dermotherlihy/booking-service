package com.dermotherlihy.bookingservice.api;

import com.dermotherlihy.bookingservice.api.entities.BookingResponse;
import com.dermotherlihy.bookingservice.domain.Booking;
import com.dermotherlihy.bookingservice.service.BookingService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = {BookingServiceAPI.PATH}, consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class BookingServiceAPI {

    public static final String PATH = "/v1/bookings";

    private final BookingService bookingService;

    public BookingServiceAPI(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable UUID bookingId) {
        Optional<Booking> booking = bookingService.getBooking(bookingId);
        if (booking.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Booking found = booking.get();
        BookingResponse response = new BookingResponse(
                found.getId().toString(),
                found.getUserId(),
                found.getClassId(),
                found.getCreatedAt().toString(),
                found.getStartsAt().toString()
        );
        return ResponseEntity.ok(response);
    }



}
