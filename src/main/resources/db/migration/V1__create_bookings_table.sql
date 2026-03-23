CREATE TABLE booking (
                         id CHAR(36) NOT NULL,
                         user_id VARCHAR(255) NOT NULL,
                         class_id VARCHAR(255) NOT NULL,
                         created_at TIMESTAMP(6) NOT NULL,
                         starts_at TIMESTAMP(6) NOT NULL,
                         PRIMARY KEY (id)
);

CREATE INDEX idx_booking_user_id ON booking(user_id);
CREATE INDEX idx_booking_class_id ON booking(class_id);