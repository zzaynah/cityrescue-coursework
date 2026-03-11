package cityrescue;

public class CityMap {

    private int width;
    private int height;
    private boolean[][] blocked;

    public CityMap(int width, int height) {
        this.width = width;
        this.height = height;
        blocked = new boolean[width][height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean isBlocked(int x, int y) {
        return blocked[x][y];
    }

    public void addObstacle(int x, int y) {
        blocked[x][y] = true;
    }

    public void removeObstacle(int x, int y) {
        blocked[x][y] = false;
    }
}