package se.lexicon.eventmanagement.domain;

/**
 * A participant attending as an individual.
 */
public final class IndividualParticipant extends Participant {

    private final String fullName;

    public IndividualParticipant(String fullName, String email) {
        this(null, fullName, email);
    }

    public IndividualParticipant(Long id, String fullName, String email) {
        super(id, email);
        this.fullName = DomainValidation.requiredText(fullName, "fullName");
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String displayName() {
        return fullName;
    }
}
