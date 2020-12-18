package World;

import Utility.CoordinateTransformer;
import Utility.Vector2d;
import World.Entities.Animal;
import World.Map.WorldMap;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<Animal> animals = new ArrayList<>();
    private final WorldMap worldMap = new WorldMap();
    private final Vector2d size = new Vector2d(400, 300);
    private final CoordinateTransformer coordinateTransformer = new CoordinateTransformer(worldMap, size);
    private final PGraphics graphics;
    private final Vector2d cellSize;


    private int back = 0;
    public World(PApplet ps) {
        graphics = ps.createGraphics(size.x, size.y);
        cellSize = new Vector2d(size.x / worldMap.getSize().x, size.y / worldMap.getSize().y);
        spawnFirstAnimals(4);
    }

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
        animals.stream().filter(Animal::hasNoEnergy).forEach(Animal::kill);
        animals.removeIf(Animal::hasNoEnergy);
    }

    public PGraphics draw() {
        graphics.beginDraw();
        graphics.background(back);
        graphics.fill(0,back, 0);
        back = (back + 1) % 256;
        for(var animal : animals) {
            var pos = coordinateTransformer.toWorldCords(animal.getPosition());
            int rx = cellSize.x;
            int ry = cellSize.y;
            graphics.ellipse(pos.x, pos.y, rx, ry);
        }
        graphics.endDraw();
        return graphics;
    }

    private void addAnimal(Animal animal) {
        animals.add(animal);
        worldMap.add(animal);
    }

    private void spawnFirstAnimals(int n) {
        for (int i = 0; i < n; ++i) {
            Animal animal = new Animal(worldMap, Vector2d.getRandom(worldMap.getLowerLeft(), worldMap.getUpperRight()));
            addAnimal(animal);
        }
    }
}
