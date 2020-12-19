package Utility.UI;

import Utility.Vector2d;
import processing.core.PApplet;
import processing.core.PGraphics;

public interface IWidget {
    void draw(PGraphics ps, Vector2d pos);
    void add(IWidget child);
    Vector2d getPosition();
    void setPosition(Vector2d position);
}
