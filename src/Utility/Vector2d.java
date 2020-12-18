package Utility;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean precedes(Vector2d other) {
        return x < other.x && y < other.y;
    }

    public boolean follow(Vector2d other) {
        return x >= other.x && y >= other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    static public Vector2d getRandom(int leftBound, int rightBound) {
        int x = ThreadLocalRandom.current().nextInt(leftBound, rightBound);
        int y = ThreadLocalRandom.current().nextInt(leftBound, rightBound);
        return new Vector2d(x, y);
    }

    static public Vector2d getRandom(Vector2d lowerLeft, Vector2d upperRight) {
        int x = ThreadLocalRandom.current().nextInt(lowerLeft.x, upperRight.x);
        int y = ThreadLocalRandom.current().nextInt(lowerLeft.y, upperRight.y);
        return new Vector2d(x, y);
    }

    public Vector2d upperRight(Vector2d other) {
        int x = Math.max(this.x, other.x);
        int y = Math.max(this.y, other.y);
        return new Vector2d(x, y);
    }

    public Vector2d lowerLeft(Vector2d other) {
        int x = Math.min(this.x, other.x);
        int y = Math.min(this.y, other.y);
        return new Vector2d(x, y);
    }

    public Vector2d add(Vector2d other) {
        int x = this.x + other.x;
        int y = this.y + other.y;
        return new Vector2d(x, y);
    }

    public Vector2d subtract(Vector2d other) {
        int x = this.x - other.x;
        int y = this.y - other.y;
        return new Vector2d(x, y);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vector2d)) {
            return false;
        }
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

    public Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }

    public Vector2d mult(int m) {
        int x = this.x * m;
        int y = this.y * m;
        return new Vector2d(x, y);
    }

    public Vector2d div(int m) {
        int x = this.x / m;
        int y = this.y / m;
        return new Vector2d(x, y);
    }
}

