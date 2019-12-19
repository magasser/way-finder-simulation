package backend.models;

public class Coordinate {

    public long x;
    public long y;

    public Coordinate(final long x, final long y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(obj == null) {
            return false;
        }

        Coordinate c = (Coordinate)obj;

        return this.x == c.x && this.y == c.y;
    }
}
