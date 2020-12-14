package Utility;

import World.Map.IWorldMap;

public class CoordinateTransformer {
    private final IWorldMap map;
    private final Vector2d worldSize;

    public CoordinateTransformer(IWorldMap map, Vector2d worldSize) {
        this.map = map;
        this.worldSize = worldSize;
    }
    public Vector2d toWorldCords(Vector2d pos) {
        int x = (pos.x - map.getLowerLeft().x) * worldSize.x / map.getSize().x;
        int y = (pos.y - map.getLowerLeft().y) * worldSize.y / map.getSize().y;
        return new Vector2d(x, y);
    }
}
