package backend.models.obstacles;

import backend.models.Coordinate;
import backend.models.entities.Entity;
import backend.models.points.End;
import backend.models.points.Point;

public abstract class Obstacle {

    protected final Coordinate loc;
    protected final int sizeX;
    protected final int sizeY;
    protected final double angleToNorth;

    public Obstacle(final long locX, final long locY, final int sizeX, final int sizeY, final End end) {
        this.loc = new Coordinate(locX, locY);
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        double shortestDistanceToEnd = Double.MAX_VALUE;
        Coordinate shortest = null;
        for(int i = 0; i < sizeX ; i++) {
            for(int k = 0; k < sizeY; k++) {
                double distanceToEnd = Math.sqrt(
                        Math.pow(locX + i - end.getLoc().x, 2) + Math.pow(locY + k - end.getLoc().y, 2));
                if(distanceToEnd < shortestDistanceToEnd) {
                    shortest = new Coordinate(locX + i, locY + k);
                }
            }
        }

        assert shortest != null;
        angleToNorth = Math.asin(Math.abs(shortest.x - end.getLoc().x) / shortestDistanceToEnd);
    }

    public Coordinate getLoc() {
        return loc;
    }

    public int sizeX() { return sizeX; }
    public int sizeY() { return sizeY; }

    public void setSize(final int x, final int y) {}
    public boolean doesCollideWith(final Entity entity) { return false; }
    public boolean hasClearedObstacle(final Entity e, final Point end) {
        return Math.abs(e.getLoc().y - end.getLoc().y) < Math.abs(loc.y - end.getLoc().y);
    }
}
