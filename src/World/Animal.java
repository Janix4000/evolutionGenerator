package World;

import Utility.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Animal implements IWorldElement, IPositionChangeSender<Animal>, Comparable<Animal> {
    private Vector2d position;
    private final MapDirection mapDirection = MapDirection.N;
    private final List<IPositionChangeObserver<Animal>> postionObservers = new ArrayList<>();
    private final List<IAnimalDeathObserver> isDeadObservers = new ArrayList<>();
    private int energy;
    private final IWorldBoundaries boundaries;


    // private final IWorldMap worldMap;

    public Animal(IWorldBoundaries boundaries) {
        this(boundaries, new Vector2d(0 ,0 ));
    }
    public Animal(IWorldBoundaries boundaries, Vector2d initialPosition) {
        this.boundaries = boundaries;
        setPosition(initialPosition);
    }

    private void setPosition(Vector2d newPos) {
        var oldPos = this.getPosition();
        newPos = boundaries.getProperNextPosition(newPos);
        this.position = newPos;
        positionChanged(oldPos);
    }



    public String toTestString() {
        return position.toString() + " " + mapDirection.toString();
    }


    @Override
    public String toString() {
        return switch (this.mapDirection) {
            case S -> "S";
            case E -> "E";
            case N -> "N";
            case W -> "W";
            case NE -> "Ne";
            case NW -> "NW";
            case SE -> "SE";
            case SW -> "SW";
        };
    }

    public void consumeEnergy(int x) {
        addEnergy( -x );
    }

    public void addEnergy(int x) {
        this.energy += x;
    }

    public boolean hasNoEnergy() {
        return this.energy <= 0;
    }

    public void move() {
        var add = mapDirection.toUnitVector();
        var newPos = position.add(add);
        setPosition(newPos);
    }
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Animal)) {
            return false;
        }
        Animal that = (Animal) other;
        return this.position == that.position &&
                this.mapDirection == that.mapDirection;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void addPositionObserver(IPositionChangeObserver<Animal> observer) {
        this.postionObservers.add(observer);
    }
    public void removePositionObserver(IPositionChangeObserver<Animal> observer) {
        this.postionObservers.remove(observer);
    }

    @Override
    public IWorldElement getWorldElement() {
        return this;
    }

    private void positionChanged(Vector2d oldPos) {
        for(var observer : this.postionObservers) {
            observer.positionChanged(oldPos, this.getPosition(), this);
        }
    }

    public void addIsDeadObserver(IAnimalDeathObserver observer) {
        isDeadObservers.add(observer);
    }
    public void removeIsDeadObserver(IAnimalDeathObserver observer) {
        isDeadObservers.remove(observer);
    }

    public void kill() {
        isDeadObservers.forEach(observer -> observer.animalIsDead(this));
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public int compareTo(Animal that) {
        return -(this.energy - that.energy);
    }
}
