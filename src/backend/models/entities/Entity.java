package backend.models.entities;

import backend.models.Coordinate;
import javafx.scene.paint.Color;

import java.util.Random;

public abstract class Entity {

    protected Coordinate loc;
    protected Coordinate startLoc;
    protected Color color;
    protected int size;
    protected boolean isDead;
    protected boolean isDone;
    protected Direction[] path;
    protected int stepIndex;
    protected double fitness;

    public Entity(final long x, final long y, final Color color, final int steps) {
        this.loc = new Coordinate(x,y);
        this.startLoc = new Coordinate(x,y);
        this.color = color;
        this.size = 2;
        this.isDead = false;
        this.path = new Direction[steps];
        Random r = new Random();
        for(int i = 0; i < steps; i++) {
            path[i] = Direction.values()[r.nextInt(Direction.values().length)];
        }
        this.stepIndex = 0;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public Direction[] getPath() {
        return path;
    }

    public void setPath(final Direction[] path) {
        this.path = path;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
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
        isDone = true;
    }

    public boolean isDead() {
        return isDead;
    }
    public boolean isDone() { return isDone; }

    public void finish() {
        isDone = true;
    }

    public void respawn() {
        isDead = false;
        isDone = false;
        stepIndex = 0;
        loc = new Coordinate(startLoc.x, startLoc.y);
    }

    public void move(final long amount) {
        switch (path[stepIndex]) {
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
                throw new IllegalArgumentException(String.format("Invalid direction: %s", path[stepIndex]));
        }
        stepIndex++;
        if(stepIndex == path.length) {
            isDone = true;
        }
    }
}
