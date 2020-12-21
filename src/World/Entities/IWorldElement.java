package World.Entities;
import Utility.Rectangle2d;
import Utility.Vector2d;
import processing.core.PGraphics;

public interface IWorldElement {
    Vector2d getWorldPosition();
    void draw(PGraphics graphics, Rectangle2d box);
}
