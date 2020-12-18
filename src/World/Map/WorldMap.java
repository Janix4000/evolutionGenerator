package World.Map;

import Utility.Vector2d;
import World.Entities.Animal;
import World.Entities.Grass;
import World.IDeathObserver;
import World.IPositionChangeObserver;

import java.util.HashMap;
import java.util.Iterator;

public class WorldMap implements IPositionChangeObserver<Animal>, Iterable<WorldMapCell>, IDeathObserver<Animal>, IWorldMap, IRandomPositionGenerator {
    private final HashMap<Vector2d, WorldMapCell> cells = new HashMap<>();
    private final Vector2d size;
    private final JungleRegion jungleRegion;
    private final FreePositionsManager freePositionsManager;

    public WorldMap() {
        size = new Vector2d(12, 12);
        jungleRegion = new JungleRegion(size.div(3), this);
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

    private void addGrassOutsideJungle() {
        Vector2d notInJunglePos = freePositionsManager.getRandomFreePositionOutsideJungle();
        if(notInJunglePos == null) {
            return;
        }
        Grass grass = new Grass();
        grass.setPosition(notInJunglePos);
        putInProperCell(grass);
    }

    private void addGrassInsideJungle() {
        Vector2d inJunglePos = freePositionsManager.getRandomFreePositionInJungle();
        if(inJunglePos == null) {
            return;
        }
        Grass grass = new Grass();
        grass.setPosition(inJunglePos);
        putInProperCell(grass);
    }

    public void addGrassesIfPossible() {
        addGrassOutsideJungle();
        addGrassInsideJungle();
    }

    private void removeFromProperCell(Animal animal) {
        var pos = animal.getPosition();
        var cell = cells.get(pos);
        cell.remove(animal);
        if(cell.isEmpty()) {
            cells.remove(pos);
            freePositionsManager.addFreePosition(pos);
        }
    }

    private void removeFromProperCell(Grass grass) {
        var pos = grass.getPosition();
        var cell = cells.get(pos);
        cell.removeGrass();
        if(cell.isEmpty()) {
            cells.remove(pos);
            freePositionsManager.addFreePosition(pos);
        }
    }

    private void putInProperCell(Animal animal) {
        var pos = animal.getPosition();
        if(cells.containsKey(pos)) {
            cells.get(pos).add(animal);
        } else {
            var cell =  new WorldMapCell();
            cells.put(pos,cell);
            cell.add(animal);
            freePositionsManager.removeFreePosition(pos);
        }
    }
    private void putInProperCell(Grass grass) {
        var pos = grass.getPosition();
        if(cells.containsKey(pos)) {
            cells.get(pos).addGrass(grass);
        } else {
            var cell = cells.put(pos, new WorldMapCell());
            assert cell != null;
            cell.addGrass(grass);
            freePositionsManager.removeFreePosition(pos);
        }
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal sender) {
        var oldCell = cells.get(oldPosition);
        oldCell.remove(sender);
        putInProperCell(sender);
    }

    @Override
    public Iterator<WorldMapCell> iterator() {
        return cells.values().iterator();
    }


    @Override
    public void senderIsDead(Animal animal) {
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
}
