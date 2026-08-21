package se.lexicon.eventmanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParticipantTest {

    @Test
    void createsAndTrimsIndividualParticipant() {
        Participant participant = new IndividualParticipant(
                7L, "  Ada Lovelace  ", "  ada@example.com  "
        );

        assertAll(
                () -> assertEquals(7L, participant.getId()),
                () -> assertEquals("ada@example.com", participant.getEmail()),
                () -> assertEquals("Ada Lovelace", participant.displayName()),
                () -> assertTrue(participant.isPersisted())
        );
    }

    @Test
    void createsOrganizationParticipantPolymorphically() {
        Participant participant = new OrganizationParticipant(
                "Lexicon", "Felix Andersson", "felix@example.com"
        );

        assertAll(
                () -> assertEquals("Felix Andersson (Lexicon)", participant.displayName()),
                () -> assertFalse(participant.isPersisted()),
                () -> assertNull(participant.getId())
        );
    }

    @Test
    void rejectsInvalidParticipantData() {
        assertAll(
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new IndividualParticipant(" ", "person@example.com")
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new IndividualParticipant("Name", " ")
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new OrganizationParticipant(" ", "Representative", "r@example.com")
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new OrganizationParticipant("Company", " ", "r@example.com")
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new IndividualParticipant(0L, "Name", "person@example.com")
                )
        );
    }

    @Test
    void persistedIdentityUsesIdAndConcreteType() {
        Participant first = new IndividualParticipant(4L, "First Name", "first@example.com");
        Participant sameEntity = new IndividualParticipant(4L, "Changed Name", "new@example.com");
        Participant otherType = new OrganizationParticipant(
                4L, "Company", "First Name", "first@example.com"
        );

        assertAll(
                () -> assertEquals(first, sameEntity),
                () -> assertEquals(first.hashCode(), sameEntity.hashCode()),
                () -> assertNotEquals(first, otherType),
                () -> assertNotEquals(
                        new IndividualParticipant("New", "new@example.com"),
                        new IndividualParticipant("New", "new@example.com")
                )
        );
    }
}
