package com.dermotherlihy.bookingservice.api.entities;

import java.time.Instant;
import java.util.UUID;

public class BookingResponse {


    private String id;
    private String userId;
    private String classId;
    private String createdAt;
    private String startsAt;


    public BookingResponse(String id, String userId, String classId, String createdAt, String startsAt) {
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

    public String getCreatedAt() {
        return createdAt;
    }

    public String getStartsAt() {
        return startsAt;
    }
}
