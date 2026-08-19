![lexicon-logo.svg](images/lexicon-logo.svg)

# Project Test (Event Management App)

![EventManagementApp.png](images/EventManagementApp.png)

## Scenario

A community center organizes events such as workshops, meetups, and trainings.
Events can be created by staff members and participants can be invited to attend.

Each event has:
- a title and description
- date and time range
- a location (room name or address)
- a maximum number of participants (capacity)

Participants can be either individuals or representatives of a company/organization.

When creating an event, the organizer selects the time, location, and capacity.
The system must ensure the event is valid (time range is correct, capacity is positive)
and should allow inviting participants.

Invitations have a status:
- PENDING
- ACCEPTED
- DECLINED

The system should provide views for:
- upcoming events
- participants invited to an event
- events a participant is invited to
- attendance list (accepted participants)

The application is managed through a console menu and stores data in a database.

---

## Requirements

### Functional Requirements

The system must allow users to:

1. Register participants (individuals and/or organizations).
2. Create and manage events (title, time range, location, capacity).
3. Invite participants to an event.
4. Update invitation status (accept/decline).
5. Prevent duplicate invitations (same participant cannot be invited twice to the same event).
6. Prevent overbooking (accepted participants cannot exceed event capacity).
7. View all upcoming events.
8. View invitations/participants for a specific event.
9. View events for a specific participant.
10. Store and load data using SQL + JDBC.
11. Interact with the application through a console menu.

---

### Domain Modeling

- Create a UML Class Diagram that reflects the scenario.
- Use proper naming conventions for classes, methods, and variables.
- Keep classes focused and meaningful.
- Add comments explaining the purpose of classes, methods, and important logic.

---

### Object-Oriented Principles

- Demonstrate OOP principles including encapsulation, abstraction, polymorphism, and correct modeling of relationships between objects.

---

### Collections & Functional Programming

Use Collections, streams, and lambdas for tasks such as:
- Filtering upcoming events
- Searching invitations by status
- Sorting events by date/time
- Listing available spots for an event

---

### Database Integration (SQL + JDBC)

Use SQL and JDBC to:
- Create and manage the database
- Insert, update, delete, and retrieve data
- Connect your Java application to the database

Suggested tables:
- participants
- events
- invitations

---

### Code Quality

- Write clean and readable code.
- Use exceptions appropriately.
- Avoid unused methods or unnecessary code.

---

### Version Control (Git)

- Use a Git repository for this project.
- Make frequent, small commits.
- Write clear commit messages describing what and why.
- Use at least one separate branch and merge it back into main.
