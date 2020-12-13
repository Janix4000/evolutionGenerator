package World;

public interface IPositionChangeSender<T> {
    void addPositionObserver(IPositionChangeObserver<T> observer);
    void removePositionObserver(IPositionChangeObserver<T> observer);
    IWorldElement getWorldElement();
}
