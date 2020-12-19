package World;

import World.Entities.Animal;

public interface IDeathObserver<T> {
    void senderIsDead(T sender, int deathDay);
}
