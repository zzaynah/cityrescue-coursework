package cityrescue;

import cityrescue.enums.*;

public class PoliceCar extends Unit {

    public PoliceCar(int id, int homeStationId, int x, int y) {
        super(id, UnitType.POLICE_CAR, x, y, homeStationId);
    }

    @Override
    public boolean canHandle(IncidentType type) {
        return type == IncidentType.CRIME;
    }

    @Override
    public int getTicksToResolve(int severity) {
        return 3;
    }
}