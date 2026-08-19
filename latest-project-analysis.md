# Project Recommendation and Implementation Plans

My recommendation: choose the **Event Management App** if the priority is finishing a solid, well-tested project with lower risk. Choose the **Classroom Rental App** if you want the more interesting technical challenge and a stronger live demonstration.

I reviewed the [project overview](C:/Users/Felix/IdeaProjects/Project-test-1/G62-Project-test-1-main/README.md), [Event Management specification](C:/Users/Felix/IdeaProjects/Project-test-1/G62-Project-test-1-main/ProjectTest_EventManagementApp.md), and [Classroom Rental specification](C:/Users/Felix/IdeaProjects/Project-test-1/G62-Project-test-1-main/ProjectTest_ClassRoomRentalApp.md).

## Comparison

| Area | Event Management | Classroom Rental |
|---|---|---|
| Overall difficulty | Easier | Moderately harder |
| Main workflow | Event → invitation → response | Search → availability → booking |
| Likely tables | 3–4 | 5–6 |
| Hardest rule | Accepted invitations cannot exceed capacity | Time-overlap and equipment matching |
| Streams/lambdas | Straightforward | More interesting and natural |
| Demo quality | Clear invitation workflow | Stronger search and scheduling demo |
| Risk of scope growing | Low–medium | Medium–high |
| My assessment | Safest choice | Most interesting choice |

The Classroom project is more interesting because searching by capacity, equipment, accessibility, and availability creates a proper scheduling system. However, the Event project contains everything needed to demonstrate Java, OOP, collections, streams, JDBC, validation, and Git with fewer interacting concepts.

## Obstacles that affect both projects

The console menu is not the difficult part. The important obstacles are:

- **Unclear business rules:** Some details are not specified, so they must be documented as project assumptions.
- **Keeping domain logic out of the menu:** Validation belongs in service/domain classes, not scattered through `Scanner` code.
- **Database consistency:** Java validation alone is insufficient. Important operations should use database transactions and constraints.
- **JDBC mapping:** Every database row must be converted cleanly into the correct Java object.
- **Meaningful OOP:** Inheritance and polymorphism should represent real differences, not be added artificially just to satisfy the checklist.
- **Date and time handling:** Use `LocalDateTime`, validate `start < end`, and define exactly what “upcoming” means.
- **Error handling:** Invalid input, missing IDs, unavailable rooms, capacity problems, and SQL failures need understandable messages.
- **Testing:** Business rules should be tested independently from console input.

I would use a layered structure for either project:

```text
domain/          Entities and enums
service/         Business rules and application operations
repository/      Repository interfaces
repository/jdbc/ JDBC implementations
ui/              Console menus and input handling
exception/       Domain-specific exceptions
config/          Database connection/configuration
```

A file-based H2 database would be the easiest development option if the instructor accepts it. Otherwise, use whichever database has been taught—likely MySQL or PostgreSQL—and keep credentials outside Git.

## Plan A: Event Management App

### Suggested model

- `Participant` as an abstract class or interface
- `IndividualParticipant`
- `OrganizationParticipant`
- `Event`
- `Invitation`
- `InvitationStatus`: `PENDING`, `ACCEPTED`, `DECLINED`
- `EventService`
- `ParticipantRepository`
- `EventRepository`
- `InvitationRepository`

A practical database could use:

- `participants`
- `events`
- `invitations`
- A unique constraint on `(event_id, participant_id)`

### Important decisions

Before coding, define these assumptions:

- One invitation represents one place at an event.
- An organization representative also consumes one place.
- Capacity is checked when an invitation becomes `ACCEPTED`, not when it is created.
- Changing `ACCEPTED` to `DECLINED` makes the place available again.
- “Upcoming” means events whose start time is later than the current time.
- Decide whether events with existing invitations may be deleted.

### Implementation phases

1. **Design and documentation**

   Draw the UML diagram, define relationships, document assumptions, and write the first README outline.

2. **Create the domain model**

   Implement participants, events, invitations, enums, constructors, validation, equality, and useful display methods.

3. **Implement in-memory business logic**

   Build the service layer before JDBC. Validate event time and capacity, prevent duplicate invitations, and enforce capacity when accepting.

4. **Add tests**

   Test invalid time ranges, zero/negative capacity, duplicate invitations, status changes, full events, and sorting/filtering.

5. **Design and create the database**

   Add `schema.sql`, constraints, foreign keys, and sample data. Then implement JDBC repositories with prepared statements.

6. **Connect service logic to JDBC**

   Acceptance should be transactional:

   - Count accepted invitations.
   - Verify that capacity remains.
   - Update the invitation.
   - Commit or roll back the operation.

7. **Build the console menu**

   Add participant registration, event creation, invitations, status updates, upcoming events, event attendance, and participant schedules.

8. **Finish and verify**

   Run all tests, manually test the happy path and common failures, clean the code, complete the README/UML, and prepare the presentation.

### Event-specific obstacles

- Modeling individuals and organizations without creating awkward inheritance.
- Ensuring duplicate invitations are prevented in both Java and SQL.
- Handling invitation status transitions correctly.
- Preventing overbooking if two acceptance operations happen close together.
- Clearly displaying nested information in the console.

This project is reasonably achievable in approximately **7–10 focused work sessions**.

## Plan B: Classroom Rental App

### Suggested model

- `Customer` as an abstract class or interface
- `IndividualCustomer`
- `CompanyCustomer`
- `BookingUser`
- `Classroom`
- `Equipment` enum or entity
- `Booking`
- `RoomSearchCriteria`
- `BookingService`
- `ClassroomRepository`
- `CustomerRepository`
- `BookingRepository`

Likely database tables:

- `customers`
- `booking_users`
- `classrooms`
- `equipment`
- `classroom_equipment`
- `bookings`

### Important decisions

The specification leaves one particularly important ambiguity: a **customer** and a **booking user** appear to be separate concepts. A sensible interpretation is:

- The customer is the company or individual paying for the booking.
- The booking user is the person who created it.
- A company may have multiple booking users.
- For an individual customer, the customer and booking user may represent the same person.

Other assumptions:

- Start time must be before end time.
- Bookings that touch at their boundaries are allowed.
- Requested capacity means minimum required seating.
- A room must contain every requested equipment item.
- Accessibility is only mandatory when requested.

### The critical overlap rule

Two bookings overlap when:

```text
existingStart < requestedEnd
AND
existingEnd > requestedStart
```

This correctly allows one booking to begin exactly when another ends.

### Implementation phases

1. **Design the data model**

   Resolve the customer/booking-user relationship, create the UML diagram, and document equipment and availability rules.

2. **Create the initial room catalogue**

   Seed exactly 20 classrooms with different capacities, equipment combinations, and accessibility values.

3. **Implement domain objects**

   Create customers, users, rooms, equipment, bookings, and search criteria with validation.

4. **Build availability searching in memory**

   Filter rooms by capacity, equipment, and accessibility. Remove rooms with conflicting bookings and sort the result.

5. **Test overlap logic thoroughly**

   Include:

   - Booking completely before or after another
   - Same start time
   - Partial overlap at either end
   - Booking completely inside another
   - Booking surrounding another
   - Adjacent bookings

6. **Create the database**

   Add foreign keys and the many-to-many room/equipment relationship. Implement JDBC repositories using prepared statements.

7. **Implement transactional booking creation**

   Recheck availability immediately before inserting the booking. This prevents a room being selected from an old search result and then booked after it becomes unavailable.

8. **Build the console workflow**

   A good booking flow would be:

   ```text
   Enter requirements
       → show matching rooms
       → select room
       → select customer and booking user
       → add optional comments
       → revalidate
       → save booking
   ```

9. **Add reporting views**

   Show upcoming bookings globally, by room, and by customer, sorted by date and time.

10. **Polish and verify**

   Complete tests, README, UML, Git history, error handling, seed instructions, and demo data.

### Classroom-specific obstacles

- Correctly detecting every form of time overlap.
- Modeling equipment as a many-to-many relationship.
- Searching for rooms that contain **all**, rather than merely one, requested equipment items.
- Distinguishing customers from booking users.
- Preventing a booking conflict between searching and saving.
- Keeping optional search criteria manageable without a large, brittle SQL query.

This project would probably require **9–13 focused work sessions**.

## What would be hardest for me to manage?

The hardest issue would be making decisions that the specification does not answer—especially the Classroom app’s customer/booking-user relationship. I can implement either interpretation, but only the instructor can confirm which interpretation is expected.

Technically, the most demanding part would be Classroom availability: time-overlap checks, equipment matching, and transactional booking creation must all agree. The Event app’s hardest operation is accepting an invitation without exceeding capacity, but that is a smaller and easier rule to isolate and test.

Database installation, credentials, and instructor-specific database expectations could also cause friction. Once those are known, neither project presents a serious implementation obstacle.

## Final recommendation

I would choose the **Event Management App** for the best balance of difficulty, completeness, and presentation readiness. It is not trivial—it still demonstrates state transitions, validation, inheritance, streams, database relations, and transactions—but it has less risk of becoming oversized.

If you are comfortable spending extra time on edge cases and want the project with the more impressive search feature, choose **Classroom Rental**.

Whichever one you select, create a **new implementation repository** as required rather than building directly inside this assignment-material folder. Start with the README, UML, assumptions, and database sketch before writing the console menu.
