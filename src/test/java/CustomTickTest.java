import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import cityrescue.*;
import cityrescue.enums.*;
import cityrescue.exceptions.*;

public class CustomTickTest {
    private CityRescue cr;

    @BeforeEach
    void setUp() throws Exception {
        cr = new CityRescueImpl();
        cr.initialise(5, 5);
    }

    @Test
    void movement_respectsObstacles() throws Exception {
        int s = cr.addStation("A", 0, 0);
        int u = cr.addUnit(s, UnitType.POLICE_CAR);

        cr.addObstacle(0, 1);

        int i = cr.reportIncident(IncidentType.CRIME, 1, 0, 2);

        cr.dispatch();
        cr.tick();

        assertFalse(cr.viewUnit(u).contains("LOC=(0,1)"));
    }

    @Test
    void resolvesInIncidentOrder() throws Exception {
        int s = cr.addStation("A", 0, 0);

        cr.addUnit(s, UnitType.AMBULANCE);
        cr.addUnit(s, UnitType.AMBULANCE);

        int i1 = cr.reportIncident(IncidentType.MEDICAL, 1, 0, 1);
        int i2 = cr.reportIncident(IncidentType.MEDICAL, 1, 0, 2);

        cr.dispatch();

        cr.tick();
        cr.tick();
        cr.tick();

        String status = cr.getStatus();

        assertTrue(status.indexOf("I#1") < status.indexOf("I#2"));
    }

    @Test
    void unitEventuallyBecomesIdleAfterResolution() throws Exception {
        int s = cr.addStation("A", 0, 0);
        int u = cr.addUnit(s, UnitType.AMBULANCE);

        int i = cr.reportIncident(IncidentType.MEDICAL, 1, 0, 1);

        cr.dispatch();

        cr.tick();
        cr.tick();
        cr.tick();

        assertTrue(cr.viewIncident(i).contains("STATUS=RESOLVED"));
        assertTrue(cr.viewUnit(u).contains("STATUS=IDLE"));
    }
}