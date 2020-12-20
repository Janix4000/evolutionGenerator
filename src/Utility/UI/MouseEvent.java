package Utility.UI;

import Utility.Vector2d;

class MouseEvent {
    public final int mouseButton;
    public final Vector2d mousePosition;

    public MouseEvent(int mouseButton, int mouseX, int mouseY) {
        this.mouseButton = mouseButton;
        this.mousePosition = new Vector2d(mouseX, mouseY);
    }

    public MouseEvent(MouseEvent event, Vector2d shift) {
        this(event.mouseButton, event.mousePosition.x - shift.x, event.mousePosition.y - shift.y);
    }
}
