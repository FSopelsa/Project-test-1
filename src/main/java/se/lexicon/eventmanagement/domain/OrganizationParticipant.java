package se.lexicon.eventmanagement.domain;

/**
 * A named representative attending on behalf of an organization.
 */
public final class OrganizationParticipant extends Participant {

    private final String organizationName;
    private final String representativeName;

    public OrganizationParticipant(String organizationName, String representativeName,
                                   String email) {
        this(null, organizationName, representativeName, email);
    }

    public OrganizationParticipant(Long id, String organizationName,
                                   String representativeName, String email) {
        super(id, email);
        this.organizationName = DomainValidation.requiredText(
                organizationName, "organizationName"
        );
        this.representativeName = DomainValidation.requiredText(
                representativeName, "representativeName"
        );
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    @Override
    public String displayName() {
        return representativeName + " (" + organizationName + ")";
    }
}
