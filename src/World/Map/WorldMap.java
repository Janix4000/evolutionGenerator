package World.Map;

import Utility.Config.IWorldMapConfig;
import Utility.Rectangle2d;
import Utility.Vector2d;
import World.Entities.Animal;
import World.Entities.Grass;
import World.ObserversInterfaces.IDeathObserver;
import World.ObserversInterfaces.IPositionChangeObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class WorldMap implements IPositionChangeObserver<Animal>, Iterable<WorldMapCell>, IDeathObserver<Animal>, IWorldMap, IRandomPositionGenerator {
    private final HashMap<Vector2d, WorldMapCell> cells = new HashMap<>();
    private final List<WorldMapCell> animalCells = new ArrayList<>();
    private final Vector2d size;
    private final JungleRegion jungleRegion;
    private final FreePositionsManager freePositionsManager;


    public WorldMap(IWorldMapConfig config) {
        size = config.getWorldSize();
        jungleRegion = new JungleRegion(config.getJungleSize(), this);
        freePositionsManager = new FreePositionsManager(this, size, jungleRegion);
    }

    public void add(Animal animal) {
        animal.addPositionObserver(this);
        animal.addIsDeadObserver(this);
        putInProperCell(animal);
    }

    public void remove(Animal animal) {
        animal.removePositionObserver(this);
        animal.removeIsDeadObserver(this);
        removeFromProperCell(animal);
    }

    private int addGrassOutsideJungle() {
        Vector2d notInJunglePos = freePositionsManager.getRandomFreePositionOutsideJungle();
        if(notInJunglePos == null) {
            return 0;
        }
        Grass grass = new Grass();
        grass.setPosition(notInJunglePos);
        putInProperCell(grass);
        return 1;
    }

    private int addGrassInsideJungle() {
        Vector2d inJunglePos = freePositionsManager.getRandomFreePositionInJungle();
        if(inJunglePos == null) {
            return 0;
        }
        Grass grass = new Grass();
        grass.setPosition(inJunglePos);
        putInProperCell(grass);
        return 1;
    }

    public int addGrassesIfPossible() {
        return addGrassOutsideJungle() + addGrassInsideJungle();
    }

    private void removeFromProperCell(Animal animal) {
        removeFromProperCell(animal, animal.getWorldPosition());
    }
    private void removeFromProperCell(Animal animal, Vector2d position) {
        var cell = cells.get(position);
        cell.remove(animal);
        if(!cell.hasAnyAnimals()) {
            animalCells.remove(cell);
        }
        if(cell.isEmpty()) {
            cells.remove(position);
            freePositionsManager.addFreePosition(position);
        }
    }

    private void removeFromProperCell(Grass grass) {
        var pos = grass.getWorldPosition();
        var cell = cells.get(pos);
        cell.removeGrass();
        if(cell.isEmpty()) {
            cells.remove(pos);
            freePositionsManager.addFreePosition(pos);
        }
    }

    private void putInProperCell(Animal animal) {
        var pos = animal.getWorldPosition();
        if(cells.containsKey(pos)) {
            var cell = cells.get(pos);
            if(!cell.hasAnyAnimals()) {
                animalCells.add(cell);
            }
            cell.add(animal);
        } else {
            var cell =  new WorldMapCell();
            cells.put(pos,cell);
            cell.add(animal);
            freePositionsManager.removeFreePosition(pos);
            animalCells.add(cell);
        }
    }
    private void putInProperCell(Grass grass) {
        var pos = grass.getWorldPosition();
        if(cells.containsKey(pos)) {
            cells.get(pos).addGrass(grass);
        } else {
            var cell =  new WorldMapCell();
            cells.put(pos,cell);
            cell.addGrass(grass);
            freePositionsManager.removeFreePosition(pos);
        }
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal sender) {
        removeFromProperCell(sender, oldPosition);
        putInProperCell(sender);
    }

    @Override
    public Iterator<WorldMapCell> iterator() {
        return cells.values().iterator();
    }

    public Iterator<WorldMapCell> animalsIterator() {
        return animalCells.iterator();
    }

    @Override
    public void senderIsDead(Animal animal, int deathDay) {
        removeFromProperCell(animal);
    }

    @Override
    public Vector2d getProperNextPosition(Vector2d nextPosition) {
        int x = (nextPosition.x % size.x + size.x) % size.x;
        int y = (nextPosition.y % size.y + size.y) % size.y;
        return new Vector2d(x, y);
    }

    @Override
    public Vector2d getLowerLeft() {
        return new Vector2d(0, 0);
    }

    @Override
    public Vector2d getUpperRight() {
        return size;
    }

    @Override
    public Vector2d getSize() {
        return getUpperRight().subtract(getLowerLeft());
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return cells.containsKey(position) && !cells.get(position).isEmpty();
    }

    @Override
    public Vector2d getRandomValidPosition() {
        Vector2d res;
        do {
            res = Vector2d.getRandom(getLowerLeft(), getUpperRight());
        } while(jungleRegion.isInJungle(res));
        return res;
    }

    public Vector2d getRandomFreePosition() {
        Vector2d res = freePositionsManager.getRandomFreePositionInJungle();
        if(res == null) {
            res = freePositionsManager.getRandomFreePositionOutsideJungle();
        }
        return res;
    }

    public Rectangle2d getJungleBox() {
        return new Rectangle2d(
                jungleRegion.getLowerLeftJunglePos(),
                jungleRegion.getUpperRightJunglePos().subtract(jungleRegion.getLowerLeftJunglePos())
        );
    }

    public WorldMapCell getCell(Vector2d worldPos) {
        return cells.getOrDefault(worldPos, null);
    }
}
