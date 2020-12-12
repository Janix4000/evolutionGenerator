package World;

public interface IPositionChangeSender {
    void addObserver(IPositionChangeObserver observer);
    void removeObserver(IPositionChangeObserver observer);
    IWorldElement getWorldElement();
}
