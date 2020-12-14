import processing.core.PApplet;

public class App extends PApplet {

    public static void main(String[] args) {
        PApplet.main("App", args);
    }

    public void settings() {
        size(800, 600);
    }

    public void draw() {
        background(0);

        stroke(255);
        line(0, 0, 250, 250);
    }

}
