package backend.models.entities;

import backend.models.Coordinate;
import javafx.scene.paint.Color;

public abstract class Entity {

    protected Coordinate loc;
    protected Color color;
    protected int size;
    protected boolean isDead;

    public Entity(final long x, final long y, final Color color) {
        this.loc = new Coordinate(x,y);
        this.color = color;
        this.size = 4;
        this.isDead = false;
    }

    public Coordinate getLoc() {
        return loc;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getColor() {
        return String.format("#%s", color.toString().substring(2));
    }

    public int getSize() {
        return size;
    }

    public void kill() {
        isDead = true;
    }

    public boolean isDead() {
        return isDead;
    }

    public void move(final Direction direction, final long amount) {
        switch (direction) {
            case UP:
                loc.y -= amount;
                break;
            case RIGHT:
                loc.x += amount;
                break;
            case DOWN:
                loc.y += amount;
                break;
            case LEFT:
                loc.x -= amount;
                break;
            default:
                throw new IllegalArgumentException(String.format("Invalid direction: %s", direction));
        }
    }
    
}
