package cityrescue;

import cityrescue.enums.*;

/**
 * Ambulance.java represents the police ambulance unit. This unit, which is inherited from Unit.java,
 * responds to medical related incidents, with a tick value of 2. As such,
 * this class should be called whenever there is an incident indicating a medical incident has taken place.
 */
public class Ambulance extends Unit {

    /**
     * Constructs the ambulance unit.
     *
     * @param id the unique identifier for the ambulance
     * @param homeStationId the identifier of the starting point, home station
     * @param x this is the x coordinate for the location of the ambulance
     * @param y this is the y coordinate for the location of the ambulance
     */
    public Ambulance(int id, int homeStationId, int x, int y) {
        super(id, UnitType.AMBULANCE, x, y, homeStationId);
    }

    /**
     * Determine the type of incident handled by the ambulance.
     * Returns true if the type of incident being handles is medical, false otherwise.
     *
     * @param type the type of incident to validate
     * @return true if incident is "MEDICAL", false otherwise
     */
    @Override
    public boolean canHandle(IncidentType type) {
        return type == IncidentType.MEDICAL;
    }

    /**
     * Returns the number of ticks required to resolve a medical related incident depending on the severity.
     * The ambulance has a fixed tick value of 2.
     *
     * @param severity the severity of the medical related incident
     * @return fixed tick value to resolve the incident which is 2
     */
    @Override
    public int getTicksToResolve(int severity) {
        return 2;
    }
}