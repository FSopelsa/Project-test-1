package se.lexicon.eventmanagement.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventTest {

    private static final LocalDateTime START = LocalDateTime.of(2026, 9, 10, 9, 0);
    private static final LocalDateTime END = START.plusHours(2);

    @Test
    void createsAndTrimsValidEvent() {
        Event event = new Event(
                12L,
                "  Java Workshop  ",
                "  Streams and lambdas  ",
                START,
                END,
                "  Room A  ",
                20
        );

        assertAll(
                () -> assertEquals(12L, event.getId()),
                () -> assertEquals("Java Workshop", event.getTitle()),
                () -> assertEquals("Streams and lambdas", event.getDescription()),
                () -> assertEquals(START, event.getStartTime()),
                () -> assertEquals(END, event.getEndTime()),
                () -> assertEquals("Room A", event.getLocation()),
                () -> assertEquals(20, event.getCapacity()),
                () -> assertTrue(event.isPersisted())
        );
    }

    @Test
    void rejectsMissingTextAndInvalidCapacity() {
        assertAll(
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Event(" ", "Description", START, END, "Room", 10)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Event("Title", null, START, END, "Room", 10)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Event("Title", "Description", START, END, " ", 10)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Event("Title", "Description", START, END, "Room", 0)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Event(-1L, "Title", "Description", START, END, "Room", 10)
                )
        );
    }

    @Test
    void rejectsInvalidTimeRanges() {
        assertAll(
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Event("Title", "Description", null, END, "Room", 10)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Event("Title", "Description", START, null, "Room", 10)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Event("Title", "Description", START, START, "Room", 10)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new Event("Title", "Description", START, START.minusMinutes(1),
                                "Room", 10)
                )
        );
    }

    @Test
    void checksUpcomingStateAtAnExplicitReferenceTime() {
        Event event = eventWithCapacity(10);

        assertAll(
                () -> assertTrue(event.isUpcomingAt(START.minusSeconds(1))),
                () -> assertTrue(event.isUpcomingAt(START)),
                () -> assertFalse(event.isUpcomingAt(START.plusSeconds(1))),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> event.isUpcomingAt(null)
                )
        );
    }

    @Test
    void calculatesAvailableSpotsAndRejectsImpossibleCounts() {
        Event event = eventWithCapacity(10);

        assertAll(
                () -> assertEquals(10, event.availableSpots(0)),
                () -> assertEquals(3, event.availableSpots(7)),
                () -> assertEquals(0, event.availableSpots(10)),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> event.availableSpots(-1)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> event.availableSpots(11)
                )
        );
    }

    @Test
    void persistedIdentityUsesId() {
        Event first = new Event(2L, "First", "Description", START, END, "A", 10);
        Event sameEntity = new Event(2L, "Changed", "Updated", START, END, "B", 20);

        assertAll(
                () -> assertEquals(first, sameEntity),
                () -> assertEquals(first.hashCode(), sameEntity.hashCode()),
                () -> assertNotEquals(eventWithCapacity(10), eventWithCapacity(10))
        );
    }

    private static Event eventWithCapacity(int capacity) {
        return new Event("Workshop", "Description", START, END, "Room A", capacity);
    }
}
