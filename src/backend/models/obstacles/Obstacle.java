package backend.models.obstacles;

import backend.models.Coordinate;
import backend.models.entities.Entity;

public abstract class Obstacle {

    protected final Coordinate loc;
    protected final int sizeX;
    protected final int sizeY;

    public Obstacle(final long locX, final long locY, final int sizeX, final int sizeY) {
        this.loc = new Coordinate(locX, locY);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public Coordinate getLoc() {
        return loc;
    }

    public int sizeX() { return sizeX; }
    public int sizeY() { return sizeY; }

    public void setSize(final int x, final int y) {}
    public boolean doesCollideWith(final Entity entity) { return false; }
}
