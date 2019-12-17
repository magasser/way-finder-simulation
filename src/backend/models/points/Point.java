package backend.models.points;

import backend.models.Coordinate;
import javafx.scene.paint.Color;

public abstract class Point {

    protected final Coordinate loc;
    protected Color color;

    public Point(final long x, final long y) {
        this.loc = new Coordinate(x,y);
    }

    public Coordinate getLoc() {
        return loc;
    }
}
