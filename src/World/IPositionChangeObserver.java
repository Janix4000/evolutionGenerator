package World;

import Utility.Vector2d;

public interface IPositionChangeObserver <T> {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, T sender);
}
