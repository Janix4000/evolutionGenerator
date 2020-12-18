package World.Entities;

import Utility.Rectangle;
import Utility.Vector2d;
import processing.core.PGraphics;

public class Grass implements IWorldElement {
    private Vector2d position;
    @Override
    public Vector2d getWorldPosition() {
        return position;
    }

    @Override
    public void draw(PGraphics graphics, Rectangle box) {

    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }
}
