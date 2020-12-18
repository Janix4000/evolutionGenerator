package World;

import Utility.CoordinateTransformer;
import Utility.Vector2d;
import World.Entities.Animal;
import World.Map.WorldMap;
import World.Systems.BreedingSystem;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class World {
    private final List<Animal> animals = new ArrayList<>();
    private final WorldMap worldMap = new WorldMap();
    private final Vector2d size = new Vector2d(800, 600);
    private final CoordinateTransformer coordinateTransformer = new CoordinateTransformer(worldMap, size);
    private final PGraphics graphics;
    private final Vector2d cellSize;


    public World(PApplet ps) {
        graphics = ps.createGraphics(size.x, size.y);
        cellSize = new Vector2d(size.x / worldMap.getSize().x, size.y / worldMap.getSize().y);
        spawnFirstAnimals(300);
    }

    public void makeTick() {
        removeDeadAnimals();
        rotateAnimals();
        moveAnimals();
        feedAnimalsWithGrass();
        breedAnimals();
        spawnGrass();
    }


    public void addAnimal(Vector2d pos) {
        Animal animal = new Animal(worldMap, pos);
        animal.setMaxEnergy(100);
        addAnimal(animal);
    }

    private void addAnimal(Animal animal) {
        animals.add(animal);
        worldMap.add(animal);
    }

    private void spawnFirstAnimals(int n) {
        for (int i = 0; i < n; ++i) {
            addAnimal(Vector2d.getRandom(worldMap.getLowerLeft(), worldMap.getUpperRight()));
        }
        for (int i = 0; i < n; ++i) {
            worldMap.addGrassesIfPossible();
        }
    }

    private void spawnGrass() {
        worldMap.addGrassesIfPossible();
    }

    private void breedAnimals() {
        BreedingSystem breedingSystem = new BreedingSystem(worldMap);
        var children = breedingSystem.getChildren(worldMap.iterator());
        for (var child : children) {
            addAnimal(child);
        }
    }

    private void feedAnimalsWithGrass() {
        for(var cell : worldMap) {
            if(cell.hasAnyAnimals() && cell.hasGrass()) {
                int grassEnergy = 10;
                var animals = cell.getBestAnimals();
                final int energy = grassEnergy / animals.size();
                animals.forEach(a -> a.addEnergy(energy));
                cell.removeGrass();
            }
        }
    }

    private void moveAnimals() {
        animals.forEach(Animal::move);
        animals.forEach(a -> a.consumeEnergy(1));
    }

    private void rotateAnimals() {
        animals.forEach(Animal::randomlyRotate);
    }

    private void removeDeadAnimals() {
        animals.stream().filter(Animal::hasNoEnergy).forEach(Animal::kill);
        animals.removeIf(Animal::hasNoEnergy);
    }

    public PGraphics draw() {
        graphics.beginDraw();

        graphics.background(0, 200, 0);
        drawJungle(graphics);
        drawWorldElements(graphics);

        graphics.endDraw();
        return graphics;
    }

    private void drawWorldElements(PGraphics graphics) {
        graphics.smooth();
        for (var cell : worldMap) {
            var representative = cell.getRepresentative();
            var pos = coordinateTransformer.toWorldCords(representative.getWorldPosition());
            int rx = cellSize.x;
            int ry = cellSize.y;
            representative.draw(graphics, new Utility.Rectangle(pos, new Vector2d(rx, ry)) );
        }
    }

    private void drawJungle(PGraphics graphics) {
        var box = worldMap.getJungleBox();
        var lt = coordinateTransformer.toWorldCords(box.position);
        var rb = coordinateTransformer.toWorldCords(box.position.add(box.size));
        graphics.fill(0, 255, 0);
        graphics.rect(lt.x, lt.y, rb.x - lt.x, rb.y - lt.y);
    }

}
