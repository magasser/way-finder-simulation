package backend.models.playground;

import backend.models.entities.Ball;
import backend.models.entities.Direction;
import backend.models.entities.Entity;
import backend.models.obstacles.Obstacle;
import backend.models.points.End;
import backend.models.points.Start;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Playground {

    private List<Entity> entities;
    private List<Obstacle> obstacles;
    private final long sizeX;
    private final long sizeY;
    private Start start;
    private End end;

    private final int STEPS = 2000;
    private final int ENTITIES = 1000;
    private final int MOVE_AMOUNT = 2;

    private Entity best;

    private int generation;

    public Playground(final long sizeX, final long sizeY, final long startX, final long startY,
                      final long endX, final long endY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.start = new Start(startX, startY);
        this.end = new End(endX, endY);
        this.entities = new ArrayList<>();
        this.obstacles = new ArrayList<>();

        for(int i = 0; i < ENTITIES; i++) {
            addEntity(new Ball(start.getLoc().x, start.getLoc().y, Color.BLACK, STEPS));
        }

        generation = 1;
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

    public int getGeneration() {
        return generation;
    }

    public long sizeX() { return sizeX; }
    public long sizeY() { return sizeY; }

    public boolean addEntity(final Entity e) {
        return entities.add(e);
    }

    public boolean addObstacle(final Obstacle o) {
        return obstacles.add(o);
    }

    public boolean moveAll() {
        boolean done = true;
        for(Entity e : entities) {
            if(!e.isDead() && !e.isDone()) {
                e.move(MOVE_AMOUNT);
            }
            if(!e.isDone()) {
                done = false;
            }
        }

        return done;
    }

    public void respawnAll() {
        calculateFitness();
        best = null;
        Random r = new Random();
        for(Entity e : entities) {
            e.respawn();
            //e.setColor(Color.rgb(r.nextInt(200) + 56, r.nextInt(200) + 56, r.nextInt(200) + 56));
            e.setColor(Color.BLACK);
            e.setSize(2);
            if(best == null) {
                best = e;
            }

            if(e.getFitness() > best.getFitness()) {
                best = e;
            }
        }
        best.setColor(Color.GREEN);
        best.setSize(5);
        recalculatePaths();
        generation++;

    }

    public boolean checkCollision() {
        boolean done = true;
        for(Entity e : entities) {
            for(Obstacle o : obstacles) {
                if(o.doesCollideWith(e)) {
                    e.kill();
                }
            }
            if(!e.isDead() || !e.isDone()) {
                done = false;
            }

            if(Math.abs(e.getLoc().x - end.getLoc().x) < end.getRadius() &&
                Math.abs(e.getLoc().y - end.getLoc().y) < end.getRadius()) {
                e.finish();
            }

            if(e.getLoc().x == 0 || e.getLoc().x >= sizeX || e.getLoc().y == 0 || e.getLoc().y >= sizeY) {
                e.kill();
            }
        }

        return done;
    }

    private void calculateFitness() {
        for(Entity e : entities) {
            double distanceToEnd = Math.sqrt(
                    Math.pow(Math.abs(e.getLoc().x - end.getLoc().x), 2) +
                            Math.pow(Math.abs(e.getLoc().y - end.getLoc().y), 2));
            int obstaclesCleared = 0;
            for(Obstacle o : obstacles) {
                obstaclesCleared += o.hasClearedObstacle(e, end) ? 1 : 0;
            }

            double obstaclesSuccess = (double)obstaclesCleared / (double)obstacles.size();
            obstaclesSuccess *= 2;
            double distanceSuccess = 1 / (distanceToEnd / 100);
            if(e.isDead()) {
                e.setFitness(0);
            } else {
                e.setFitness((distanceSuccess + obstaclesSuccess) / 3);
            }
        }
    }

    private void recalculatePaths() {
        Random r = new Random();
        for(int i = 0; i < entities.size(); i++) {
            if(entities.get(i) == best) continue;
            Direction[] path = Arrays.copyOf(best.getPath(), best.getPath().length);
            for(int k = 0; k < path.length; k++) {
                if (0.7 < r.nextDouble()) {
                    path[k] = Direction.values()[r.nextInt(Direction.values().length)];
                }
            }
            entities.get(i).setPath(path);
        }
    }
}