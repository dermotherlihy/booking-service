package com.dermotherlihy.bookingservice.repository.impl;

import com.dermotherlihy.bookingservice.domain.Booking;
import com.dermotherlihy.bookingservice.repository.BookingRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BookingRepositoryImpl  implements BookingRepository {


    private final JdbcTemplate jdbcTemplate;

    public BookingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Booking save(String userId, String classId, Instant startsAt) {
        UUID id = UUID.randomUUID();
        Instant createdAt = Instant.now();

        String sql = """
            INSERT INTO booking (id, user_id, class_id, created_at, starts_at)
            VALUES (?, ?, ?, ?, ?)
            """;

        jdbcTemplate.update(
                sql,
                id.toString(),
                userId,
                classId,
                Timestamp.from(createdAt),
                Timestamp.from(startsAt)
        );

        return new Booking(id, userId, classId, createdAt, startsAt);
    }

    public Optional<Booking> findById(UUID id) {
        // `LIMIT 1` keeps the query plan simple and makes the intent explicit.
        String sql = """
                SELECT id, user_id, class_id, created_at, starts_at
                FROM booking
                WHERE id = ?
                LIMIT 1
                """;

        try {
            Booking booking = jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> new Booking(
                            UUID.fromString(rs.getString("id")),
                            rs.getString("user_id"),
                            rs.getString("class_id"),
                            rs.getTimestamp("created_at").toInstant(),
                            rs.getTimestamp("starts_at").toInstant()
                    ),
                    id.toString()
            );
            return Optional.ofNullable(booking);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}
