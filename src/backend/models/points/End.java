package backend.models.points;

import javafx.scene.paint.Color;

public class End extends Point {
    public End(final long x, final long y) {
        super(x, y);
        color = Color.RED;
    }
}
