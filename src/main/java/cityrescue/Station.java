package cityrescue;

/**
 * Station.java symbolises a rescue station within the city. Each station has a unique ID, a name,
 * a location as a coordinate and a capacity to determine how many units can be stores within the stations.
 *
 * This class manages the unit capacity and may remove or add units accordingly.
 */
public class Station {

    private int id;
    private String name;

    private int x;
    private int y;

    private int capacity;
    private int unitCount;

    /**
     * Constructs a station used to store units.
     * Sets the default capacity to 10 and begins with no units.
     *
     * @param id this is the unique identifier for the station
     * @param name this is the name of the station
     * @param x this is the x coordinate for the location of the station
     * @param y this is the y coordinate for the location of the station
     */
    public Station(int id, String name, int x, int y) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.capacity = 10;
        this.unitCount = 0;
    }

    /**
     * Returns the identifier for the station
     *
     * @return station ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the station
     *
     * @return name of the station as a string
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the x coordinate of the station's location
     *
     * @return x coordinate of the station's location
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y coordinate of the station's location
     *
     * @return y coordinate of the station's location
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the capacity of the station.
     *
     * @return capacity of the station
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns current number of units assigned to that station.
     *
     * @return number of units present in the station
     */
    public int getUnitCount() {
        return unitCount;
    }

    /**
     * Sets a capacity to the station to determine how many units can be stored in that station.
     *
     * @param capacity the new capacity of that station
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Adds a single unit to the station, increments unit count by 1.
     * Occurs when a unit is assigned to that particular station.
     */
    public void addUnit() {
        unitCount++;
    }

    /**
     * Removes a single unit from the station, decreasing the unit count by 1.
     * Occurs when a unit is moved away from the station to a potential incident.
     */
    public void removeUnit() {
        unitCount--;
    }
}