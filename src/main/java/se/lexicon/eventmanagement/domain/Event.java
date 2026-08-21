package se.lexicon.eventmanagement.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Describes a scheduled event and its fixed participant capacity.
 *
 * <p>Invitation state is deliberately not stored in this class. The service
 * layer will query invitations when it needs attendance or capacity data.</p>
 */
public final class Event {

    private final Long id;
    private final String title;
    private final String description;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String location;
    private final int capacity;

    public Event(String title, String description, LocalDateTime startTime,
                 LocalDateTime endTime, String location, int capacity) {
        this(null, title, description, startTime, endTime, location, capacity);
    }

    public Event(Long id, String title, String description, LocalDateTime startTime,
                 LocalDateTime endTime, String location, int capacity) {
        DomainValidation.validTimeRange(startTime, endTime);
        this.id = DomainValidation.optionalPositiveId(id);
        this.title = DomainValidation.requiredText(title, "title");
        this.description = DomainValidation.requiredText(description, "description");
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = DomainValidation.requiredText(location, "location");
        this.capacity = DomainValidation.positive(capacity, "capacity");
    }

    /**
     * Returns the database ID, or {@code null} before the event is saved.
     */
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isPersisted() {
        return id != null;
    }

    /**
     * Treats an event starting exactly at the reference time as upcoming.
     */
    public boolean isUpcomingAt(LocalDateTime referenceTime) {
        DomainValidation.required(referenceTime, "referenceTime");
        return !startTime.isBefore(referenceTime);
    }

    /**
     * Calculates remaining places from an accepted-invitation count supplied by
     * the service layer.
     */
    public int availableSpots(int acceptedParticipants) {
        DomainValidation.nonNegative(acceptedParticipants, "acceptedParticipants");
        if (acceptedParticipants > capacity) {
            throw new IllegalArgumentException(
                    "acceptedParticipants must not exceed event capacity"
            );
        }
        return capacity - acceptedParticipants;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Event that)) {
            return false;
        }
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Event.class, id);
    }
}
