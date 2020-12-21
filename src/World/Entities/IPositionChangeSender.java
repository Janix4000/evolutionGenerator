package World.Entities;

import World.ObserversInterfaces.IPositionChangeObserver;

public interface IPositionChangeSender<T> {
    void addPositionObserver(IPositionChangeObserver<T> observer);
    void removePositionObserver(IPositionChangeObserver<T> observer);
}
