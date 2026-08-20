# Project-test-1

## UML Class Diagram — Event Management App

```mermaid
classDiagram
    class Event {
        +Long id
        +String title
        +String description
        +LocalDateTime startTime
        +LocalDateTime endTime
        +String location
        +int capacity
        +isUpcoming() boolean
        +availableSpots() int
    }

    class Participant {
        <<abstract>>
        +Long id
        +String email
        +displayName() String
    }

    class IndividualParticipant {
        +String fullName
    }

    class OrganizationParticipant {
        +String organizationName
        +String representativeName
    }

    class Invitation {
        +Long id
        +InvitationStatus status
        +LocalDateTime respondedAt
        +accept() void
        +decline() void
    }

    class InvitationStatus {
        <<enumeration>>
        PENDING
        ACCEPTED
        DECLINED
    }

    Participant <|-- IndividualParticipant
    Participant <|-- OrganizationParticipant
    Event "1" --> "0..*" Invitation : has
    Participant "1" --> "0..*" Invitation : receives
    Invitation --> InvitationStatus
```

Design rules: an invitation links exactly one participant to one event; the
combination of event and participant must be unique. Only accepted invitations
count toward an event's capacity.

## UML Class Diagram — Classroom Rental App

```mermaid
classDiagram
    class Customer {
        <<abstract>>
        +Long id
        +String email
        +displayName() String
    }

    class IndividualCustomer {
        +String fullName
    }

    class CompanyCustomer {
        +String companyName
    }

    class BookingUser {
        +Long id
        +String fullName
        +String email
    }

    class Classroom {
        +Long id
        +String name
        +int seatingCapacity
        +boolean accessible
        +meetsCapacity(requiredCapacity) boolean
        +hasEquipment(requiredEquipment) boolean
    }

    class Equipment {
        <<enumeration>>
        PROJECTOR
        WHITEBOARD
        VIDEO_CONFERENCING
        SPEAKERS
    }

    class Booking {
        +Long id
        +LocalDateTime startTime
        +LocalDateTime endTime
        +String comments
        +overlaps(startTime, endTime) boolean
    }

    Customer <|-- IndividualCustomer
    Customer <|-- CompanyCustomer
    Customer "1" --> "0..*" BookingUser : has
    Customer "1" --> "0..*" Booking : makes
    BookingUser "1" --> "0..*" Booking : creates
    Classroom "1" --> "0..*" Booking : is reserved for
    Classroom "0..*" -- "0..*" Equipment : contains
```

Design rules: a booking belongs to one customer, is created by one booking user,
and reserves one classroom. A classroom can only be booked when its capacity,
equipment, accessibility, and time availability meet the customer's request.
