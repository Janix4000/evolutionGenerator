package World.WorldStatistics.UI;

import Utility.UI.Widget;
import Utility.Vector2d;
import World.WorldStatistics.WorldStatistics;
import processing.core.PGraphics;

public class WorldStatisticsUI extends Widget {
    private final Widget mainWidget = new Widget();
    private final WorldStatistics statistics;

    public WorldStatisticsUI(WorldStatistics statistics) {
        this.statistics = statistics;
    }

    @Override
    public void draw(PGraphics ps, Vector2d pos) {
        super.draw(ps, pos);
        Vector2d finalPos = pos.add(getPosition());
        ps.fill(255);
        ps.textSize(12);
        String text = statistics.getTextStatistics().stream().reduce("", (t, s) -> t + "\n" + s.getText(), (i, t) -> i + t);
        ps.text(text, finalPos.x, finalPos.y);
    }

}
