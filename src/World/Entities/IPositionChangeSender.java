package World.Entities;

import World.IPositionChangeObserver;

public interface IPositionChangeSender<T> {
    void addPositionObserver(IPositionChangeObserver<T> observer);
    void removePositionObserver(IPositionChangeObserver<T> observer);
}
