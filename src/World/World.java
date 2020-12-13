package World;

import Utility.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<Animal> animals = new ArrayList<>();
    private final WorldMap map = new WorldMap(new Vector2d(10 ,10));
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
        animals.forEach(animal -> animal.move());
    }

    private void rotateAnimals() {
        // animals.forEach(animal -> animal.makeRotationMove());
    }

    private void removeDeadAnimals() {
        // animals.stream().filter(animal -> animal.hasNoEnergy()).forEach(animal -> animal.kill());
        animals.removeIf(animal -> animal.hasNoEnergy());
    }
}
