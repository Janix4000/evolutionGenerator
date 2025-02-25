package World;

import Utility.Config.IWorldConfig;
import Utility.CoordinateTransformer;
import Utility.Rectangle2d;
import Utility.Vector2d;
import World.WorldStatistics.IMapStatistics;
import World.WorldStatistics.JsonAverageStatisticsSaver;
import World.WorldStatistics.UI.WorldStatisticsUI;
import World.WorldStatistics.WorldStatistics;
import World.Entities.Animal;
import World.Map.WorldMap;
import World.Map.WorldMapCell;
import World.Systems.BreedingSystem;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.floor;
import static java.lang.StrictMath.round;

public class World implements IMapStatistics {
    private final List<Animal> animals = new ArrayList<>();
    private final WorldMap worldMap;
    private final CoordinateTransformer coordinateTransformer;
    private final BreedingSystem breedingSystem;
    private final PGraphics mapGraphics;
    private final Vector2d cellSize;
    private int day = 0;
    private int nGrasses = 0;
    IWorldConfig config;
    private final WorldStatistics statistics;
    private final WorldStatisticsUI statisticsUI;
    static private final int statisticsWidth = 250;
    private final Vector2d sceneSize;

    public World(PApplet ps, IWorldConfig config, Vector2d sceneSize) {
        worldMap = new WorldMap(config.getWorldMapConfig());
        this.sceneSize = sceneSize;
        this.config = config;

        Vector2d mapSize = getMapSize();
        coordinateTransformer = new CoordinateTransformer(worldMap, mapSize);
        breedingSystem = new BreedingSystem(worldMap);
        mapGraphics = ps.createGraphics(this.sceneSize.x, this.sceneSize.y);
        cellSize = new Vector2d(mapSize.x / worldMap.getSize().x, mapSize.y / worldMap.getSize().y);

        statistics = new WorldStatistics(this);
        statistics.addBirthSender(breedingSystem);
        statisticsUI = new WorldStatisticsUI(statistics);

        spawnFirstAnimals(config.getNStartingAnimals());
    }

    public void makeTick() {
        removeDeadAnimals();
        rotateAnimals();
        moveAnimals();
        feedAnimalsWithGrass();
        breedAnimals();
        spawnGrass();

        statistics.update();

        day++;
    }


    public void addAnimal(Vector2d pos) {
        Animal animal = new Animal(worldMap, pos);
        animal.setMaxEnergy(config.getAnimalsStartingEnergy());
        addAnimal(animal);
    }

    private void addAnimal(Animal animal) {
        animals.add(animal);
        worldMap.add(animal);
        statistics.addAnimal(animal);
        animal.setBirthDay(day);
    }

    private void spawnFirstAnimals(int n) {
        for (int i = 0; i < n; ++i) {
            var position = worldMap.getRandomFreePosition();
            if(position == null) {
                break;
            }
            addAnimal(worldMap.getRandomFreePosition());
        }
        for (int i = 0; i < n; ++i) {
            spawnGrass();
        }
    }

    private void spawnGrass() {
        nGrasses += worldMap.addGrassesIfPossible();
    }

    private void breedAnimals() {
        var children = breedingSystem.getChildren(worldMap.animalsIterator());
        for (var child : children) {
            addAnimal(child);
        }
    }

    private void feedAnimalsWithGrass() {
        var it = worldMap.animalsIterator();
        while(it.hasNext()) {
            var cell = it.next();
            if(cell.hasAnyAnimals() && cell.hasGrass()) {
                int grassEnergy = config.getGrassEnergy();
                var animals = cell.getBestAnimals();
                final int energy = grassEnergy / animals.size();
                animals.forEach(a -> a.addEnergy(energy));
                cell.removeGrass();
                nGrasses--;
            }
        }
    }

    private void moveAnimals() {
        animals.forEach(Animal::move);
        animals.forEach(a -> a.consumeEnergy(config.getEnergyCostOfMove()));
    }

    private void rotateAnimals() {
        animals.forEach(Animal::randomlyRotate);
    }

    private void removeDeadAnimals() {
        animals.stream().filter(Animal::hasNoEnergy).forEach(a -> a.kill(day));
        animals.removeIf(Animal::hasNoEnergy);
    }

    public PGraphics draw() {
        mapGraphics.beginDraw();

        mapGraphics.background(100);
        var mapSize = getMapSize();
        mapGraphics.fill(0, 200, 0);
        mapGraphics.rect(0, 0, mapSize.x, mapSize.y);
        drawJungle();
        drawTargetAnimal();
        drawWorldElements();
        drawAnimalsWithBestGenome();
        drawUI();
        mapGraphics.endDraw();
        return mapGraphics;
    }

    private void drawTargetAnimal() {
        if(statistics.hasNoTarget()) {
            return;
        }
        Animal target = statistics.getTarget();
        int c;
        if(target.hasNoEnergy()) {
            c = mapGraphics.color(125);
        } else {
            c = mapGraphics.color(255, 0, 0);
        }
        drawAnimalFrame(target, c);
    }

    private void drawAnimalsWithBestGenome() {
        var bestAnimals = statistics.getAnimalsWithBestGenomes();
        for(var animal : bestAnimals) {
            drawAnimalFrame(animal, mapGraphics.color(255, 255, 0));
        }
    }

    private void drawAnimalFrame(Animal animal, int color) {
        var pos = coordinateTransformer.toSceneCords(animal.getWorldPosition());

        mapGraphics.stroke(color);
        mapGraphics.strokeWeight(2);
        mapGraphics.noFill();
        mapGraphics.rect(pos.x, pos.y, cellSize.x, cellSize.y);
    }

    private Vector2d getMapSize() {
        var maxSpace = sceneSize.subtract(new Vector2d(statisticsWidth, 0));
        var size = config.getWorldMapConfig().getWorldSize();
        Vector2d finalSpace;
        if(maxSpace.x * size.y > maxSpace.y * size.x) {
            finalSpace = new Vector2d (size.x * maxSpace.y / size.y, maxSpace.y);
        } else {
            finalSpace = new Vector2d (maxSpace.x, size.y * maxSpace.x / size.x);
        }
        return finalSpace;
    }

    private void drawUI() {
        Vector2d pos = new Vector2d(getMapSize().x + 5, 0);
        statisticsUI.draw(mapGraphics, pos);
    }

    private void drawWorldElements() {
        mapGraphics.smooth();
        for (var cell : worldMap) {
            var representative = cell.getRepresentative();
            var pos = coordinateTransformer.toSceneCords(representative.getWorldPosition());
            representative.draw(mapGraphics, new Rectangle2d(pos, cellSize) );
        }
    }

    public float getAverageEnergy() {
        if(animals.isEmpty()) {
            return 0;
        }
        return (float) animals.stream().reduce(0, (s, a) -> s + a.getEnergy(), Integer::sum) / animals.size();
    }

    @Override
    public int getNGrasses() {
        return nGrasses;
    }

    @Override
    public int getNAnimals() {
        return animals.size();
    }

    private void drawJungle() {
        var box = worldMap.getJungleBox();
        var lt = coordinateTransformer.toSceneCords(box.position);
        var rb = coordinateTransformer.toSceneCords(box.position.add(box.size));
        mapGraphics.fill(0, 255, 0);
        mapGraphics.rect(lt.x, lt.y, rb.x - lt.x, rb.y - lt.y);
    }

    @Override
    public String getText() {
        int nAnimals = getNAnimals();
        int nGrasses = getNGrasses();
        float averageEnergy = getAverageEnergy();
        return  "There are " + nAnimals + " animals on the map\n" +
                "There are " + nGrasses + " grasses on the map\n" +
                "Average energy of animals: " + round(averageEnergy);
    }

    public void processMouseEvent(Vector2d mousePos) {
        var worldPos = coordinateTransformer.toWorldCords(mousePos);
        WorldMapCell cell = worldMap.getCell(worldPos);
        if(cell != null) {
            if(cell.hasAnyAnimals()) {
                var animal = cell.getTopAnimals(1).get(0);
                if(!statistics.hasNoTarget() && statistics.getTarget().isEqual(animal)) {
                    statistics.removeTarget();
                } else {
                    statistics.setTarget(animal);
                }
            }
        } else  {
            var target = statistics.getTarget();
            if(target != null && worldPos.equals(target.getWorldPosition())) {
                statistics.removeTarget();
            }
        }
    }

    public void saveStatistics(String fileName) throws IOException {
        JsonAverageStatisticsSaver.save(statistics, fileName);
    }
}
