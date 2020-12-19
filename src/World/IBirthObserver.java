package World;

import World.Entities.Animal;

public interface IBirthObserver {
    void hasBeenGivenBirth(Animal parent, Animal child);
}
