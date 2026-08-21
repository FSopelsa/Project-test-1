package se.lexicon.eventmanagement.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InvitationTest {

    private static final LocalDateTime START = LocalDateTime.of(2026, 9, 10, 9, 0);

    @Test
    void newInvitationStartsPendingWithoutResponseTime() {
        Event event = event();
        Participant participant = participant();

        Invitation invitation = new Invitation(event, participant);

        assertAll(
                () -> assertNull(invitation.getId()),
                () -> assertSame(event, invitation.getEvent()),
                () -> assertSame(participant, invitation.getParticipant()),
                () -> assertEquals(InvitationStatus.PENDING, invitation.getStatus()),
                () -> assertTrue(invitation.getRespondedAt().isEmpty()),
                () -> assertFalse(invitation.isPersisted())
        );
    }

    @Test
    void reconstructsAcceptedInvitationFromPersistenceData() {
        LocalDateTime respondedAt = START.minusDays(1);

        Invitation invitation = new Invitation(
                9L,
                event(),
                participant(),
                InvitationStatus.ACCEPTED,
                respondedAt
        );

        assertAll(
                () -> assertEquals(9L, invitation.getId()),
                () -> assertEquals(InvitationStatus.ACCEPTED, invitation.getStatus()),
                () -> assertEquals(respondedAt, invitation.getRespondedAt().orElseThrow()),
                () -> assertTrue(invitation.isPersisted())
        );
    }

    @Test
    void rejectsMissingRelationshipsAndInvalidResponseState() {
        assertAll(
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Invitation(null, participant())
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Invitation(event(), null)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Invitation(1L, event(), participant(), null, null)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Invitation(1L, event(), participant(),
                                InvitationStatus.PENDING, START)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Invitation(1L, event(), participant(),
                                InvitationStatus.ACCEPTED, null)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Invitation(0L, event(), participant(),
                                InvitationStatus.DECLINED, START)
                )
        );
    }

    @Test
    void persistedIdentityUsesId() {
        Invitation first = new Invitation(
                3L, event(), participant(), InvitationStatus.PENDING, null
        );
        Invitation sameEntity = new Invitation(
                3L, event(), participant(), InvitationStatus.PENDING, null
        );

        assertAll(
                () -> assertEquals(first, sameEntity),
                () -> assertEquals(first.hashCode(), sameEntity.hashCode()),
                () -> assertNotEquals(
                        new Invitation(event(), participant()),
                        new Invitation(event(), participant())
                )
        );
    }

    private static Event event() {
        return new Event("Workshop", "Description", START, START.plusHours(2), "Room A", 20);
    }

    private static Participant participant() {
        return new IndividualParticipant("Ada Lovelace", "ada@example.com");
    }
}
