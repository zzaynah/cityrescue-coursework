package cityrescue;

import java.util.Arrays;

import cityrescue.enums.*;
import cityrescue.exceptions.*;

/**
 * CityRescueImpl (Starter)
 *
 * Your task is to implement the full specification.
 * You may add additional classes in any package(s) you like.
 */
public class CityRescueImpl implements CityRescue {

    // TODO: add fields (map, arrays for stations/units/incidents, counters, tick, etc.)
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
    private Station findStation(int stationId) {
        for (int i = 0; i < stationCount; i++) {
            if (stations[i] != null && stations[i].getId() == stationId) {
                return stations[i];
            }
        }
        return null;
    }

    private Unit findUnit(int unitId) {
        for (int i = 0; i < unitCount; i++) {
            if (units[i] != null && units[i].getId() == unitId) {
                return units[i];
            }
        }
        return null;
    }

    private Incident findIncident(int incidentId) {
        for (int i = 0; i < incidentCount; i++) {
            if (incidents[i] != null && incidents[i].getId() == incidentId) {
                return incidents[i];
            }
        }
        return null;
    }


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

    @Override
    public int[] getGridSize() {
        // TODO: implement
        return new int[]{map.getWidth(), map.getHeight()};
    }

    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException {
        // TODO: implement
        if (!map.inBounds(x, y)) {
            throw new InvalidLocationException("Obstacle location is out of bounds");
        }

        map.addObstacle(x, y);
    }

    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        // TODO: implement
        if (!map.inBounds(x, y)) {
            throw new InvalidLocationException("Obstacle location is out of bounds");
        }

        map.removeObstacle(x, y);
    }

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

        station.incrementUnitCount();

        return id;
    }

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

        Station station = findStation(unit.getHomeStationId());
        station.decrementUnitCount();

        for (int i = 0; i < unitCount; i++) {
            if (units[i] != null && units[i].getId() == unitId) {
                units[i] = null;
                return;
            }
        }
    }

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

        Station oldStation = findStation(unit.getStationId();
    )
        oldStation.decrementUnitCount();
        newStation.incrementUnitCount();

        unit.setStationId(newStationId);
        unit.setLocation(newStation.getX(), newStation.getY());
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        Unit unit = findUnit(unitId);

        if (unit == null) {
            throw new IDNotRecognisedException("Unit ID not recognised");
        }

        if (outOfService) {
            if (unit.getStatus() != UnitStatus.IDLE) {
                throw new IllegalStateException("Unit must be idle to transfer");
            }
            unit.setStatus(UnitStatus.OUT_OF_SERVICE);

        } else {
            if (unit.getStatus() != UnitStatus.OUT_OF_SERVICE) {
                throw new IllegalStateException("Unit is not out of service");
            }
            unit.setStatus(UnitStatus.IDLE);
        }
    }

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

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        // TODO: implement
        if (type == null) {
            throw new IllegalArgumentException("Incident type cannot be null");
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
            Unit unit = findUnit(incident.getUnitId());

            if (unit != null) {
                unit.setStatus(UnitStatus.IDLE);
                unit.clearIncident();
            }
        }

        incident.setStatus(IncidentStatus.CANCELLED);
    }

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

    @Override
    public int[] getIncidentIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        // TODO: implement
        Incident incident = findIncident(incidentId);

        if (incident == null) {
            throw new IDNotRecognisedException("Incident ID not recognised");
        }

        String unit = "-";

        if (incident.getUnitId() != -1) {
            unit = String.valueOf(incident.getUnitId());
        }

        return "I#" + incident.getId() + " TYPE=" + incident.getType() + "SEV=" + incident.getSeverity() + " LOC=(" + incident.getX() + "," + incident.getY() + ")" + " STATUS=" + incident.getStatus() + " UNIT=" + unit;
    }

    @Override
    public void dispatch() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void tick() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getStatus() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
