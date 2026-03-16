package cityrescue;

import cityrescue.enums.*;

public abstract class Unit {

    protected int id;
    protected UnitType type;

    protected int x;
    protected int y;

    protected int homeStationId;

    protected UnitStatus status;

    protected int assignedIncidentId = -1;

    public Unit(int id, UnitType type, int x, int y, int homeStationId) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.homeStationId = homeStationId;
        this.status = UnitStatus.IDLE;
    }

    public int getId() {
        return id;
    }

    public UnitType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public UnitStatus getStatus() {
        return status;
    }

    public void setStatus(UnitStatus status) {
        this.status = status;
    }

    public int getIncidentId() {
        return assignedIncidentId;
    }

    public void setIncidentId(int incidentId) {
        this.assignedIncidentId = incidentId;
    }

    public void clearIncident() {
        this.assignedIncidentId = -1;
    }

    public int getHomeStationId() {
        return homeStationId;
    }

    public void setHomeStationId(int stationId) {
        this.homeStationId = stationId;
    }

    public abstract boolean canHandle(IncidentType type);

    public abstract int getTicksToResolve(int severity);
}