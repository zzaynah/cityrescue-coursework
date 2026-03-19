package cityrescue;

/**
 * CityMap.java represents the grid of the city, within boundaries and the respective obstacles when found.
 * It manages the layout of the city where locations, as coordinates, are validated for incidents/units/stations.
 * Provides the interface for units to respond to incidents.
 *
 * This class manages the map's size, boundaries, adds/removes obstacles and checks if a location is blocked.
 */
public class CityMap {

    private int width;
    private int height;
    private boolean[][] blocked;

    /**
     * Constructs a city map with specified width and height.
     * Initializes the grid by setting all location as empty.
     *
     * @param width width of the city grid
     * @param height height of the city grid
     */
    public CityMap(int width, int height) {
        this.width = width;
        this.height = height;
        blocked = new boolean[width][height];
    }

    /**
     * Returns the width of the city grid.
     *
     * @return the width of the city grid
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the city grid.
     *
     * @return the height of the city grid
     */
    public int getHeight() {
        return height;
    }

    /**
     * Checks if a set of coordinates is within the bounds of the city grid.
     *
     * @param x the x coordinate to be validated
     * @param y the y coordinate to be validated
     * @return true if coordinates are within the bounds, false otherwise
     */
    public boolean inBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Checks if a set of coordinates is blocked by an obstacle.
     *
     * @param x the x coordinate to be validated
     * @param y the y coordinate to be validated
     * @return true if the coordinates are blocked, false if the coordinates are not blocked
     */
    public boolean isBlocked(int x, int y) {
        return blocked[x][y];
    }

    /**
     * Adds an obstacle for a selected set of coordinates, which sets the location as blocked.
     *
     * @param x the x coordinate where the obstacle would be placed in
     * @param y the y coordinated where the obstacle would be placed in
     */
    public void addObstacle(int x, int y) {
        blocked[x][y] = true;
    }

    /**
     * Removes an obstacle for a selected set of coordinates, which sets the location as unblocked.
     *
     * @param x the x coordinate where the obstacle would be removed in
     * @param y the y coordinate where the obstacle would be removed in
     */
    public void removeObstacle(int x, int y) {
        blocked[x][y] = false;
    }
}