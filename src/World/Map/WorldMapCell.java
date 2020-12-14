package World.Map;

import World.Entities.Animal;
import World.Entities.Grass;
import World.Entities.IWorldElement;
import World.World;

import java.util.*;

public class WorldMapCell {
    private final List<Animal> animals = new ArrayList<>();
    private Grass grass = null;
    public int sizeOfAnimals() {
        return animals.size();
    }
    public boolean hasAnyAnimals() {
        return sizeOfAnimals() > 0;
    }
    public boolean hasGrass() {
        return grass != null;
    }
    public boolean isEmpty() {
        return !hasGrass() && !hasAnyAnimals();
    }
    public void add(Animal animal) {
        animals.add(animal);
    }
    public void addGrass(Grass grass) {
        this.grass = grass;
    }

    public void remove (Animal animal) {
        animals.remove(animal);
    }
    public void removeGrass() {
        grass = null;
    }

    public IWorldElement getRepresentative() {
        if(hasAnyAnimals()) {
            return getTopAnimals(1).get(0);
        } else {
            return grass;
        }
    }

    List<Animal> getTopAnimals(int n) {
        if(n > animals.size()) {
            throw new IllegalArgumentException("There is only " + animals.size() + " animals in the cell, wanted top " + n);
        }
        validateAnimalsSort();
        var topN = new ArrayList<Animal>(n);
        return animals.subList(0, n);
    }
    List<Animal> getBestAnimals() {
        if(animals.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("Cannot take any best animal, because there is no animals in the cell");
        }
        validateAnimalsSort();
        var bestEnergy = animals.get(0).getEnergy();
        int n = 0;
        for(var animal : animals) {
            if(animal.getEnergy() != bestEnergy) {
                break;
            }
            ++n;
        }
        return animals.subList(0, n);
    }

    private void validateAnimalsSort() {
        animals.sort((a0, a1) -> -(a0.getEnergy() - a1.getEnergy()));
    }

}
