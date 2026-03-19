package cityrescue;

import cityrescue.enums.*;

/**
 * Unit.java handles common unit behaviour shared by emergency vehicles.
 * Depicts a rescue unit's type, including whether it is an ambulance, a fire engine or a police car,
 * handling each unit type depending on how they respond to it.
 * Status indicates whether a unit is Idle, en route, at the scene or out of service.
 * Tracks work progress for each unit type.
 *
 * This abstract class manages a unit's ID, the unit type, the location as a coordinate,
 * the status and finally the work tick, which is used to track progress across an incident resolution.
 */
public abstract class Unit {

    protected int id;
    protected UnitType type;

    protected int x;
    protected int y;

    protected int homeStationId;

    protected UnitStatus status;

    protected int assignedIncidentId = -1;

    protected int workTicks;

    /**
     * Constructs a unit
     *
     * @param id this is the unique identifier for the unit
     * @param type the type of unit ("ambulance", "fire engine" or "police car")
     * @param x the x coordinate for the location of the unit
     * @param y the y coordinate for the location of the unit
     * @param homeStationId the identifier of the starting point, home station
     */
    public Unit(int id, UnitType type, int x, int y, int homeStationId) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.homeStationId = homeStationId;
        this.status = UnitStatus.IDLE;
    }

    /**
     * Returns the identifier for the unit
     *
     * @return the ID of the unit
     */
    public int getId() {
        return id;
    }

    /**
     * Returns what type the unit is
     *
     * @return the type of the unit
     */
    public UnitType getType() {
        return type;
    }

    /**
     * Returns the x coordinate of the unit's location
     *
     * @return x coordinate of the unit's location
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y coordinate of the unit's location
     *
     * @return y coordinate of the unit's location
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the location of the unit
     *
     * @param x new x coordinate of the unit's location
     * @param y new y coordinate of the unit's location
     */
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the status of the unit
     *
     * @return the status of the unit
     */
    public UnitStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the unit
     *
     * @param status the new status of the unit
     */
    public void setStatus(UnitStatus status) {
        this.status = status;
    }

    /**
     * Returns the ID of the incident associated with the unit or -1 if no incident.
     *
     * @return incident ID, or -1 if not assigned
     */
    public int getIncidentId() {
        return assignedIncidentId;
    }

    /**
     * Sets the ID of the incident associated with the unit
     *
     * @param incidentId ID of the incident associated with the unit
     */
    public void setIncidentId(int incidentId) {
        this.assignedIncidentId = incidentId;
    }

    /**
     * Clears the assigned incident from the unit
     */
    public void clearIncident() {
        this.assignedIncidentId = -1;
    }

    /**
     * Returns the ID of the station the unit starts in
     *
     * @return the home station ID
     */
    public int getHomeStationId() {
        return homeStationId;
    }

    /**
     * Sets the home station for a unit
     *
     * @param stationId the ID of the new home station for that unit
     */
    public void setHomeStationId(int stationId) {
        this.homeStationId = stationId;
    }

    /**
     * Returns the number of work ticks of a unit whilst at the incident
     *
     * @return work ticks
     */
    public int getWorkTicks() {
        return workTicks;
    }

    /**
     * Increments work ticks for the unit tracking progress whilst the unit is resolving an incident.
     */
    public void incrementWorkTicks() {
        workTicks++;
    }

    /**
     * Resets work ticks to zero, whenever a unit starts resolving a new incident or is idle
     */
    public void resetWorkTicks() {
        workTicks = 0;
    }

    /**
     * Determines whether the unit can handle the incident in question.
     *
     * @param type the type of incident
     * @return true/false depending on whether the unit can handle the incident
     */
    public abstract boolean canHandle(IncidentType type);

    /**
     * Returns the number of ticks required to resolve an incident for a given severity level.
     *
     * @param severity the severity of the incident
     * @return number of ticks to resolve that incident
     */
    public abstract int getTicksToResolve(int severity);
}