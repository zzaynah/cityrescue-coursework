package cityrescue;

import cityrescue.enums.*;

/**
 * Incident.java represents an event which requires attention and response from rescue units.
 *
 * This class encapsulates the incident's type, severity, location and status.
 * The statuses track progress of an incident.
 */
public class Incident {

    private int id;
    private IncidentType type;
    private int severity;

    private int x;
    private int y;

    private IncidentStatus status;

    private int assignedUnitId = -1;

    /**
     * Sets up an incident for the various incident types
     *
     * @param id this is the unique identifier for the incident
     * @param type the type of incident ("medical", "fire" or "criminal")
     * @param severity severity of the incident (1-5)
     * @param x the x coordinate for the location of the incident
     * @param y the y coordinate for the location of the incident
     */
    public Incident(int id, IncidentType type, int severity, int x, int y) {
        this.id = id;
        this.type = type;
        this.severity = severity;
        this.x = x;
        this.y = y;
        this.status = IncidentStatus.REPORTED;
    }

    /**
     * Returns the unique ID of an incident.
     *
     * @return incident ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the type of incident.
     *
     * @return incident type
     */
    public IncidentType getType() {
        return type;
    }

    /**
     * Returns the severity of the incident.
     *
     * @return incident severity
     */
    public int getSeverity() {
        return severity;
    }

    /**
     * Sets the incident severity.
     *
     * @param severity new severity level (1-5)
     */
    public void setSeverity(int severity) {
        this.severity = severity;
    }

    /**
     * Returns the x coordinate of the incident location.
     *
     * @return x coordinate of the incident
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y coordinate of the incident location.
     *
     * @return y coordinate of the incident
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the status of the incident ("reported", "dispatched", "en-route", "resolved", "cancelled").
     *
     * @return status of the incident
     */
    public IncidentStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the incident to a new status.
     *
     * @param status new status of incident ("reported", "dispatched", "en-route", "resolved", "cancelled")
     */
    public void setStatus(IncidentStatus status) {
        this.status = status;
    }

    /**
     * Returns the ID of the unit associated with resolving the incident.
     *
     * @return ID of assigned unit, -1 if no unit is assigned
     */
    public int getAssignedUnitId() {
        return assignedUnitId;
    }

    /**
     * Assigns a unit to the incident.
     *
     * @param id obtains the id of the unit assigned to that specific incident.
     */
    public void setAssignedUnitId(int id) {
        this.assignedUnitId = id;
    }
}