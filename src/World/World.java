package World;

import Utility.CoordinateTransformer;
import Utility.Vector2d;
import World.Entities.Animal;
import World.Map.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<Animal> animals = new ArrayList<>();
    private final WorldMap worldMap = new WorldMap();
    private final Vector2d size = new Vector2d(400, 300);
    private final CoordinateTransformer coordinateTransformer = new CoordinateTransformer(worldMap, size);


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
