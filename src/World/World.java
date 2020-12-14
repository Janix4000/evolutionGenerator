package World;

import World.Entities.Animal;
import World.Map.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<Animal> animals = new ArrayList<>();
    private final WorldMap worldMap = new WorldMap();
    public void makeTick() {
        removeDeadAnimals();
        rotateAnimals();
        moveAnimals();
        feedAnimalsWithGrass();
        breedAnimals();
        spawnGrass();
    }

    private void spawnGrass() {

    }

    private void breedAnimals() {

    }

    private void feedAnimalsWithGrass() {
    }

    private void moveAnimals() {
        animals.forEach(Animal::move);
    }

    private void rotateAnimals() {
        // animals.forEach(animal -> animal.makeRotationMove());
    }

    private void removeDeadAnimals() {
        // animals.stream().filter(animal -> animal.hasNoEnergy()).forEach(animal -> animal.kill());
        animals.removeIf(Animal::hasNoEnergy);
    }
}
