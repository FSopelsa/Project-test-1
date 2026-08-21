package se.lexicon.eventmanagement.domain;

import java.util.Objects;

/**
 * Base type for every person or organization representative that can be invited
 * to an event.
 */
public abstract class Participant {

    private final Long id;
    private final String email;

    protected Participant(Long id, String email) {
        this.id = DomainValidation.optionalPositiveId(id);
        this.email = DomainValidation.requiredText(email, "email");
    }

    /**
     * Returns the database ID, or {@code null} before the participant is saved.
     */
    public final Long getId() {
        return id;
    }

    public final String getEmail() {
        return email;
    }

    public final boolean isPersisted() {
        return id != null;
    }

    /**
     * Supplies a subtype-specific name without type checks in callers.
     */
    public abstract String displayName();

    @Override
    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Participant that = (Participant) other;
        return id != null && id.equals(that.id);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getClass(), id);
    }
}
