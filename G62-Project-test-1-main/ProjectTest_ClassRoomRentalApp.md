![lexicon-logo.svg](images/lexicon-logo.svg)

# Project Test (Classroom Rental App)

![ClassRoomRental.jpg](images/ClassRoomRental.jpg)

## Scenario


A facility management company owns 20 training classrooms of different
sizes. These rooms can be rented by external customers, including both
companies and individual clients. Every classroom has a name, a seating
capacity, certain equipment (such as projectors or whiteboards), and may
or may not be accessible for people with disabilities.

When a company booking user wants to reserve a room, they select the
date, time range, required capacity, and equipment. The system must
check that the selected room meets these requirements and is not already
booked during that time. If everything is valid, the booking is saved
and linked to the customer and the booking user who created the
reservation.

A booking may also include optional comments, such as allergy
information or other special requirements that the facility should be
aware of.

The system should provide a way to view all upcoming bookings---either
by room or by customer---to help the facility manage its schedule
effectively.

------------------------------------------------------------------------

## Requirements

### Functional Requirements

The system must allow users to:

1.  Register customers (companies or individuals).
2.  Manage the 20 classrooms (name, capacity, equipment, accessibility).
3.  Search for available rooms based on date, time, capacity, equipment,
    or accessibility.
4.  Create new bookings for a customer.
5.  Add optional comments to a booking (e.g., allergies or special
    requirements).
6.  Validate bookings so a room cannot be double-booked.
7.  View bookings for a specific room.
8.  View bookings for a specific customer.
9.  View all upcoming bookings in the system.
10. Store and load data using SQL + JDBC.
11. Interact with the application through a console menu.

------------------------------------------------------------------------

### Domain Modeling

-   Create a Class Diagram that reflects the scenario.
-   Use proper naming conventions for classes, methods, and variables.
-   Keep classes focused and meaningful.
-   Add comments explaining the purpose of classes, methods, and
    important logic.

------------------------------------------------------------------------

### Object-Oriented Principles

- Demonstrate OOP principles including encapsulation, abstraction, polymorphism, and correct modeling of relationships between objects.

------------------------------------------------------------------------

### Collections & Functional Programming

Use **Collections**, streams, and lambda expressions for tasks such as: 
- Searching for available rooms 
- Filtering bookings
- Sorting results

------------------------------------------------------------------------

### Database Integration (SQL + JDBC)

Use SQL and JDBC to: 
- Create and manage the database 
- Insert, update, delete, and retrieve data 
- Connect your Java application to the database

------------------------------------------------------------------------

### Code Quality

-   Write clean and readable code.
-   Use exceptions appropriately.
-   Avoid unused methods or unnecessary code.

------------------------------------------------------------------------

### Version Control (Git)

- Use a Git repository for this project.
- Make **frequent, small commits** instead of one big “final” commit.
- Write **clear commit messages** that explain *what* and *why*.
- Use at least **one separate branch**.
- Merge your feature branch back into the main branch when the feature is complete.
