package backend.models.points;

import backend.models.Coordinate;
import javafx.scene.paint.Color;

public abstract class Point {

    protected final Coordinate loc;
    protected Color color;
    protected int radius;

    public Point(final long x, final long y) {
        this.loc = new Coordinate(x,y);
        this.radius = 10;
    }

    public Coordinate getLoc() {
        return loc;
    }

    public int getRadius() {
        return radius;
    }
}
