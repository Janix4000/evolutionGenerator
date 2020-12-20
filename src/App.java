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
    private float tickDf = (float) (1.0 / 60);

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
        tickCooldown -= 1 / frameRate;
        while(tickCooldown <= 0) {
            world.makeTick();
            tickCooldown += tickDf;
        }
    }

    private void render() {
        background(0);
        this.smooth();
        var worldGraphic = world.draw();
        image(worldGraphic, 0 ,0);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        super.keyReleased(event);
        System.out.println(event.getKey());
    }
}
