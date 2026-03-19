import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import cityrescue.*;
import cityrescue.enums.*;
import cityrescue.exceptions.*;

public class CustomExceptionTest {
    private CityRescue cr;

    @BeforeEach
    void setUp() throws Exception {
        cr = new CityRescueImpl();
        cr.initialise(5, 5);
    }

    @Test
    void invalidStationId_doesNotChangeState() {
        String before = cr.getStatus();

        assertThrows(IDNotRecognisedException.class, () -> {
            cr.removeStation(999);
        });

        assertEquals(before, cr.getStatus());
    }

    @Test
    void escalateCancelledIncident_throws() throws Exception {
        int i = cr.reportIncident(IncidentType.FIRE, 2, 1, 1);

        cr.cancelIncident(i);

        assertThrows(IllegalStateException.class, () -> {
            cr.escalateIncident(i, 4);
        });
    }

    @Test
    void cancelDispatchedIncident_releasesUnit() throws Exception {
        int s = cr.addStation("A", 0, 0);
        int u = cr.addUnit(s, UnitType.AMBULANCE);

        int i = cr.reportIncident(IncidentType.MEDICAL, 1, 2, 2);

        cr.dispatch();
        cr.cancelIncident(i);

        assertTrue(cr.viewIncident(i).contains("STATUS=CANCELLED"));
        assertTrue(cr.viewUnit(u).contains("STATUS=IDLE"));
    }
}