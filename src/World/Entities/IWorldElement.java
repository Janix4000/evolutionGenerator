package World.Entities;
import Utility.Rectangle;
import Utility.Vector2d;
import World.World;
import processing.core.PGraphics;

public interface IWorldElement {
    Vector2d getWorldPosition();
    void draw(PGraphics graphics, Rectangle box);
}
