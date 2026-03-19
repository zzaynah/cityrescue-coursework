import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import cityrescue.*;
import cityrescue.enums.*;
import cityrescue.exceptions.*;

public class CustomDispatchTest {
    private CityRescue cr;

    @BeforeEach
    void setUp() throws Exception {
        cr = new CityRescueImpl();
        cr.initialise(6, 6);
    }

    @Test
    void tieBreaker_lowestUnitIdWins() throws Exception {
        int s1 = cr.addStation("A", 0, 0);
        int s2 = cr.addStation("S", 0, 0);

        int u1 = cr.addUnit(s1, UnitType.POLICE_CAR);
        int u2 = cr.addUnit(s2, UnitType.POLICE_CAR);

        int i = cr.reportIncident(IncidentType.CRIME, 1, 1, 0);

        cr.dispatch();

        String inc = cr.viewIncident(i);
        assertTrue(inc.contains("UNIT=" + u1));
    }

    @Test
    void ignoresOutOfServiceUnits() throws Exception {
        int s = cr.addStation("A", 0, 0);
        int u1 = cr.addUnit(s, UnitType.FIRE_ENGINE);
        int u2 = cr.addUnit(s, UnitType.FIRE_ENGINE);

        cr.setUnitOutOfService(u1, true);

        int i = cr.reportIncident(IncidentType.FIRE, 2, 1, 0);

        cr.dispatch();

        String inc = cr.viewIncident(i);
        assertTrue(inc.contains("UNIT=" + u2));
        assertFalse(inc.contains("UNIT=" + u1));
    }

    @Test
    void wrongUnitType_notAssigned() throws Exception {
        int s = cr.addStation("A", 0, 0);
        cr.addUnit(s, UnitType.AMBULANCE);

        int i = cr.reportIncident(IncidentType.FIRE, 2, 1, 1);

        cr.dispatch();

        String inc = cr.viewIncident(i);
        assertTrue(inc.contains("UNIT=-"));
    }
}