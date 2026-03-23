package com.dermotherlihy.bookingservice.api.entities;

import java.time.Instant;
import java.util.UUID;

public class BookingResponse {


    private String id;
    private String userId;
    private String classId;
    private Instant createdAt;
    private Instant startsAt;


    public BookingResponse(String id, String userId, String classId, Instant createdAt, Instant startsAt) {
        this.id = id;
        this.userId = userId;
        this.classId = classId;
        this.createdAt = createdAt;
        this.startsAt = startsAt;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getClassId() {
        return classId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getStartsAt() {
        return startsAt;
    }
}
