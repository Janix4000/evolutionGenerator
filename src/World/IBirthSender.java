package World;

public interface IBirthSender {
    void addObserver(IBirthObserver observer);
    void removeObserver(IBirthObserver observer);
}
