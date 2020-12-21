package World.ObserversInterfaces;

import World.World;

public interface IDeathObserver<T> {
    void senderIsDead(T sender, int deathDay);
}
