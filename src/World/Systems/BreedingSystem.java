package World.Systems;

import Utility.Vector2d;
import World.Entities.Animal;
import World.ObserversInterfaces.IBirthObserver;
import World.ObserversInterfaces.IBirthSender;
import World.Map.IWorldMap;
import World.Map.WorldMapCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class BreedingSystem implements IBirthSender {
    private final IWorldMap map;

    public BreedingSystem(IWorldMap map) {
        this.map = map;
    }

    public List<Animal> getChildren(Iterator<WorldMapCell> it) {
        ArrayList<Animal> children = new ArrayList<>();

        while(it.hasNext()) {
            var cell = it.next();
            if(cell.sizeOfAnimals() < 2) {
                continue;
            }
            var candidatesToReproduce = cell.getTopAnimals(2);
            var par0 = candidatesToReproduce.get(0);
            var par1 = candidatesToReproduce.get(1);
            if(!par1.canBreed()) {
                continue;
            }
            Animal child = new Animal(par0, par1);
            Vector2d pos = generatePositionForChild(par0.getWorldPosition());
            child.setPosition(pos);
            children.add(child);
            notifyObservers(par0, child);
            notifyObservers(par1, child);
        }

        return children;
    }

    private Vector2d generatePositionForChild(Vector2d position) {
        var posPositions = new ArrayList<Vector2d>(8);
        for(int y = -1; y <= 1; ++y) {
            for(int x = -1; x <= 1; ++x) {
                if(x == 0 && y == 0) {
                    continue;
                }
                var diff = new Vector2d(x, y);
                posPositions.add(map.getProperNextPosition(position.add(diff)));
            }
        }
        Collections.shuffle(posPositions);
        for(var pos : posPositions) {
            if(map.isOccupied(pos)) {
                continue;
            }
            return  pos;
        }
        return posPositions.get(0);
    }

    private final List<IBirthObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(IBirthObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IBirthObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(Animal parent, Animal child) {
        for(var observer : observers) {
            observer.hasBeenGivenBirth(parent, child);
        }
    }
}
