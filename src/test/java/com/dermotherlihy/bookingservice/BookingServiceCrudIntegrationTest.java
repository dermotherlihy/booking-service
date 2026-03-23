package com.dermotherlihy.bookingservice;

import com.dermotherlihy.bookingservice.api.entities.BookingResponse;
import com.dermotherlihy.bookingservice.api.entities.CreateBookingRequest;
import com.dermotherlihy.bookingservice.api.error.ApiError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class BookingServiceCrudIntegrationTest {

    @Container
    static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("app_db")
            .withUsername("app_user")
            .withPassword("app_password");

    @DynamicPropertySource
    static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void clean() {
        jdbcTemplate.execute("DELETE FROM booking");
    }

    @Test
    void crudLifecycle_works() throws Exception {
        Instant startsAt1 = Instant.parse("2026-03-24T10:15:30Z");
        CreateBookingRequest request1 = new CreateBookingRequest("user-1", "class-1", startsAt1);

        ResponseEntity<BookingResponse> created = restTemplate.postForEntity(
                "/v1/bookings",
                request1,
                BookingResponse.class
        );
        assertEquals(HttpStatus.CREATED, created.getStatusCode());

        BookingResponse createdBody = created.getBody();
        assertNotNull(createdBody);

        UUID id = UUID.fromString(createdBody.getId());
        assertEquals("user-1", createdBody.getUserId());
        assertEquals("class-1", createdBody.getClassId());
        assertEquals(startsAt1, createdBody.getStartsAt());

        // get
        ResponseEntity<String> fetchedRaw = restTemplate.getForEntity(
                "/v1/bookings/{bookingId}",
                String.class,
                id
        );
        assertEquals(HttpStatus.OK, fetchedRaw.getStatusCode(), "Body: " + fetchedRaw.getBody());
        BookingResponse fetched = objectMapper.readValue(fetchedRaw.getBody(), BookingResponse.class);
        assertEquals(id.toString(), fetched.getId());

        // list
        BookingResponse[] list = restTemplate.getForEntity("/v1/bookings", BookingResponse[].class).getBody();
        assertNotNull(list);
        assertEquals(1, list.length);

        // update
        Instant startsAt2 = Instant.parse("2026-03-25T10:15:30Z");
        CreateBookingRequest request2 = new CreateBookingRequest("user-1", "class-2", startsAt2);
        HttpEntity<CreateBookingRequest> updateEntity = new HttpEntity<>(request2);

        ResponseEntity<BookingResponse> updated = restTemplate.exchange(
                "/v1/bookings/{bookingId}",
                HttpMethod.PUT,
                updateEntity,
                BookingResponse.class,
                id
        );
        assertEquals(HttpStatus.OK, updated.getStatusCode());
        assertEquals("class-2", updated.getBody().getClassId());
        assertEquals(startsAt2, updated.getBody().getStartsAt());

        // delete
        ResponseEntity<Void> deleted = restTemplate.exchange(
                "/v1/bookings/{bookingId}",
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class,
                id
        );
        assertEquals(HttpStatus.NO_CONTENT, deleted.getStatusCode());

        // get missing
        UUID missingId = UUID.randomUUID();
        assertNotFound("/v1/bookings/{bookingId}", missingId);
    }

    @Test
    void createBooking_returns400_onValidationErrors() throws Exception {
        Instant startsAt = Instant.parse("2026-03-24T10:15:30Z");

        // invalid userId (blank)
        CreateBookingRequest invalid1 = new CreateBookingRequest("", "class-1", startsAt);
        ResponseEntity<String> invalid1Resp = restTemplate.postForEntity(
                "/v1/bookings",
                invalid1,
                String.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, invalid1Resp.getStatusCode());

        ApiError err1 = objectMapper.readValue(invalid1Resp.getBody(), ApiError.class);
        assertEquals(400, err1.status());

        // invalid startsAt (null)
        CreateBookingRequest invalid2 = new CreateBookingRequest("user-1", "class-1", null);
        ResponseEntity<String> invalid2Resp = restTemplate.postForEntity(
                "/v1/bookings",
                invalid2,
                String.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, invalid2Resp.getStatusCode());

        ApiError err2 = objectMapper.readValue(invalid2Resp.getBody(), ApiError.class);
        assertEquals(400, err2.status());
    }

    private void assertNotFound(String urlTemplate, UUID id) {
        ResponseEntity<BookingResponse> response = restTemplate.getForEntity(
                urlTemplate,
                BookingResponse.class,
                id
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

