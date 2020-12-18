package World.Map;

import Utility.Vector2d;

public class FreePositionsManager {
    private final FreePositionsInRegionsManager outsideJungleFreePositions;
    private final FreePositionsInRegionsManager insideJungleFreePositions;
    private final JungleRegion jungleRegion;
    public FreePositionsManager(IRandomPositionGenerator map, Vector2d size, JungleRegion jungleRegion) {
        this.jungleRegion = jungleRegion;
        outsideJungleFreePositions = new FreePositionsInRegionsManager(map);
        insideJungleFreePositions = new FreePositionsInRegionsManager(jungleRegion);

        for(int y = 0; y < size.y; ++y) {
            for (int x = 0; x < size.x; ++x) {
                var pos= new Vector2d(x, y);
                if(jungleRegion.isInJungle(pos)) {
                    insideJungleFreePositions.insertFreePosition(pos);
                } else {
                    outsideJungleFreePositions.insertFreePosition(new Vector2d(x, y));
                }
            }
        }
    }

    public void addFreePosition(Vector2d pos) {
        if(jungleRegion.isInJungle(pos))  {
            insideJungleFreePositions.insertFreePosition(pos);
        } else {
            outsideJungleFreePositions.insertFreePosition(pos);
        }
    }

    public void removeFreePosition(Vector2d pos) {
        if(jungleRegion.isInJungle(pos))  {
            insideJungleFreePositions.removeFreePosition(pos);
        } else {
            outsideJungleFreePositions.removeFreePosition(pos);
        }
    }

    public Vector2d getRandomFreePositionInJungle() {
        if(insideJungleFreePositions.isEmpty()) {
            return null;
        }
        return insideJungleFreePositions.getRandomFreePosition();
    }

    public Vector2d getRandomFreePositionOutsideJungle() {
        if(outsideJungleFreePositions.isEmpty()) {
            return null;
        }
        return outsideJungleFreePositions.getRandomFreePosition();
    }
}
