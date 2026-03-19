package cityrescue;

import cityrescue.enums.*;

/**
 * PoliceCar.java represents the police car unit. This unit, which is inherited from Unit.java,
 * responds to crime related incidents, with a tick value of 3. As such,
 * this class should be called whenever there is an incident suggesting a crime has occured.
 */
public class PoliceCar extends Unit {

    /**
     * Constructs the police car unit.
     *
     * @param id the unique identifier for the police car
     * @param homeStationId the identifier of the starting point, home station
     * @param x this is the x coordinate for the location of the police car
     * @param y this is the y coordinate for the location of the police car
     */
    public PoliceCar(int id, int homeStationId, int x, int y) {
        super(id, UnitType.POLICE_CAR, x, y, homeStationId);
    }

    /**
     * Determine the type of incident handled by the police car
     *
     * @param type the type of incident to validate
     * @return true if incident is "CRIME", false otherwise
     */
    @Override
    public boolean canHandle(IncidentType type) {
        return type == IncidentType.CRIME;
    }

    /**
     * Returns the number of ticks required to resolve a crime related incident depending on the severity.
     * The police car has a fixed tick value of 3.
     *
     * @param severity the severity of the crime related incident
     * @return fixed tick value to resolve the incident which is 3
     */
    @Override
    public int getTicksToResolve(int severity) {
        return 3;
    }
}