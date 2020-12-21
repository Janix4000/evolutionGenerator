package World.ObserversInterfaces;

import World.Entities.Animal;
import World.World;

public interface IBirthObserver {
    void hasBeenGivenBirth(Animal parent, Animal child);
}
