package World.Map;

import Utility.Vector2d;

public class JungleRegion implements IRandomPositionGenerator {
    private final IWorldMap map;
    private final Vector2d size;
    private int nGrasses = 0;

    public JungleRegion(Vector2d size, IWorldMap map) {
        this.size = size;
        this.map = map;
    }

    public Vector2d getFreePosition() {
        Vector2d newPos;
        do {
            newPos = Vector2d.getRandom(getLowerLeftJunglePos(), getUpperRightJunglePos());
        } while(map.isOccupied(newPos));
        return  newPos;
    }

    public boolean canBeGrassAdded() {
        return getGrassMaxCapacity() <= nGrasses;

    }
    public int getGrassMaxCapacity() {
        return size.x * size.y;
    }

    public Vector2d getLowerLeftJunglePos() {
        return map.getLowerLeft().add(map.getSize().subtract(size).div(2));
    }

    public Vector2d getUpperRightJunglePos() {
        return getLowerLeftJunglePos().add(size);
    }

    public boolean isInJungle(Vector2d position ) {
        return getLowerLeftJunglePos().follow(position) && position.follow(getUpperRightJunglePos());
    }

    @Override
    public Vector2d getNextRandomValidPosition() {
        return Vector2d.getRandom(getLowerLeftJunglePos(), getUpperRightJunglePos());
    }
}
