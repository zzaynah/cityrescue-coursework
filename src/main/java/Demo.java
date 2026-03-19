import cityrescue.*;
import cityrescue.enums.*;

public class Demo {
    public static void main(String[] args) throws Exception {
        CityRescue cr = new CityRescueImpl();

        //Sets up the grid size as width 5 and height 5
        cr.initialise(5, 5);

        //Station named "Central" is added in the centre
        int s1 = cr.addStation("Central", 0, 0);
        
        //The ambulance unit is added onto the central station
        int u1 = cr.addUnit(s1, UnitType.AMBULANCE);

        //Sets up an obstacle
        cr.addObstacle(0, 1);

        //Medical incident with severity 1 is reported
        int i1 = cr.reportIncident(IncidentType.MEDICAL, 1, 0, 2);

        System.out.println("=== BEFORE DISPATCH ===");
        System.out.println(cr.getStatus());

        //Unit which handles medical incident is dispatched
        cr.dispatch();

        System.out.println("\n=== AFTER DISPATCH ===");
        System.out.println(cr.getStatus());

        //Gets the status of the unit after first tick
        cr.tick();
        System.out.println("\n=== AFTER TICK 1 ===");
        System.out.println(cr.getStatus());

        //Gets the status of the unit after second tick
        cr.tick();
        System.out.println("\n=== AFTER TICK 2 ===");
        System.out.println(cr.getStatus());

        //Gets the status of the unit after third tick which should be resolved as it is a medical unit
        cr.tick();
        System.out.println("\n=== AFTER TICK 3 ===");
        System.out.println(cr.getStatus());
    }
}