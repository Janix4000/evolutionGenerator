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
        var pos = box.position;
        var size = box.size;
        graphics.noStroke();
        graphics.fill(255, 255, 0);
        graphics.ellipse(pos.x + (float) size.x / 2, pos.y + (float) size.y / 2, size.x , size.y);
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }
}
