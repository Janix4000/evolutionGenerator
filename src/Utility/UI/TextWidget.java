package Utility.UI;

import Utility.Vector2d;
import processing.core.PApplet;
import processing.core.PGraphics;

public class TextWidget extends Widget {
    private String text;


    @Override
    public void draw(PGraphics ps, Vector2d pos) {
        super.draw(ps, pos);
        if(text != null) {
            Vector2d finalPos = pos.add(getPosition());
            ps.stroke(0);
            ps.textSize(10);
            ps.text(text, finalPos.x, finalPos.y);
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
