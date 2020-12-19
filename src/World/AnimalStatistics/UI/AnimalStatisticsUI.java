package World.AnimalStatistics.UI;

import Utility.UI.Widget;
import Utility.Vector2d;
import World.AnimalStatistics.AnimalStatistics;
import processing.core.PApplet;

public class AnimalStatisticsUI extends Widget {
    private final Widget mainWidget = new Widget();
    private final AnimalStatistics statistics;

    public AnimalStatisticsUI(AnimalStatistics statistics) {
        this.statistics = statistics;
    }

    @Override
    public void draw(PApplet ps, Vector2d pos) {
        super.draw(ps, pos);
        Vector2d finalPos = pos.add(getPosition());
        ps.stroke(0);
        ps.textSize(10);
        String text = statistics.getStatistics().stream().reduce("", (t, s) -> t + "\n" + s.getText(), (i, t) -> i + t);
        ps.text(text, finalPos.x, finalPos.y);
    }

}
