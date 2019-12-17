package backend.models.playground;

import backend.models.entities.Entity;
import backend.models.obstacles.Obstacle;
import backend.models.points.End;
import backend.models.points.Point;
import backend.models.points.Start;

import java.util.ArrayList;
import java.util.List;

public class Playground {

    private List<Entity> entities;
    private List<Obstacle> obstacles;
    private final long sizeX;
    private final long sizeY;
    private Start start;
    private End end;

    public Playground(final long sizeX, final long sizeY, final long startX, final long startY,
                      final long endX, final long endY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.start = new Start(startX, startY);
        this.end = new End(endX, endY);
        this.entities = new ArrayList<>();
        this.obstacles = new ArrayList<>();
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public Start getStart() {
        return start;
    }

    public End getEnd() {
        return end;
    }

    public long sizeX() { return sizeX; }
    public long sizeY() { return sizeY; }

    public boolean addEntity(final Entity e) {
        return entities.add(e);
    }

    public boolean addObstacle(final Obstacle o) {
        return obstacles.add(o);
    }
}
