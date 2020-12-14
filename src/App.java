import processing.core.PApplet;

public class App extends PApplet {

    public static void main(String[] args) {
        PApplet.main("App", args);
    }

    public void settings() {
        size(800, 600);
    }

    public void draw() {
        line(0, 0, 5, 5);
    }

}
