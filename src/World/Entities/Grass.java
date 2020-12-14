package World.Entities;

import Utility.Vector2d;

public class Grass implements IWorldElement {
    private Vector2d position;
    @Override
    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }
}
