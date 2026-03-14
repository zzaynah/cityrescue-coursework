package cityrescue;

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
    
    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        // TODO: implement
        if (width <= 0 || height <= 0) {
            throw new InvalidGridException("Invalid grid size");
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
            throw new InvalidLocationException("Invalid location");
        }

        map.addObstacle(x, y);
    }

    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        // TODO: implement
        if (!map.inBounds(x, y)) {
            throw new InvalidLocationException("Invalid location");
        }

        map.removeObstacle(x, y);
    }

    @Override
    public int addStation(String name, int x, int y) throws InvalidNameException, InvalidLocationException {
        // TODO: implement
        if (name == null || name.isEmpty()) {
            throw new InvalidNameException();
        }

        if (!map.inBounds(x, y)) {
            throw new InvalidLocationException();
        }

        if (stationCount >= MAX_STATIONS) {
            throw new IllegalStateException();
        }

        Station s = new Station(stationCount, name, x, y);

        stations[stationCount] = s;

        stationCount++;

        return s.getId();
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        int stationIndex = -1;

        for (int i = 0; i < stationCount; i++) {
            if (stations[i] != null && stations[i].getId() == stationId) {
                stationIndex = i;
                break;
            }
        }

        if (stationIndex == -1) {
            throw new IDNotRecognisedException("Station not recognised");
        }

        for (int i = 0; i < unitCount; i++) { 
            if (units[i] != null && units[i].getStationId() == stationId) {
                throw new IllegalStateException("Station still has units");
            }
        }


        stations[stationIndex] = null;
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        // TODO: implement
        if (stationId < 0 || stationId >= stationCount || stations[stationId] == null) {
            throw new IDNotRecognisedException(("Station ID not recognised");
        }

        if (maxUnits <= 0) {
            throw new InvalidCapacityException("Capacity must be positive");
        }

        stations[stationId].setCapacity(maxUnits);
    }

    @Override
    public int[] getStationIds() {
        // TODO: implement
        int[] ids = new int[stationCount];

        for (int i = 0; i < stationCount; i++) {
            ids[i] = stations[i].getId();
        }

        return ids;
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        // TODO: implement
        if (type == null) {
            throw new InvalidUnitException("Unit type cannot be null");
        }

        if (stationId < 0 || stationId >= stationCount || stations[stationId] == null) {
            throw new IDNotRecognisedException("Station not recognised");
        }

        if (unitCount >= MAX_UNITS) {
            throw new IllegalStateException("Maximum units reached");
        }

        Station s = stations[stationId];

        if (s.getUnitCount() >= s.getCapacity()) {
            throw new IllegalStateException("Station has no free capacity");
        }

        int unitId = unitCount + 1;

        Unit unit = new Unit(unitId, type, stationId, s.getX(), s.getY());

        units[unitCount] = unit;

        unitCount ++;

        s.incrementUnitCount();

        return unitId;
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getUnitIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getIncidentIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
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
