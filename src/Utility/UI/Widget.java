package Utility.UI;

import Utility.Vector2d;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class Widget implements IWidget {
    private Vector2d position;
    private Vector2d size;
    private final List<IWidget> widgets = new ArrayList<>();

    @Override
    public void draw(PApplet ps, Vector2d pos) {
        Vector2d finalPos = pos.add(getPosition());
        ps.fill(100, 100);
        ps.stroke(0, 0, 100, 100);
        ps.rect(finalPos.x, finalPos.y, size.x, size.y);
        drawChildren(ps, pos);
    }

    public void draw(PApplet ps) {
        draw(ps, new Vector2d(0, 0));
    }

    private void drawChildren(PApplet ps, Vector2d pos) {
        widgets.forEach(w -> w.draw(ps, pos.add(getPosition())));
    }


    @Override
    public void add(IWidget child) {
        widgets.add(child);
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public Vector2d getSize() {
        return size;
    }

    public void setSize(Vector2d size) {
        this.size = size;
    }
}
