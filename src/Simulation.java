import Utility.Config.Config;
import Utility.Rectangle2d;
import Utility.Vector2d;
import World.World;
import processing.core.PApplet;
import processing.event.KeyEvent;

import java.io.IOException;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class Simulation {
    private final Rectangle2d screenBox;
    private final World world;
    private float tickCooldown = 0;
    private int nTicksPerSecond = 20;
    private AppState state = AppState.Running;
    private boolean isSelected = false;


    public Simulation(Rectangle2d screenBox, Config config, PApplet ps) {
        this.screenBox = screenBox;
        world = new World(ps, config, screenBox.size);
    }

    public void update(float dt) {
        if(state == AppState.Stopped) {
            return;
        }
        tickCooldown -= dt;
        while(tickCooldown <= 0) {
            world.makeTick();
            tickCooldown += getTickDf();
        }
    }

    public void draw(PApplet ps) {
        var worldGraphic = world.draw();
        ps.image(worldGraphic, screenBox.position.x ,screenBox.position.y);
        if(isSelected) {
            ps.stroke(255, 0, 0);
            ps.strokeWeight(5);
            ps.noFill();
            ps.rect(screenBox.position.x ,screenBox.position.y, screenBox.size.x, screenBox.size.y);
        }
    }

    void keyReleased(KeyEvent event) {
        final int leftKeyCode = 37;
        final int rightKeyCode = 39;
        if(!isSelected) {
            return;
        }
        if (event.getKey() == ' ') {
            state = state.flip();
        }
        if(state == AppState.Running) {
            if (event.getKeyCode() == leftKeyCode) {
                nTicksPerSecond = max(1, nTicksPerSecond - 1);
            } else if (event.getKeyCode() == rightKeyCode) {
                nTicksPerSecond = min(60, nTicksPerSecond + 1);
            }
        }
    }

    public void saveStatistics(String fileName) throws IOException {
        world.saveStatistics(fileName);
    }

    private float getTickDf() {
        return (float) 1 / nTicksPerSecond;
    }

    public void mouseClicked(Vector2d mousePos) {
        if(state == AppState.Stopped) {
            world.processMouseEvent(mousePos.subtract(screenBox.position));
        }
    }

    public void select() {
        isSelected = true;
    }

    public void unselect() {
        isSelected = false;
    }

    public Rectangle2d getScreenBox() {
        return screenBox;
    }
}
