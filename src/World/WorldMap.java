package World;

import Utility.Vector2d;

public class WorldMap implements IWorldBoundaries {
    private final AnimalMap animals = new AnimalMap();
    private final Vector2d size;
    WorldMap(Vector2d size) {
        this.size = size;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
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
}
