package Utility;

public class Rectangle2d {
    public Vector2d position;
    public Vector2d size;

    public Rectangle2d(Vector2d position, Vector2d size) {
        this.position = position;
        this.size = size;
    }

    public boolean isIn(Vector2d pos) {
        return pos.follow(position) && pos.precedes(position.add(size));
    }
}
