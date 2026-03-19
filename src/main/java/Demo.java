import cityrescue.*;
import cityrescue.enums.*;

public class Demo {
    public static void main(String[] args) throws Exception {
        CityRescue cr = new CityRescueImpl();

        cr.initialise(5, 5);

        int s1 = cr.addStation("Central", 0, 0);
        int u1 = cr.addUnit(s1, UnitType.AMBULANCE);

        cr.addObstacle(0, 1);

        int i1 = cr.reportIncident(IncidentType.MEDICAL, 1, 0, 2);

        System.out.println("=== BEFORE DISPATCH ===");
        System.out.println(cr.getStatus());

        cr.dispatch();

        System.out.println("\n=== AFTER DISPATCH ===");
        System.out.println(cr.getStatus());

        cr.tick();
        System.out.println("\n=== AFTER TICK 1 ===");
        System.out.println(cr.getStatus());

        cr.tick();
        System.out.println("\n=== AFTER TICK 2 ===");
        System.out.println(cr.getStatus());


        cr.tick();
        System.out.println("\n=== AFTER TICK 3 ===");
        System.out.println(cr.getStatus());
    }
}