# ZooSeeker

Android navigation app for the San Diego Zoo. Users select which exhibits they want to visit and the app computes the most efficient walking route, then guides them turn-by-turn with live GPS tracking and automatic rerouting if they go off-path.

Built as a team project in CSE 110 (Software Engineering) at UC San Diego.

---

## Features

- **Exhibit search** — searchable, autocompleting list of all zoo exhibits; filter view to only selected exhibits
- **Optimal route planning** — nearest-neighbor routing over a weighted graph of zoo paths, starting and ending at the entrance gate
- **Turn-by-turn directions** — brief (per-street, consolidated) and detailed (per-segment) views; step forward, step back, or skip any exhibit mid-route
- **Live GPS tracking** — detects when the user strays off-route and prompts to reorder remaining exhibits via Dijkstra re-routing from current position
- **Route plan summary** — full itinerary with distances displayed before navigation starts
- **Manual location injection** — enter mock GPS coordinates to test routing behavior without physical movement

## Architecture

Three activities cover the full user flow:

| Activity | Role |
|---|---|
| `MainActivity` | Exhibit search, checkbox selection, planned-count counter |
| `RoutePlanSummaryActivity` | Full itinerary overview (A→B distance, B→C distance, …) before navigation starts |
| `DirectionActivity` | Step-by-step navigation with next / previous / skip / exit controls |

**Key components:**

- **`DirectionTracker`** — owns the route: computes exhibit visit order via greedy nearest-neighbor, runs Dijkstra shortest-path (JGraphT) for each step, and notifies observers on order or direction change
- **`GPSTracker`** — implements `LocationListener` and `DirectionObserver`; fires on device position updates and runs off-track detection using the Vincenty geodesic formula; prompts the user to reroute if a closer unvisited exhibit is detected
- **`ZooData`** — parses three JSON asset files into a JGraphT weighted undirected graph and vertex/edge info maps at startup
- **`NodeDatabase` / `NodeDao`** — Room (SQLite) database persisting exhibit selection state across the app lifecycle
- **`ExhibitViewModel`** — MVVM ViewModel exposing exhibit lists to `MainActivity` via `LiveData`

**Design patterns:** Observer (direction and route-order notifications), MVVM (exhibit list), DAO (Room persistence)

## Zoo data

The zoo map is encoded in three bundled JSON assets:

| File | Contents |
|---|---|
| `exhibit_info.json` | All nodes — gate, exhibits, intersections, and exhibit groups — with lat/lng and searchable tags |
| `trail_info.json` | All edges with street names |
| `zoo_graph.json` | Weighted undirected graph; edge weights are distances in feet |

## Tech stack

| | |
|---|---|
| Language | Java |
| Platform | Android SDK |
| Graph routing | JGraphT (Dijkstra shortest path) |
| Database | Room (SQLite) |
| JSON parsing | Gson |
| Distance calculation | Vincenty geodesic formula |
| Testing | JUnit unit tests + Espresso instrumented tests; CI via GitHub Actions (runs on every push) |

## Testing

Unit tests cover direction generation, route planning, node lookup, and database operations. Instrumented tests cover UI flows including search, exhibit selection, route planning, GPS permission denial, off-track prompting, skip/previous behavior, and route persistence across app restarts.
