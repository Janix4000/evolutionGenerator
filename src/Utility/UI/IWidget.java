package Utility.UI;

import Utility.Vector2d;
import processing.core.PApplet;

public interface IWidget {
    void draw(PApplet ps, Vector2d pos);
    void add(IWidget child);
    Vector2d getPosition();
    void setPosition(Vector2d position);
}
