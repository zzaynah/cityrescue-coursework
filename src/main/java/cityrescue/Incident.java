package cityrescue;

import cityrescue.enums.*;

public class Incident {

    private int id;
    private IncidentType type;
    private int severity;

    private int x;
    private int y;

    private IncidentStatus status;

    private int assignedUnitId = -1;

    public Incident(int id, IncidentType type, int severity, int x, int y) {
        this.id = id;
        this.type = type;
        this.severity = severity;
        this.x = x;
        this.y = y;
        this.status = IncidentStatus.REPORTED;
    }

    public int getId() {
        return id;
    }

    public IncidentType getType() {
        return type;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public void setStatus(IncidentStatus status) {
        this.status = status;
    }

    public int getAssignedUnitId() {
        return assignedUnitId;
    }

    public void setAssignedUnitId(int id) {
        this.assignedUnitId = id;
    }
}