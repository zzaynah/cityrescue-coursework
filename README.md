# CityRescue — Emergency Dispatch Simulator

A deterministic, tick-based emergency dispatch simulator built in Java for a university Object-Oriented Programming module (ECM1410). Models a city's dispatch control room: a 2D grid map with obstacles, stations, emergency units, and incidents that move through a full lifecycle as simulated time advances.

Grade: 84%

## Features

- **Grid-based city map** with configurable size and obstacle placement.
- **Stations** with unit capacity limits, housing emergency units.
- **Three unit types** — Ambulance, Fire Engine, Police Car — modelled via an abstract `Unit` class with type-specific behaviour expressed through inheritance and method overriding (polymorphism), rather than conditional branching.
- **Incident lifecycle**: REPORTED → DISPATCHED → IN_PROGRESS → RESOLVED, with support for cancellation and severity escalation.
- **Deterministic dispatch logic**: units are assigned to incidents using a strict tie-break rule set (Manhattan distance, then unit ID, then home station ID), ensuring identical input always produces identical output — a requirement for automated correctness testing.
- **Rule-based movement**: units navigate around obstacles using a fixed directional preference (N, E, S, W) rather than pathfinding algorithms.
- **Design-by-contract validation**: every public method enforces documented preconditions and guarantees system state remains unchanged if an invalid call throws an exception.

## Tech Stack

- Java 17+
- Maven (build and test runner)
- Arrays only (no Java Collections), per coursework constraints

## Core Classes

- `CityRescueImpl` — main implementation of the public `CityRescue` interface (22 methods)
- `CityMap` — grid dimensions and obstacle/legality checks
- `Station` — location, capacity, and assigned units
- `Incident` — type, severity, location, and lifecycle status
- `Unit` (abstract) — shared unit behaviour; subclassed by `Ambulance`, `FireEngine`, `PoliceCar`

## Running Tests Locally

```bash
mvn test
```

Public tests are included for guidance; the full grading suite includes additional hidden tests.

## Notes

Built as a pair-programming assignment. Core design challenges involved enforcing strict determinism (fixed tie-break and movement rules) and using inheritance/polymorphism to differentiate unit behaviour cleanly, without resorting to large conditional blocks.
