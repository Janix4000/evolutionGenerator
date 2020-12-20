import Utility.Config.Config;
import Utility.Config.JsonConfigLoader;
import Utility.Vector2d;
import World.World;
import processing.core.PApplet;
import processing.event.KeyEvent;

public class App extends PApplet {
    private World world;
    private int last_t = 0;
    private AppState state = AppState.Running;
    private float tickCooldown = 0;
    private int nTicksPerSecond = 20;

    public static void main(String[] args) {
        PApplet.main("App", args);
    }

    public void settings() {
        size(1000, 600);
    }

    public void setup() {
        Config config = JsonConfigLoader.load("parameters.json", this);
        world = new World(this, config, new Vector2d(800, 600));
        frameRate(60);
    }

    public void draw() {
        update();
        render();
        frame.setTitle("FPS: " + round(frameRate));
    }

    private void update() {
        if(state == AppState.Stopped) {
            return;
        }
        tickCooldown -= 1 / frameRate;
        while(tickCooldown <= 0) {
            world.makeTick();
            tickCooldown += getTickDf();
        }
    }

    private float getTickDf() {
        return (float) 1 / nTicksPerSecond;
    }

    private void render() {
        background(0);
        this.smooth();
        var worldGraphic = world.draw();
        image(worldGraphic, 0 ,0);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        final int leftKeyCode = 37;
        final int rightKeyCode = 39;
        super.keyReleased(event);
        if (event.getKey() == ' ') {
            state = state.flip();
        }
        if(state == AppState.Running) {
            if (event.getKeyCode() == leftKeyCode) {
                nTicksPerSecond = max(1, nTicksPerSecond - 1);
            } else if (event.getKeyCode() == rightKeyCode) {
                nTicksPerSecond = min(60, nTicksPerSecond + 1);
            }
        } else {
            if(event.getKey() == 'a') {
                world.printAverageStatistics();
            }
        }
    }

    @Override
    public void mouseClicked() {
        super.mouseClicked();
        processMouseEvent();

    }

    private void processMouseEvent() {
        if(state == AppState.Stopped) {
            world.processMouseEvent(new Vector2d(mouseX, mouseY).subtract(new Vector2d(0, 0)));
        }
    }
}
