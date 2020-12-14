package Utility;

public enum MapDirection {
    S("South", new Vector2d(0, -1)),
    SW("SouthWest", new Vector2d(-1, -1)),
    W("West", new Vector2d(-1, 0)),
    NW("NorthWest", new Vector2d(-1, 1)),
    N("North", new Vector2d(0, 1)),
    NE("NorthEast", new Vector2d(1, 1)),
    E("East", new Vector2d(1, 0)),
    SE("SouthEast", new Vector2d(1, -1));
    private final String label;
    private final Vector2d unit;

    MapDirection(String label, Vector2d unit) {
        this.label = label;
        this.unit = unit;
    }

    public MapDirection next(int n) {
        int size = values().length;
        n %= size;
        return values()[(this.ordinal() - n + size) % size];
    }
    public MapDirection next() {
        return next(1);
    }
    public MapDirection previous(int n) {
        return next(-n);
    }
    public MapDirection previous() {
        return previous(1);
    }


    public Vector2d toUnitVector() {
        return this.unit;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
