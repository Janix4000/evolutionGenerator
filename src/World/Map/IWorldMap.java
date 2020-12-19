package World.Map;

import Utility.Vector2d;

public interface IWorldMap {
    Vector2d getProperNextPosition(Vector2d nextPosition);
    Vector2d getLowerLeft();
    Vector2d getUpperRight();
    Vector2d getSize();
    boolean isOccupied(Vector2d position);
}
