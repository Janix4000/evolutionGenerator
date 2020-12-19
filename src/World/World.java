package World;

import Utility.Config.IWorldConfig;
import Utility.CoordinateTransformer;
import Utility.Vector2d;
import World.AnimalStatistics.IMapStatistics;
import World.AnimalStatistics.UI.WorldStatisticsUI;
import World.AnimalStatistics.WorldStatistics;
import World.Entities.Animal;
import World.Map.WorldMap;
import World.Systems.BreedingSystem;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.ArrayList;
import java.util.List;

public class World implements IMapStatistics {
    private final List<Animal> animals = new ArrayList<>();
    private final WorldMap worldMap;
    private final CoordinateTransformer coordinateTransformer;
    private final BreedingSystem breedingSystem;
    private final PGraphics mapGraphics;
    private final PApplet ps;
    private final Vector2d cellSize;
    private int day = 0;
    private int nGrasses = 0;
    IWorldConfig config;
    private final WorldStatistics statistics;
    private final WorldStatisticsUI statisticsUI;
    static private final int statisticsWidth = 200;
    private final Vector2d sceneSize;

    public World(PApplet ps, IWorldConfig config, Vector2d sceneSize) {
        this.ps = ps;
        worldMap = new WorldMap(config.getWorldMapConfig());
        this.sceneSize = sceneSize;
        Vector2d mapSize = getMapSize();
        coordinateTransformer = new CoordinateTransformer(worldMap, mapSize);
        breedingSystem = new BreedingSystem(worldMap);
        mapGraphics = ps.createGraphics(this.sceneSize.x, this.sceneSize.y);
        cellSize = new Vector2d(mapSize.x / worldMap.getSize().x, mapSize.y / worldMap.getSize().y);
        this.config = config;
        statistics = new WorldStatistics(this);
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
            addAnimal(Vector2d.getRandom(worldMap.getLowerLeft(), worldMap.getUpperRight()));
        }
        for (int i = 0; i < n; ++i) {
            worldMap.addGrassesIfPossible();
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
        drawJungle(mapGraphics);
        drawWorldElements(mapGraphics);
        drawUI();
        mapGraphics.endDraw();



        return mapGraphics;
    }

    private Vector2d getMapSize() {
        return sceneSize.subtract(new Vector2d(statisticsWidth, 0));
    }

    private void drawUI() {
        Vector2d pos = new Vector2d(getMapSize().x, 0);
        statisticsUI.draw(mapGraphics, pos);
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

    @Override
    public String getText() {
        int nAnimals = getNAnimals();
        int nGrasses = getNGrasses();
        float averageEnergy = getAverageEnergy();
        return  "There are " + nAnimals + " animals on the map\n" +
                "There are " + nGrasses + " grasses on the map\n" +
                "Average energy of animals: " + averageEnergy;
    }
}
