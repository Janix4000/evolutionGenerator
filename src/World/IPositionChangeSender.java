package World;

public interface IPositionChangeSender {
    void addPositionObserver(IPositionChangeObserver observer);
    void removePositionObserver(IPositionChangeObserver observer);
    IWorldElement getWorldElement();
}
