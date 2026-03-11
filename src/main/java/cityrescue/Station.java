package cityrescue;

public class Station {

    private int id;
    private String name;

    private int x;
    private int y;

    private int capacity;
    private int unitCount;

    public Station(int id, String name, int x, int y) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.capacity = 10;
        this.unitCount = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getUnitCount() {
        return unitCount;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void addUnit() {
        unitCount++;
    }

    public void removeUnit() {
        unitCount--;
    }
}