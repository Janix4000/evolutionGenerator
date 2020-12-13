package World;

import Utility.Vector2d;

public interface IWorldBoundaries {
    Vector2d getProperNextPosition(Vector2d nextPosition);
    Vector2d getLowerLeft();
    Vector2d getUpperRight();
}
