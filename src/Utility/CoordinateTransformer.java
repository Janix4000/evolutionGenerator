package Utility;

import World.Map.IWorldMap;

public class CoordinateTransformer {
    private final IWorldMap map;
    private final Vector2d sceneSize;

    public CoordinateTransformer(IWorldMap map, Vector2d sceneSize) {
        this.map = map;
        this.sceneSize = sceneSize;
    }
    public Vector2d toSceneCords(Vector2d pos) {
        int x = (pos.x - map.getLowerLeft().x) * sceneSize.x / map.getSize().x;
        int y = (pos.y - map.getLowerLeft().y) * sceneSize.y / map.getSize().y;
        return new Vector2d(x, y);
    }

    public Vector2d toWorldCords(Vector2d pos) {
        int x = (pos.x * map.getSize().x / sceneSize.x) + map.getLowerLeft().x;
        int y = (pos.y * map.getSize().y / sceneSize.y) + map.getLowerLeft().y;
        return new Vector2d(x, y);
    }
}
