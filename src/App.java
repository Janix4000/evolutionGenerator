import World.World;
import processing.core.PApplet;

public class App extends PApplet {
    private World world;
    private int last_t = 0;
    public static void main(String[] args) {
        PApplet.main("App", args);
    }

    public void settings() {
        size(800, 600);
    }

    public void setup() {
        world = new World(this);
        //frameRate(60);
    }

    public void draw() {
        update();
        render();
        frame.setTitle("FPS: " + round(frameRate));
    }

    private void update() {
        if(keyPressed) {
            world.makeTick();
            System.out.println(key);
        }
    }

    private void render() {
        background(0);

        var worldGraphic = world.draw();
        image(worldGraphic, 0 ,0);
    }
}
