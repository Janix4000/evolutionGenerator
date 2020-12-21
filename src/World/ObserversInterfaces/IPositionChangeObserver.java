package World.ObserversInterfaces;

import Utility.Vector2d;
import World.World;

public interface IPositionChangeObserver <T> {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, T sender);
}
