package Utility.Config;

import Utility.Vector2d;

public interface IWorldConfig {
    int getNStartingAnimals();
    int getAnimalsStartingEnergy();
    int getGrassEnergy();
    int getEnergyCostOfMove();
    IWorldMapConfig getWorldMapConfig();
}
