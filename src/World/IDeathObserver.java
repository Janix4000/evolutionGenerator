package World;

public interface IDeathObserver<T> {
    void senderIsDead(T sender, int deathDay);
}
