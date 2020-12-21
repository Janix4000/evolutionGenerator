import Utility.Config.Config;
import Utility.Config.JsonConfigLoader;
import Utility.Rectangle2d;
import Utility.Vector2d;
import processing.core.PApplet;
import processing.event.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends PApplet {
    private final List<Simulation> simulations = new ArrayList<>();
    private Simulation selectedSimulation;

    public static void main(String[] args) {
        PApplet.main("App", args);
    }

    public void settings() {
        size(1700, 500);
    }

    public void setup() {

        Config config;
        try {
            config = JsonConfigLoader.load("parameters.json", this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            super.exit();
            return;
        }
        frameRate(60);
        final Vector2d simSize = new Vector2d(width / 2, height);
        simulations.add(new Simulation(new Rectangle2d(new Vector2d(0 ,0), simSize), config, this));
        simulations.add(new Simulation(new Rectangle2d(new Vector2d(simSize.x ,0), simSize), config, this));
    }

    public void draw() {
        update();
        render();
        frame.setTitle("FPS: " + round(frameRate));
    }

    private void update() {
        final float dt = (float) 1 / frameRate;
        simulations.forEach(s -> s.update(dt));
    }


    private void render() {
        background(0);
        this.smooth();
        simulations.forEach(s -> s.draw(this));
    }

    @Override
    public void keyReleased(KeyEvent event) {
        super.keyReleased(event);

        simulations.forEach(s -> s.keyReleased(event));

        if(event.getKey() == 'a') {
            saveStatistics();
        }


    }

    private void saveStatistics() {
        for(int i = 0; i < simulations.size(); ++i) {
            var simulation = simulations.get(i);
            final String fileName =  "statistics" + i + ".json";
            try {
                simulation.saveStatistics(fileName);
                System.out.println("Saved statistics to " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Couldn't saved statistics to " + fileName);
            }
        }
    }


    @Override
    public void mouseClicked() {
        super.mouseClicked();
        Vector2d mousePos = new Vector2d(mouseX, mouseY);
        simulations.forEach(s -> s.mouseClicked(mousePos));
        handleSelection(mousePos);
    }

    private void handleSelection(Vector2d mousePos) {
        simulations.forEach(s -> {
            if(s.getScreenBox().isIn(mousePos)) {
                if(selectedSimulation != null) {
                    selectedSimulation.unselect();
                }
                selectedSimulation = s;
                s.select();
            }
        });
    }
}
