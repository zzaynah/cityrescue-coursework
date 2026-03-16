package cityrescue;

import cityrescue.enums.*;

public class FireEngine extends Unit {

    public FireEngine(int id, int homeStationId, int x, int y) {
        super(id, UnitType.FIRE_ENGINE, x, y, homeStationId);
    }

    @Override
    public boolean canHandle(IncidentType type) {
        return type == IncidentType.FIRE;
    }

    @Override
    public int getTicksToResolve(int severity) {
        return 4;
    }
}