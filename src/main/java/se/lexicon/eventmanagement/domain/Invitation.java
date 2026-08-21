package se.lexicon.eventmanagement.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Connects one participant to one event and records the participant's response.
 *
 * <p>Duplicate prevention and status transitions are service-layer rules and
 * will be added in a later stage.</p>
 */
public final class Invitation {

    private final Long id;
    private final Event event;
    private final Participant participant;
    private final InvitationStatus status;
    private final LocalDateTime respondedAt;

    public Invitation(Event event, Participant participant) {
        this(null, event, participant, InvitationStatus.PENDING, null);
    }

    public Invitation(Long id, Event event, Participant participant,
                      InvitationStatus status, LocalDateTime respondedAt) {
        this.id = DomainValidation.optionalPositiveId(id);
        this.event = DomainValidation.required(event, "event");
        this.participant = DomainValidation.required(participant, "participant");
        this.status = DomainValidation.required(status, "status");
        validateResponseState(status, respondedAt);
        this.respondedAt = respondedAt;
    }

    private static void validateResponseState(InvitationStatus status,
                                              LocalDateTime respondedAt) {
        if (status == InvitationStatus.PENDING && respondedAt != null) {
            throw new IllegalArgumentException(
                    "a pending invitation must not have a response time"
            );
        }
        if (status != InvitationStatus.PENDING && respondedAt == null) {
            throw new IllegalArgumentException(
                    "an accepted or declined invitation requires a response time"
            );
        }
    }

    /**
     * Returns the database ID, or {@code null} before the invitation is saved.
     */
    public Long getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }

    public Participant getParticipant() {
        return participant;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public Optional<LocalDateTime> getRespondedAt() {
        return Optional.ofNullable(respondedAt);
    }

    public boolean isPersisted() {
        return id != null;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Invitation that)) {
            return false;
        }
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Invitation.class, id);
    }
}
