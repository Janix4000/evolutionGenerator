package Utility.Config;

import Utility.Vector2d;
import processing.data.JSONObject;

public class Config implements IWorldConfig, IWorldMapConfig{
    private final int nStartingAnimals;
    private final Vector2d jungleSize;
    private final Vector2d mapSize;
    private final int startingEnergy;
    private final int grassEnergy;
    private final int moveCost;

    public Config(JSONObject json) throws IllegalArgumentException {
        try {
            nStartingAnimals = json.getInt("nStartingAnimals");
            startingEnergy = json.getInt("startEnergy");
            moveCost = json.getInt("moveEnergy");
            grassEnergy = json.getInt("plantEnergy");
            int width = json.getInt("width");
            int height = json.getInt("height");
            mapSize = new Vector2d(width, height);
            float jungleRatio = json.getFloat("jungleRatio");
            jungleSize = new Vector2d((int) (jungleRatio * width), (int) (jungleRatio * height));
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("There are no parameters in got json config in Config constructor\n" + e.getMessage());
        }
    }


    @Override
    public int getNStartingAnimals() {
        return nStartingAnimals;
    }

    @Override
    public int getAnimalsStartingEnergy() {
        return startingEnergy;
    }

    @Override
    public int getGrassEnergy() {
        return grassEnergy;
    }

    @Override
    public int getEnergyCostOfMove() {
        return moveCost;
    }

    @Override
    public IWorldMapConfig getWorldMapConfig() {
        return this;
    }

    @Override
    public Vector2d getJungleSize() {
        return jungleSize;
    }

    @Override
    public Vector2d getWorldSize() {
        return mapSize;
    }
}
