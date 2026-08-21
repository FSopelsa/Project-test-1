package se.lexicon.eventmanagement.domain;

import java.time.LocalDateTime;

/**
 * Centralizes validation shared by the domain model.
 */
final class DomainValidation {

    private DomainValidation() {
    }

    static Long optionalPositiveId(Long id) {
        if (id != null && id <= 0) {
            throw new IllegalArgumentException("id must be positive when present");
        }
        return id;
    }

    static String requiredText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value.trim();
    }

    static int positive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be positive");
        }
        return value;
    }

    static int nonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " must not be negative");
        }
        return value;
    }

    static <T> T required(T value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " must not be null");
        }
        return value;
    }

    static void validTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        required(startTime, "startTime");
        required(endTime, "endTime");
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("startTime must be before endTime");
        }
    }
}
