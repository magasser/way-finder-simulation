package backend.models.obstacles;

import backend.models.Coordinate;
import backend.models.entities.Entity;

public class Cuboid extends Obstacle {

    public Cuboid(final long locX, final long locY, final int sizeX, final int sizeY) {
        super(locX, locY, sizeX, sizeY);
    }

    @Override
    public boolean doesCollideWith(final Entity entity) {
        Coordinate coordsE = entity.getLoc();

        // size is currently ignored

        return coordsE.x > loc.x && coordsE.x < loc.x + sizeX &&
            coordsE.y > loc.y && coordsE.y < loc.y + sizeY;


    }
}
