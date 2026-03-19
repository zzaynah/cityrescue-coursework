package cityrescue;

import cityrescue.enums.*;
import cityrescue.exceptions.*;

/**
 * CityRescueImpl.java is the main implementation of the CityRescue interface.
 * It is used to handle all the rescue operations, units, stations, incidents, obstacles,
 * and handles the resolutions to incidents over time.
 *
 * This class tracks the units, stations and incidents. It allows removal or addition of stations,
 * increasing and decommissioning units, reporting and cancelling incidents and finally has time ticks.
 */
public class CityRescueImpl implements CityRescue {

    // Map of city
    private CityMap map;

    // Max storage sizes
    private static final int MAX_STATIONS = 20;
    private static final int MAX_UNITS = 50;
    private static final int MAX_INCIDENTS = 200;

    // Arrays to store objects
    private Station[] stations;
    private Unit[] units;
    private Incident[] incidents;

    // Counters
    private int stationCount;
    private int unitCount;
    private int incidentCount;

    // Simulation time
    private int tick;
    
    //Helpers
    /**
     * Searches and returns station with a specific ID
     *
     * @param stationId the unique identification of the station to search
     * @return station if found, null otherwise
     */
    private Station findStation(int stationId) {
        for (int i = 0; i < stationCount; i++) {
            if (stations[i] != null && stations[i].getId() == stationId) {
                return stations[i];
            }
        }
        return null;
    }

    /**
     * Searches and returns the unit with a specific ID
     *
     * @param unitId the unique identification of the unit to search
     * @return unit if found, null otherwise
     */
    private Unit findUnit(int unitId) {
        for (int i = 0; i < unitCount; i++) {
            if (units[i] != null && units[i].getId() == unitId) {
                return units[i];
            }
        }
        return null;
    }

    /**
     * Searches and returns the incident with a specific ID
     *
     * @param incidentId the unique identification of the incident to search
     * @return incident if found, null otherwise
     */
    private Incident findIncident(int incidentId) {
        for (int i = 0; i < incidentCount; i++) {
            if (incidents[i] != null && incidents[i].getId() == incidentId) {
                return incidents[i];
            }
        }
        return null;
    }

    /**
     * Following a set of movement rules, the unit moves towards the incident.
     * This is chosen based on Manhattan distance.
     * Avoids obstacles and checks moves available in the order of ("north", "east", "south", "west").
     * If multiple moves are available:
     * - lists the moves in the order or N,E,S,W
     * - ignores moves going out of bounds
     * - takes the move which would reduce Manhattan distance to the target
     * - if tied, takes the first move available in the order N,E,S,W
     * - if no legal move, unit stays put
     *
     * @param unit this is the unit moving
     * @param incident this is the incident the unit is responding to
     */
    private void moveUnitTowards(Unit unit, Incident incident) {
        int[][] directions = {
            {0, -1}, {1, 0}, {0, 1}, {-1, 0}
        };

        int ux = unit.getX();
        int uy = unit.getY();

        int currentDist = Math.abs(ux - incident.getX()) + Math.abs(uy - incident.getY());

        for (int[] d : directions) {
            int nx = ux + d[0];
            int ny = uy + d[1];

            if (!map.inBounds(nx, ny) || map.isBlocked(nx, ny)) continue;

            int newDist = Math.abs(nx - incident.getX()) + Math.abs(ny - incident.getY());

            if (newDist < currentDist) {
                unit.setLocation(nx, ny);
                return;
            }
        }

        for (int[] d : directions) {
            int nx = ux + d[0];
            int ny = uy + d[1];

            if (!map.inBounds(nx, ny) || map.isBlocked(nx, ny)) continue;

            unit.setLocation(nx, ny);
            return;
        }
    }


    /**
     * Initialises city rescue with a predefined map size.
     * Sets up a grid of the map empty and stores stations, units and incidents in arrays.
     * Begins with 0 incidents, stations and units and sets tick to 0
     *
     * @param width width of the grid
     * @param height height of the grid
     * @throws InvalidGridException if grid is invalid or not within the restrictions
     */
    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        // TODO: implement
        if (width <= 0 || height <= 0) {
            throw new InvalidGridException("Grid width and height must be greater than 0");
        }
        
        map = new CityMap(width, height);

        stations = new Station[MAX_STATIONS];
        units = new Unit[MAX_UNITS];
        incidents = new Incident[MAX_INCIDENTS];

        stationCount = 0;
        unitCount = 0;
        incidentCount = 0;

        tick = 0;
    }

    /**
     * Returns the current size of the city as an array [width, height]
     *
     * @return array with the width and height of the grid
     */
    @Override
    public int[] getGridSize() {
        // TODO: implement
        return new int[]{map.getWidth(), map.getHeight()};
    }

    /**
     * Adds an obstacle in the coordinates selected within the map grid.
     * Checks that the chosen coordinates are within the boundaries otherwise, throws an exception.
     *
     * @param x the x coordinate in the map where the obstacle is to be added in
     * @param y the y coordinate in the map where the obstacle is to be added in
     * @throws InvalidLocationException if coordinates are not within the bounds
     */
    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException {
        // TODO: implement
        if (!map.inBounds(x, y)) {
            throw new InvalidLocationException("Obstacle location is out of bounds");
        }

        map.addObstacle(x, y);
    }

    /**
     * Removes an obstacle in the coordinates selected within the map grid.
     * Checks that the chosen coordinates are within the boundaries, otherwise throws an exception.
     *
     * @param x the x coordinate in the map where the obstacle is to be removed in
     * @param y the y coordinate in the map where the obstacle is to be removed in
     * @throws InvalidLocationException if coordinates are not within the bounds
     */
    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        // TODO: implement
        if (!map.inBounds(x, y)) {
            throw new InvalidLocationException("Obstacle location is out of bounds");
        }

        map.removeObstacle(x, y);
    }

    /**
     * Adds a station in the coordinates selected within the map grid.
     * Checks to see if the name of the station is not blank, the location is within the boundaries,
     * and that maximum number of stations has not been exceeded, otherwise throws an exception.
     *
     * @param name the name of the new station
     * @param x the new x coordinate of the station
     * @param y the new y coordinate of the station
     * @return ID of the new station
     * @throws InvalidNameException if the station name is empty or null
     * @throws InvalidLocationException if the station location is not within the bounds or is blocked
     */
    @Override
    public int addStation(String name, int x, int y) throws InvalidNameException, InvalidLocationException {
        // TODO: implement
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidNameException("Station name must not be blank");
        }

        if (!map.inBounds(x, y)) {
            throw new InvalidLocationException("Station location is out of bounds");
        }

        if (map.isBlocked(x, y)) {
            throw new InvalidLocationException("Station location is blocked");
        }

        if (stationCount >= MAX_STATIONS) {
            throw new IllegalStateException("Max number of stations reached");
        }
        
        int id = stationCount + 1;

        Station station = new Station(id, name, x, y);

        stations[stationCount] = station;

        stationCount++;

        return id;
    }

    /**
     * Removes a station's ID as long as it doesn't have any units assigned to it anymore.
     *
     * @param stationId the unique identification of the station to be removed
     * @throws IDNotRecognisedException if the station is not found or recognised
     * @throws IllegalStateException if the station still owns units
     */
    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        Station station = findStation(stationId);

        if (station == null) {
            throw new IDNotRecognisedException("Station ID not recognised");
        }

        if (station.getUnitCount() > 0) {
            throw new IllegalStateException("Station still owns units and cannot be removed");
        }

        for (int i = 0; i < stationCount; i++) { 
            if (stations[i] != null && stations[i].getId() == stationId) {
                stations[i] = null;
                return;
            }
        }
    }

    /**
     * Sets the capacity of a station.
     * Is invalid if the new capacity is less than the current unit count.
     *
     * @param stationId the unique identification of the station
     * @param maxUnits new maximum unit capacity in the station
     * @throws IDNotRecognisedException if the ID is not recognised
     * @throws InvalidCapacityException if the new capacity is 0 or less than the number of units
     */
    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        // TODO: implement
        Station station = findStation(stationId);

        if (station == null) {
            throw new IDNotRecognisedException("Station ID not recognised");
        }

        if (maxUnits <= 0) {
            throw new InvalidCapacityException("Station capacity must be greater than zero");
        }

        if (maxUnits < station.getUnitCount()) {
            throw new InvalidCapacityException("New capacity cannot be less than current unit count");
        }

        station.setCapacity(maxUnits);
    }

    /**
     * Returns the IDs of all the stations in ascending order.
     *
     * @return an array of the station IDs
     */
    @Override
    public int[] getStationIds() {
        // TODO: implement
        int count = 0;

        for (int i = 0; i < stationCount; i++) {
            if (stations[i] != null) count++;
        }

        int[] ids = new int[count]; 

        int index = 0;

        for (int i = 0; i < stationCount; i++) {
            if (stations[i] != null) {
                ids[index++] = stations[i].getId();
            }
        }

        return ids;
    }

    /**
     * Adds a unit to the station.
     * Throws an exception if the unit is null, station ID cannot be found or if maximum capacity has been met.
     *
     * @param stationId the unique identification of the station the unit is to be added to
     * @param type type of the unit to be added in the station
     * @return the ID of the new unit
     * @throws IDNotRecognisedException if the station ID is not recognised
     * @throws InvalidUnitException if the unit type to be added in the station is invalid
     * @throws IllegalStateException if the station has no free capacity
     */
    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        // TODO: implement
        if (type == null) {
            throw new InvalidUnitException("Unit type cannot be null");
        }

        Station station = findStation(stationId);

        if (station == null) {
            throw new IDNotRecognisedException("Station ID not recognised");
        }

        if (unitCount >= MAX_UNITS) {
            throw new IllegalStateException("Maximum number of units reached");
        }

        if (station.getUnitCount() >= station.getCapacity()) {
            throw new IllegalStateException("Station has no free capacity");
        }

        int id = unitCount + 1;

        Unit unit;

        switch(type) {
            case AMBULANCE:
                unit = new Ambulance(id, stationId, station.getX(), station.getY());
                break;

            case FIRE_ENGINE:
                unit = new FireEngine(id, stationId, station.getX(), station.getY());
                break;

            case POLICE_CAR:
                unit = new PoliceCar(id, stationId, station.getX(), station.getY());
                break;

            default:
                throw new InvalidUnitException("Invalid unit");
        }


        units[unitCount] = unit;

        unitCount++;

        station.addUnit();

        return id;
    }

    /**
     * Decommissions a unit based on its ID. Cannot occur if the unit is busy.
     *
     * @param unitId the unique identification of the unit to be decommissioned
     * @throws IDNotRecognisedException if the ID is not recognised
     * @throws IllegalStateException if the unit cannot be decommissioned
     */
    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        Unit unit = findUnit(unitId);

        if (unit == null) {
            throw new IDNotRecognisedException("Unit ID not recognised");
        }

        if (unit.getStatus() == UnitStatus.EN_ROUTE || unit.getStatus() == UnitStatus.AT_SCENE) {
            throw new IllegalStateException("Unit cannot be decommissioned while busy");
        }

        for (int i = 0; i < unitCount; i++) {
            if (units[i] != null && units[i].getId() == unitId) {
                units[i] = null;

                Station station = findStation(unit.getHomeStationId());
                station.removeUnit();

                return;
            }
        }
    }

    /**
     * Transfers a unit from current station to new station.
     * The unit must be idle and the station must not be full.
     *
     * @param unitId gets the unique identification of the unit to be transferred
     * @param newStationId gets the unique identification of the station to transfer the unit to
     * @throws IDNotRecognisedException if the unit's ID or station is not recognised
     * @throws IllegalStateException if the unit is not idle or the station is full
     */
    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        Unit unit = findUnit(unitId);
        Station newStation = findStation(newStationId);

        if (unit == null) {
            throw new IDNotRecognisedException("Unit ID not recognised");
        }

        if (newStation == null) {
            throw new IDNotRecognisedException("Destination station not recognised");
        }

        if (unit.getStatus() != UnitStatus.IDLE) {
            throw new IllegalStateException("Unit must be idle to transfer");
        }

        if (newStation.getUnitCount() >= newStation.getCapacity()) {
            throw new IllegalStateException("Destination station full");
        }

        Station oldStation = findStation(unit.getHomeStationId());

        oldStation.removeUnit();
        newStation.addUnit();

        unit.setHomeStationId(newStationId);
        unit.setLocation(newStation.getX(), newStation.getY());
    }

    /**
     * Sets a unit's status as out of service or idle.
     * Units must be idle to go out of service and vice versa.
     *
     * @param unitId gets the unique identification of the unit
     * @param outOfService returns a boolean indicating whether unit is go out of service or idle
     * @throws IDNotRecognisedException if unit ID is not recognised
     * @throws IllegalStateException if unit cannot be set out of service or back to idle
     */
    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        Unit unit = findUnit(unitId);

        if (unit == null) {
            throw new IDNotRecognisedException("Unit ID not recognised");
        }

        if (outOfService) {
            if (unit.getStatus() != UnitStatus.IDLE) {
                throw new IllegalStateException("Unit must be idle to go out of service");
            }
            unit.setStatus(UnitStatus.OUT_OF_SERVICE);

        } else {
            if (unit.getStatus() != UnitStatus.OUT_OF_SERVICE) {
                throw new IllegalStateException("Unit is not out of service");
            }
            unit.setStatus(UnitStatus.IDLE);
        }
    }

    /**
     * Returns all the IDs of the units in ascending order
     *
     * @return array of the units Ids
     */
    @Override
    public int[] getUnitIds() {
        // TODO: implement
        int count = 0;

        for (int i = 0; i < unitCount; i++) {
            if (units[i] != null) count++;
        }

        int[] ids = new int[count];
        int index = 0;

        for (int i = 0; i < unitCount; i++) {
            if (units[i] != null) {
                ids[index++] = units[i].getId();
            }
        }

        return ids;
    }

    /**
     * Returns a string with the unit ID, the unit type, the home station ID, the location of the unit,
     * the status of the unit, the incident ID to which the current unit is responding and the work tick,
     * which is only available if the unit is at the scene.
     *
     * @param unitId the unique Identification of the unit to view
     * @return string containing information regarding the unit
     * @throws IDNotRecognisedException if the unit ID is not recognised
     */
    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        // TODO: implement
        Unit unit = findUnit(unitId);

        if (unit == null) {
            throw new IDNotRecognisedException("Unit ID not recognised");
        }
        
        String incident = "-";
        if (unit.getIncidentId() != -1) {
            incident = String.valueOf(unit.getIncidentId());
        }

        String result = String.format(
            "U#%d TYPE=%s HOME=%d LOC=(%d,%d) STATUS=%s INCIDENT=%s",
            unit.getId(),
            unit.getType(),
            unit.getHomeStationId(),
            unit.getX(),
            unit.getY(),
            unit.getStatus(),
            incident
        );

        if (unit.getStatus() == UnitStatus.AT_SCENE) {
            result += " WORK=" + unit.getWorkTicks();
        }

        return result;

    }

    /**
     * Reports an incident, including the type, the severity and the location.
     * Throws an exception if the incident is null, severity is out of the range, the location is out of the bounds,
     * the location is blocked or if the maximum number of incidents has been reached.
     *
     * @param type the type of incident
     * @param severity the severity of the incident
     * @param x the x coordinate of the incident location
     * @param y the y coordinate of the incident location
     * @return the ID of a new accident being created
     * @throws InvalidSeverityException if the severity is out of range
     * @throws InvalidLocationException if the location is out of the bounds or is blocked
     */
    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        // TODO: implement
        if (type == null) {
            throw new InvalidSeverityException("Incident type cannot be null");
        }

        if (severity < 1 || severity > 5) {
            throw new InvalidSeverityException("Incident severity must be between 1 and 5");
        }

        if (!map.inBounds(x, y)) {
            throw new InvalidLocationException("Incident location is out of bounds");
        }

        if (map.isBlocked(x, y)) {
            throw new InvalidLocationException("Incident location is blocked");
        }

        if (incidentCount >= MAX_INCIDENTS) {
            throw new IllegalStateException("Max number of incidents reached");
        }

        int id = incidentCount + 1;

        Incident incident = new Incident(id, type, severity, x, y);

        incidents[incidentCount] = incident;

        incidentCount++;

        return id;
    }

    /**
     * Cancels an incident by its ID unless it has already been resolved or in progress.
     * If dispatched, the assigned unit is set to idle.
     *
     * @param incidentId the unique identification of the incident to cancel
     * @throws IDNotRecognisedException if incident ID is not recognised
     * @throws IllegalStateException if incident cannot be cancelled due to its current state
     */
    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        Incident incident = findIncident(incidentId);

        if (incident == null) {
            throw new IDNotRecognisedException("Incident ID not recognised");
        }

        if (incident.getStatus() != IncidentStatus.REPORTED && incident.getStatus() != IncidentStatus.DISPATCHED) {
            throw new IllegalStateException("Incident cannot be cancelled in its current state");
        }

        if (incident.getStatus() == IncidentStatus.DISPATCHED) {
            Unit unit = findUnit(incident.getAssignedUnitId());

            if (unit != null) {
                unit.setStatus(UnitStatus.IDLE);
                unit.clearIncident();
                incident.setAssignedUnitId(-1);
            }
        }

        incident.setStatus(IncidentStatus.CANCELLED);
    }

    /**
     * Escalates the severity of the incident which must be between 1 and 5,
     * and the incident must not be resolved or cancelled.
     *
     * @param incidentId the unique identification of the incident to escalate
     * @param newSeverity new severity level ranging from 1 to 5
     * @throws IDNotRecognisedException if incident ID is not recognised
     * @throws InvalidSeverityException if severity level is not between 1 and 5
     * @throws IllegalStateException if incident is resolved or cancelled
     */
    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        // TODO: implement
        Incident incident = findIncident(incidentId);

        if (incident == null) {
            throw new IDNotRecognisedException("Incident ID not recognised");
        }

        if (newSeverity < 1 || newSeverity > 5) {
            throw new InvalidSeverityException("Severity must be between 1 and 5");
        }

        if (incident.getStatus() == IncidentStatus.RESOLVED || incident.getStatus() == IncidentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot escalate a resolved or cancelled incident");
        }

        incident.setSeverity(newSeverity);
    }

    /**
     * Returns the IDs of the incidents in ascending order.
     *
     * @return an array of the incident ids
     */
    @Override
    public int[] getIncidentIds() {
        // TODO: implement
        int count = 0;

        for (int i = 0; i < incidentCount; i++) {
            if (incidents[i] != null) count++;
        }

        int[] ids = new int[count];
        int index = 0;

        for (int i = 0; i < incidentCount; i++) {
            if (incidents[i] != null) {
                ids[index++] = incidents[i].getId();
            }
        }

        return ids;
    }

    /**
     * Returns a string with the incident ID, the incident type, the severity, the location of the incident,
     * the status of the incident and the unit ID to which the current incident is resolved by.
     *
     * @param incidentId the unique identification of the incident to view
     * @return a string containing information regarding the incident
     * @throws IDNotRecognisedException if the incident ID is not recognised
     */
    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        // TODO: implement
        Incident incident = findIncident(incidentId);

        if (incident == null) {
            throw new IDNotRecognisedException("Incident ID not recognised");
        }

        String unit = "-";

        if (incident.getAssignedUnitId() != -1) {
            unit = String.valueOf(incident.getAssignedUnitId());
        }

        return String.format(
            "I#%d TYPE=%s SEV=%d LOC=(%d,%d) STATUS=%s UNIT=%s",
            incident.getId(),
            incident.getType(),
            incident.getSeverity(),
            incident.getX(),
            incident.getY(),
            incident.getStatus(),
            unit
        );
    }

    /**
     * Dispatches an available unit to the reported incident. Depends on which unit has the shortest Manhattan distance,
     * to the location the incident is taking place in.
     */
    @Override
    public void dispatch() {
        // TODO: implement
        for (int i = 0; i < incidentCount; i++) {
            Incident incident = incidents[i];
            if (incident == null) continue;
            if (incident.getStatus() != IncidentStatus.REPORTED) continue;

            Unit bestUnit = null;
            int bestDistance = Integer.MAX_VALUE;

            for (int j = 0; j < unitCount; j++) {
                Unit unit = units[j];
                if (unit == null) continue;
                if (unit.getStatus() != UnitStatus.IDLE) continue;
                if (!unit.canHandle(incident.getType())) continue;

                int distance = Math.abs(unit.getX() - incident.getX()) + Math.abs(unit.getY() - incident.getY());

                if (bestUnit == null || distance < bestDistance || (distance == bestDistance && unit.getId() < bestUnit.getId()) || (distance == bestDistance && unit.getId() == bestUnit.getId() && unit.getHomeStationId() < bestUnit.getHomeStationId())) {
                    bestUnit = unit;
                    bestDistance = distance;
                }
            }

            if (bestUnit != null) {
                incident.setAssignedUnitId(bestUnit.getId());
                incident.setStatus(IncidentStatus.DISPATCHED);

                bestUnit.setIncidentId(incident.getId());
                bestUnit.resetWorkTicks();
                bestUnit.setStatus(UnitStatus.EN_ROUTE);
            }
        }
    }

    /**
     * Introduces a tick system which keeps track of the time units spend at incident locations and solving them,
     * updates the units status during, after an incident tracking their work progress in the meantime.
     * If a unit resolves an incident, it is marked as resolved
     */
    @Override
    public void tick() {
        // TODO: implement
        tick++;

        for (int i = 0; i < unitCount; i++) {
            Unit unit = units[i];
            if (unit == null) continue;
            if (unit.getStatus() != UnitStatus.EN_ROUTE) continue;

            Incident incident = findIncident(unit.getIncidentId());
            if (incident == null) continue;

            moveUnitTowards(unit, incident);
        }

        for (int i = 0; i < unitCount; i++) {
            Unit unit = units[i];
            if (unit == null) continue;
            if (unit.getStatus() != UnitStatus.EN_ROUTE) continue;
            
            Incident incident = findIncident(unit.getIncidentId());
            if (incident == null) continue;

            if (unit.getX() == incident.getX() && unit.getY() == incident.getY()) {
                unit.setStatus(UnitStatus.AT_SCENE);
                incident.setStatus(IncidentStatus.IN_PROGRESS);
            }
        }

        for (int i = 0; i < unitCount; i++) {
            Unit unit = units[i];
            if (unit == null) continue;
            if (unit.getStatus() != UnitStatus.AT_SCENE) continue;
            
            unit.incrementWorkTicks();
        }

        for (int j = 0; j < incidentCount; j++) {
            Incident incident = incidents[j];
            if (incident == null) continue;
            if (incident.getStatus() != IncidentStatus.IN_PROGRESS) continue;

            Unit unit = findUnit(incident.getAssignedUnitId());
            if (unit == null) continue;

            int required = unit.getTicksToResolve(incident.getSeverity());

            if (unit.getWorkTicks() >= required) {
                incident.setStatus(IncidentStatus.RESOLVED);

                unit.setStatus(UnitStatus.IDLE);
                unit.clearIncident();
                unit.resetWorkTicks();
            }
        }
    }

    /**
     * Returns the status of the city which includes the number of stations, units, incidents, obstacles,
     * and the ticks. It summarises that state of the rescue system.
     *
     * @return a string which illustrates the status of the rescue system
     */
    @Override
    public String getStatus() {
        StringBuilder st = new StringBuilder();

        int obstacleCount = 0;
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                if (map.isBlocked(x, y)) {
                    obstacleCount++;
                }
            }
        }

        st.append(String.format("TICK=%d\n", tick));

        int realStations = getStationIds().length;
        int realUnits = getUnitIds().length;
        int realIncidents = getIncidentIds().length;

        st.append(String.format("STATIONS=%d UNITS=%d INCIDENTS=%d OBSTACLES=%d\n", realStations, realUnits, realIncidents, obstacleCount));

        st.append("INCIDENTS\n");
        for (int i = 0; i < incidentCount; i++) {
            if(incidents[i] != null) {
                try {
                    st.append(viewIncident(incidents[i].getId())).append("\n");
                } catch (IDNotRecognisedException e) {
                }
            }
        }    
        
        st.append("UNITS\n");
        for (int i = 0; i < unitCount; i++) {
            if(units[i] != null) {
                try {
                    st.append(viewUnit(units[i].getId())).append("\n");
                } catch (IDNotRecognisedException e) {
                }
            }
        }
        return st.toString().trim();
    }
}
