package cityrescue;

import cityrescue.enums.*;

public class Ambulance extends Unit {

    public Ambulance(int id, int homeStationId, int x, int y) {
        super(id, UnitType.AMBULANCE, x, y, homeStationId);
    }

    @Override
    public boolean canHandle(IncidentType type) {
        return type == IncidentType.MEDICAL;
    }

    @Override
    public int getTicksToResolve(int severity) {
        return 2;
    }
}