package World;

import Utility.Vector2d;

import java.util.HashMap;
import java.util.Iterator;

public class AnimalMap implements IPositionChangeObserver<Animal>, Iterable<AnimalCell>, IAnimalDeathObserver {
    private final HashMap<Vector2d, AnimalCell> cells = new HashMap<>();

    AnimalMap() {
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

    private void removeFromProperCell(Animal animal) {
        var pos = animal.getPosition();
        var cell = cells.get(pos);
        cell.remove(animal);
        if(cell.size() == 0) {
            cells.remove(pos);
        }
    }

    private void putInProperCell(Animal animal) {
        var pos = animal.getPosition();
        if(cells.containsKey(pos)) {
            cells.get(pos).add(animal);
        } else {
            var cell = cells.put(pos, new AnimalCell());
            assert cell != null;
            cell.add(animal);
        }
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal sender) {
        var oldCell = cells.get(oldPosition);
        oldCell.remove(sender);
        putInProperCell(sender);
    }

    @Override
    public Iterator<AnimalCell> iterator() {
        return cells.values().iterator();
    }


    @Override
    public void animalIsDead(Animal animal) {
        removeFromProperCell(animal);
    }
}
