package cityrescue;

import cityrescue.enums.*;

/**
 * FireEngine.java represents the fire engine unit. This unit, which is inherited from Unit.java,
 * responds to fire related incidents, with a tick value of 4. As such,
 * this class should be called whenever there is an incident suggesting a fire took place.
 */
public class FireEngine extends Unit {

    /**
     * Constructs the fire engine unit.
     *
     * @param id the unique identifier of the fire engine
     * @param homeStationId the identifier of the starting point, home station
     * @param x this is the x coordinate for the location of the fire engine
     * @param y this is the y coordinate for the location of the fire engine
     */
    public FireEngine(int id, int homeStationId, int x, int y) {
        super(id, UnitType.FIRE_ENGINE, x, y, homeStationId);
    }

    /**
     * Determine the type of incident handled by the fire engine.
     *
     * @param type the type of incident to validate
     * @return true if incident is "FIRE", false otherwise
     */
    @Override
    public boolean canHandle(IncidentType type) {
        return type == IncidentType.FIRE;
    }

    /**
     * Returns the number of ticks required to resolve a fire related incident depending on the severity.
     * The fire engine has a fixed tick value of 4.
     *
     * @param severity the severity of a fire related incident
     * @return fixed tick value to resolve the incident which is 4
     */
    @Override
    public int getTicksToResolve(int severity) {
        return 4;
    }
}